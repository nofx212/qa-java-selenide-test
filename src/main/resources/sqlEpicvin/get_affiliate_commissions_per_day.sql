WITH daily_commission AS (
    SELECT
        DATE(created_at) AS day,
        COUNT(*) AS total_commission
    FROM epicv1ndb.tbl_affiliate_commissions_logs
    WHERE created_at >= CURDATE() - INTERVAL 8 DAY
      AND created_at < CURDATE()
    GROUP BY DATE(created_at)
),
yesterday AS (
    SELECT
        COALESCE(total_commission, 0) AS yesterday_commission
    FROM daily_commission
    WHERE day = CURDATE() - INTERVAL 1 DAY
),
avg_7_days AS (
    SELECT
        AVG(total_commission) AS avg_prev_7_days
    FROM daily_commission
    WHERE day BETWEEN CURDATE() - INTERVAL 8 DAY
                  AND CURDATE() - INTERVAL 2 DAY
)
SELECT
    y.yesterday_commission,
    a.avg_prev_7_days
FROM yesterday y
CROSS JOIN avg_7_days a
WHERE y.yesterday_commission < a.avg_prev_7_days * 0.4;