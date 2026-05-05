SELECT COALESCE(count(*), '0') AS count_result
FROM tbl_subscriptions
WHERE status != 'canceled' and DATEDIFF(CURRENT_TIMESTAMP(), ends_at) > 1