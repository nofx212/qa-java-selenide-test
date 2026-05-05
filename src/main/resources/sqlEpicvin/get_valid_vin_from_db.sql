select vin
from tbl_cars
where auction not in (
  'manheim',
  'manheim2',
  'iaai',
  'otomoto',
  'autogielda',
  'gratkapl',
  'iaaica',
  'sauto',
  'cargurusuk',
  'edgepipeline',
  'ebay'
)
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