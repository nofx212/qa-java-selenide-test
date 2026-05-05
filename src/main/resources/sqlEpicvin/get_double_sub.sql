SELECT user_id
FROM tbl_subscriptions
WHERE status IN ('active', 'past_due')
AND name NOT LIKE '%dealer%'
AND user_id NOT IN (${TEST_USER_IDS})
GROUP BY user_id
having count(1)>1