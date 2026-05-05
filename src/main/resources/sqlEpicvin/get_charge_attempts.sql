select
    i.id
from tbl_invoices i
left join tbl_charges c
    on c.invoice_id = i.id
   and c.parent_id is null
where i.status = 'unpaid'
group by i.id, i.attempt_count
having count(c.id) > i.attempt_count + 10;