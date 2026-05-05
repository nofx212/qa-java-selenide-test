select user_id, count(1) cnt
from tbl_reports_access
where order_time >= date_sub(CURRENT_DATE, interval 1 day) and order_time < CURRENT_DATE
  and `type` in ('unlimFull', 'vinDb')
  and user_id not in (199312, 1026972, 549477, 534936, 534937, 534938, 534939, 534941,
      721063, 734897, 534925, 534927, 534928, 534929, 534930, 534931,
      534932, 534933, 534934, 534923, 291319, 201110, 634425, 37981)
group by user_id
having cnt > 26;
