SELECT o.user_id, o.order_time, (
    SELECT COUNT(1)
    FROM tbl_reports_access a
    WHERE a.type = 'unlimFull'
      AND a.order_time > o.order_time
      AND a.user_id = o.user_id
) AS cnt
FROM tbl_orders o
WHERE o.subscription_stage = 0
  AND o.packet_id = 136
  AND o.user_id NOT IN (199312, 1026972, 549477, 534936, 534937, 534938, 534939, 534941,
                        721063, 734897, 534925, 534927, 534928, 534929, 534930, 534931,
                        534932, 534933, 534934, 534923, 291319, 201110, 634425, 37981)
  AND NOT EXISTS (
    SELECT 1
    FROM tbl_orders o1
    WHERE o1.user_id = o.user_id
      AND o1.order_time > o.order_time
    LIMIT 1
)
HAVING cnt > 100
ORDER BY o.id DESC;