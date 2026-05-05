SELECT COALESCE(GROUP_CONCAT(id), '0') AS ids
FROM tbl_subscriptions
WHERE status != 'canceled'
AND gateway ='paypal'
AND scheme_id is null
AND DATEDIFF(CURRENT_TIMESTAMP(), next_payment_date) > 1;