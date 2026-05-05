WITH excluded_users AS (
    SELECT u.id
    FROM tbl_settings s
    JOIN JSON_TABLE(
        CONCAT('["', REPLACE(s.val, ',', '","'), '"]'),
        '$[*]' COLUMNS (email VARCHAR(255) PATH '$')
    ) AS emails ON TRUE
    JOIN tbl_users u ON u.email = emails.email
    WHERE s.key = 'test_users'
),
daily_stats AS (
    SELECT DATE(order_time) AS day, SUM(amount) AS total_amount
    FROM tbl_orders
    WHERE amount > 2
      AND payment_type != 'paypal'
      AND order_time > '2026-02-01'
      AND TIME(order_time) <= CURRENT_TIME
      AND user_id NOT IN (SELECT id FROM excluded_users)
    GROUP BY DATE(order_time)
),
today AS (
    SELECT COALESCE(SUM(amount), 0) AS today_amount
    FROM tbl_orders
    WHERE DATE(order_time) = CURDATE()
      AND amount > 2
      AND payment_type != 'paypal'
      AND TIME(order_time) <= CURRENT_TIME
      AND user_id NOT IN (SELECT id FROM excluded_users)
),
median_base AS (
    SELECT total_amount,
           ROW_NUMBER() OVER (ORDER BY total_amount) AS rn,
           COUNT(*)     OVER ()                       AS cnt
    FROM daily_stats
    WHERE day BETWEEN CURDATE() - INTERVAL 10 DAY AND CURDATE() - INTERVAL 1 DAY
      AND total_amount > 0
),
median_10_days AS (
    SELECT AVG(total_amount) AS median_last_10_days
    FROM median_base
    WHERE rn IN (FLOOR((cnt + 1) / 2), FLOOR((cnt + 2) / 2))
)
SELECT
    t.today_amount,
    m.median_last_10_days,
    ROUND((t.today_amount - m.median_last_10_days) / m.median_last_10_days * 100, 2) AS diff_percent,
    CASE
        WHEN t.today_amount <= m.median_last_10_days * 0.8 THEN 'ANOMALY'
        ELSE 'OK'
    END AS status
FROM today t
CROSS JOIN median_10_days m;