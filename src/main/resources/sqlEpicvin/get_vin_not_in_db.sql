SELECT vin
FROM epicv1ndb.tbl_searched_vins
WHERE in_db = 0
AND valid_pattern = 1
ORDER BY id DESC
LIMIT 1;