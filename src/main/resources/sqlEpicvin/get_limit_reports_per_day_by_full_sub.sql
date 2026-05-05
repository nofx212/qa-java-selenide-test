select user_id, count(1) cnt
from tbl_reports_access
where order_time >= date_sub(CURRENT_DATE, interval 1 day) and order_time < CURRENT_DATE
  and `type` in ('unlimFull', 'vinDb')
  and user_id not in (${TEST_USER_IDS})
group by user_id
having cnt > 26;
