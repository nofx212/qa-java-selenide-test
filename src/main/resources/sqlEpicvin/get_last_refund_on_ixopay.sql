select datediff(CURRENT_DATE, refund_dt) cnt_days
from tbl_orders
where payment_type='ixopay' and refund_dt is not null
order by refund_dt desc
limit 1;