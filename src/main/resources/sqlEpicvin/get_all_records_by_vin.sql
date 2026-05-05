select id, created_at,count_images
from tbl_cars
where vin = ?
and auction not in (${EXCLUDED_AUCTIONS})
and count_images > 0