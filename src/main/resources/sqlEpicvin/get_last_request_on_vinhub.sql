select TIMESTAMPDIFF(HOUR, created, now()) cnt_hours
from tbl_vinhub_log
order by id desc
limit 1