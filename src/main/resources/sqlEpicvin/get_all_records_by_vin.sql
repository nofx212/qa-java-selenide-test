select id, created_at,count_images
from tbl_cars
where vin = ?
and auction not in (
  'manheim',
  'manheim2',
  'iaai',
  'iaaica',
  'edgepipeline'
)
and count_images > 0