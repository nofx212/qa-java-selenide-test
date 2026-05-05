    select
    auction
from auctionsparser.tbl_overall_stats
where date(update_time) = CURDATE() - interval 1 day
  and auction not in (
      'manheim',
      'manheim2',
      'iaaica',
      'adesa',
      'ss',
      'getauto',
      'hemmings',
      -- исключаем источники ниже (несколько лотов в день или почти все без фото. Проверяем в get_last_photo)
      'salvagenow',
      'carzz',
      'autogielda',
      'tiriacauto'
  )
group by auction
having sum(qty_with_img) = 0;