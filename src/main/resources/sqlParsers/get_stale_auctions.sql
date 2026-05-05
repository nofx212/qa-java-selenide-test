SELECT
    auction,
    MAX(create_date) AS last_create_date
FROM tbl_cars
WHERE auction NOT IN (
'manheim',
'manheim2',
'iaaica',
'adesa',
'ss',
'getauto',
'hemmings'
)
AND id > ((SELECT MAX(id) FROM tbl_cars) - 5000000)
GROUP BY auction
HAVING MAX(create_date) <
    CASE
        WHEN auction = 'salvagenow' THEN NOW() - INTERVAL 20 DAY
        WHEN auction = 'autogielda' THEN NOW() - INTERVAL 10 DAY
        WHEN auction IN ('copartca','cinch','carzz','tiriacauto') THEN NOW() - INTERVAL 5 DAY
        ELSE NOW() - INTERVAL 2 DAY
    END;
