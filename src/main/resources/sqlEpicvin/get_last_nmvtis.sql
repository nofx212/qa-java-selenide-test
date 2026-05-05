select TIMESTAMPDIFF(HOUR, create_time, NOW()) as last_nmvtis
from tbl_nmvtis_logs
order by id desc
limit 1;