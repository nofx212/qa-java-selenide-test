WITH daily_stats AS (
    SELECT
        DATE(update_time) AS day,
        auction,
        SUM(qty) AS total_qty
    FROM auctionsparser.tbl_overall_stats
    WHERE update_time >= CURDATE() - INTERVAL 31 DAY
      AND update_time < CURDATE()
      AND auction NOT IN (
            'manheim',
            'manheim2',
            'iaaica',
            'adesa',
            'ss',
            'getauto',
            'hemmings'
      )
    GROUP BY DATE(update_time), auction
),

yesterday AS (
    SELECT
        auction,
        COALESCE(total_qty, 0) AS yesterday_qty
    FROM daily_stats
    WHERE day = CURDATE() - INTERVAL 1 DAY
),

median_base AS (
    SELECT
        auction,
        total_qty,
        ROW_NUMBER() OVER (PARTITION BY auction ORDER BY total_qty) AS rn,
        COUNT(*) OVER (PARTITION BY auction) AS cnt
    FROM daily_stats
    WHERE day BETWEEN CURDATE() - INTERVAL 31 DAY
                  AND CURDATE() - INTERVAL 2 DAY
      AND total_qty > 0
),

median_month AS (
    SELECT
        auction,
        AVG(total_qty) AS median_prev_30_days
    FROM median_base
    WHERE rn IN (
        FLOOR((cnt + 1) / 2),
        FLOOR((cnt + 2) / 2)
    )
    GROUP BY auction
)

SELECT
    y.auction,
    y.yesterday_qty,
    m.median_prev_30_days
FROM yesterday y
JOIN median_month m ON y.auction = m.auction
WHERE y.yesterday_qty > 0
  AND y.yesterday_qty <= m.median_prev_30_days * 0.3
  -- исключаем iaai во вторник (стабильная просадка после анализа БД)
  AND NOT (
        y.auction = 'iaai'
        AND DAYOFWEEK(CURDATE() - INTERVAL 1 DAY) = 3
  )
  -- исключаем парсеры если они прошли ресурс быстро и находится в ожидании (парсер работает раз в ~2 дня)
  AND NOT (
        y.auction = 'joautok'
        AND EXISTS (
            SELECT 1 FROM daily_stats d2
            WHERE d2.auction = 'joautok'
              AND d2.day = CURDATE() - INTERVAL 2 DAY
              AND d2.total_qty > m.median_prev_30_days * 0.3
        )
  );
