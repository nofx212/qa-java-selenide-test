with log as (
  select *
  from tbl_captcha_log
  order by id desc
  limit 10
)
select count(1) cnt_score
from log
where score is null
