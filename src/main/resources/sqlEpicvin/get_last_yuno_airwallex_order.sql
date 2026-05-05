select TIMESTAMPDIFF(HOUR, order_time, NOW()) hours
from epicv1ndb.tbl_orders
where payment_type = 'yuno'
and payment_sub_type = 'airwallex' and `subscription_stage` = -1
order by id desc
limit 1