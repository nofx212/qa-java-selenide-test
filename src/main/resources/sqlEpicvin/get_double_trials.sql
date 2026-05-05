SELECT distinct user_id
FROM tbl_orders o
WHERE o.packet_id in (134, 107, 135,138)
  and o.order_time > '2025-04-02 06:00:00'
  and o.is_renew = 0
  and o.refund_dt is null
  and o.user_id not in (${TEST_USER_IDS})
  and exists(select 1
             from tbl_orders o2
             where o2.packet_id in (134, 107, 135,138)
               and o2.user_id = o.user_id
               and o2.id != o.id
               and o2.is_renew = 0
               and o2.paypal_pay_id != o.paypal_pay_id
             limit 1)