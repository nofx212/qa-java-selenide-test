select d.user_id, d.created_at,
       (select status from tbl_subscriptions s where s.user_id=d.user_id and s.created_at < d.created_at order by s.id desc limit 1) st
from tbl_disputes d
where user_id is not null
having st != 'canceled';