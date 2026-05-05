    select
    auction
from auctionsparser.tbl_overall_stats
where date(update_time) = CURDATE() - interval 1 day
  and auction in (
        'cargurus',
        'carmax',
        'carscom',
        'dealercarsearch',
        'dealercom',
        'ebay',
        'kbb',
        'dealercenter',
        'dealeron',
        'carvana',
        'autotrader'
  )
group by auction
having sum(qty_autocheck) = 0;
