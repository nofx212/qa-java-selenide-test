SELECT
    auction,
    vin,
    COUNT(*) AS cnt
FROM tbl_cars
WHERE id > ((SELECT MAX(id) FROM tbl_cars) - 5000000)
  AND vin <> ''
  AND create_date > '2026-01-22 10:30:00'
GROUP BY auction, vin
HAVING COUNT(*) >
    CASE
        WHEN auction IN ('iaai','copart') THEN 3
        WHEN auction IN ('kijijiautos','autoscout24','salvagenow','copartca','edgepipeline','ebay') THEN 2
        ELSE 1
    END
   AND
   (
       auction NOT IN ('ebay', 'edgepipeline')
       OR
       (
           MAX(CAST(REPLACE(odometer, ',', '') AS SIGNED)) -
           MIN(CAST(REPLACE(odometer, ',', '') AS SIGNED))
       ) <= 100
   )
ORDER BY cnt DESC
LIMIT 100;
