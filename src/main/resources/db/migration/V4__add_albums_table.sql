create table if not exists albums
(
    id         bigint unsigned auto_increment comment '앨범 고유 ID'
        primary key,
    name       varchar(20) not null,
    created_at datetime    not null default current_timestamp,
    updated_at datetime    not null default current_timestamp,
    user_id    bigint      not null
);

create table if not exists albums_photos
(
    album_id bigint,
    photo_id bigint,
    constraint pk_albums_photos
        primary key (album_id, photo_id)
)
