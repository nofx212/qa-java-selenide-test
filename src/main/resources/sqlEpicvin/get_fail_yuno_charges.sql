WITH last_completed AS (
    SELECT MAX(id) AS id
    FROM tbl_charges
    WHERE transaction_indicator = 'INITIAL' AND status = 'succeeded'
)
SELECT count(1) attempts_failed_charges
FROM tbl_charges p, last_completed lc
WHERE p.transaction_indicator = 'INITIAL' AND p.status != 'succeeded'
  AND p.id > lc.id
  AND parent_id is null