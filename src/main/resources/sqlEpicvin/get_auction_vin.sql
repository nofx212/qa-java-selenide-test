select vin
from tbl_cars
where auction in ('copart', 'copartca', 'iaai', 'iaaica')
  and count_images > 1
  and vin is not null
  and vin != ''
order by id desc
limit 1;