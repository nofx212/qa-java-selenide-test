 select
    auction
from auctionsparser.tbl_overall_stats
where date(update_time) = curdate() - interval 1 day
  AND auction IN (
        'carsforsale',
        'dealercarsearch',
        'dealercenter',
        'dealercom',
        'dealeron',
        'driveway',
        'usedcars',
--        'carstory',
        'ebay',
        'autotraderca',
        'kijijiautos',
        'goauto',
        'clutchca',
        'carfax',
        'carvana',
        'cardirect',
        'carpages'
  )
GROUP BY auction
HAVING sum(
        CASE
            WHEN auction IN ('carsforsale', 'dealercarsearch','dealercenter','dealercom','dealeron','driveway','usedcars','carstory','ebay','carfax','carvana','cardirect') THEN qty_carfax
            WHEN auction IN ('autotraderca','kijijiautos','goauto','clutchca','carpages') THEN qty_carfax_ca
            ELSE 0
        END
    ) = 0;
