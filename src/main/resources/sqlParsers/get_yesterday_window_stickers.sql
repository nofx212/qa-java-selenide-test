    select
    auction
from auctionsparser.tbl_overall_stats
where date(update_time) = CURDATE() - interval 1 day
  and auction in (
       'dealercom',
       'dealeron',
--       'autotrader', - очень мало лотов, стикеров нет
       'carfax',
       'clutchca'
--       'getauto' - не работает искходныех сайт
--       'carscom', - пропали на ресуерсе
--       'carstory', - пропали на ресуерсе
--       'dealercarsearch', - пропали на ресуерсе
--       'usedcars', - пропали на ресуерсе
--       'carsforsale', - пропали на ресуерсе
--       'dealercenter', - пропали на ресуерсе
  )
group by auction
having sum(qty_sticker) = 0;