SELECT
    auction,
    COALESCE(MAX(CASE WHEN count_images > 0 THEN create_date END), '1900-01-01') AS last_photo_date
FROM tbl_cars
WHERE id > (SELECT MAX(id) FROM tbl_cars) - 5000000
  AND auction NOT IN (
      'manheim',
      'manheim2',
      'iaaica',
      'adesa',
      'ss',
      'getauto',
      'hemmings'
  )
GROUP BY auction
HAVING COALESCE(MAX(CASE WHEN count_images > 0 THEN create_date END), '1900-01-01') <
    CASE
        WHEN auction = 'carzz' THEN NOW() - INTERVAL 5 DAY
        WHEN auction in ('tiriacauto', 'autogielda') THEN NOW() - INTERVAL 10 DAY
        WHEN auction = 'salvagenow' THEN NOW() - INTERVAL 20 DAY
        ELSE NOW() - INTERVAL 2 DAY
    END
ORDER BY last_photo_date ASC
LIMIT 100;