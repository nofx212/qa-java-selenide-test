select subscription_id
from tbl_subscription_trial_stop_log
group by subscription_id
having count(1) > 1