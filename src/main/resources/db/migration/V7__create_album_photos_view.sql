create view vw_album_photos
as
select ap.id,
       p.id as photo_id,
       p.file_name,
       p.file_path,
       p.thumbnail_path,
       p.captured_dt,
       p.location,
       ap.album_id,
       p.user_id
from photos p
         left outer join albums_photos ap on ap.photo_id = p.id
