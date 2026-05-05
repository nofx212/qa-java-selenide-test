WITH last_completed AS (
    SELECT MAX(id) AS id
    FROM tbl_paypal_payments
    WHERE type = 'unlimFull' AND completed = 1
)
SELECT count(1) attempts_count_pp
FROM tbl_paypal_payments p, last_completed lc
WHERE p.type = 'unlimFull'
  AND p.completed = 0
  AND p.id > lc.id