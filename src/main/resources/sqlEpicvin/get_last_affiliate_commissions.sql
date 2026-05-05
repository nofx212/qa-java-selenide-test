select TIMESTAMPDIFF(HOUR, created_at, now()) cnt_hours
from epicv1ndb.tbl_affiliate_commissions_logs
order by id desc
limit 1