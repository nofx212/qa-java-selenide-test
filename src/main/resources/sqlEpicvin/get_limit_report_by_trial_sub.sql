select o.user_id, o.order_time, (
    select count(1)
    from tbl_reports_access a
    where type='unlimFull' and a.order_time > o.order_time and a.order_time <  DATE_ADD(o.order_time, INTERVAL 3 DAY)
      and a.user_id=o.user_id
) cnt
from tbl_orders o
where subscription_stage = -1
  and packet_id = 138
  and o.user_id not in (${TEST_USER_IDS})
  and not exists(
    select 1 from tbl_orders o1 where o.user_id=o1.user_id and o.packet_id=o1.packet_id and o1.order_time <  DATE_ADD(o.order_time, INTERVAL 3 DAY) limit 1
)
having cnt>15
order by o.id desc