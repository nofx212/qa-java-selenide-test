with trial_logs as(
  select l.user_id, meta->>'$.packet_id' packet_id, FROM_UNIXTIME(l.created_at) log_dt,
  (
    select order_time from tbl_orders o where o.subscription_id = s.id and o.subscription_stage = 0 limit 1
  ) order_dt
  from tbl_user_logs l
  inner join tbl_subscriptions s on s.id = meta->>'$.subscriptionId'
  inner join tbl_orders o on o.subscription_id = s.id and o.subscription_stage = -1
  where event_name = 'unlim-pause'
    and l.created_at > UNIX_TIMESTAMP(NOW() - INTERVAL 8 DAY)
    and l.created_at < UNIX_TIMESTAMP(NOW() - INTERVAL 4 DAY)
    and meta->>'$.packet_id' in (134, 138)
    and not exists(
      select 1 from tbl_subscription_trial_stop_log st where st.user_id = l.user_id limit 1
    )
    and not exists(
      select 1 from tbl_user_logs l2 where l2.user_id = l.user_id and l2.created_at > l.created_at and l2.event_name = 'unlim-resume' limit 1
    )
    and not exists(
      select 1 from tbl_user_logs l2 where l2.user_id = l.user_id and l2.created_at < l.created_at and l2.event_name = 'payment-invoice-created' limit 1
    )
    and s.created_at > DATE_SUB(NOW(), INTERVAL 8 DAY)
    and s.status = 'canceled'
    and TIMESTAMPDIFF(DAY,s.created_at, ends_at) >=2
  having order_dt is null or order_dt > log_dt
)
select user_id from trial_logs