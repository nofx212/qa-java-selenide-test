select vin
from tbl_cars
where auction not in (${EXCLUDED_AUCTIONS})
and make in (
'Audi',
'BMW',
'Ford',
'Toyota'
)
  and count_images > 1
  and vin != ''
  and vin is not null
order by id desc
limit 1000, 1