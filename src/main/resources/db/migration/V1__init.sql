create table if not exists users
(
    id         bigint unsigned auto_increment comment '사용자 고유 ID'
        primary key,
    username   varchar(30) not null unique,
    name       varchar(30) not null,
    created_at datetime    not null default current_timestamp,
    updated_at datetime    not null default current_timestamp
);

create table if not exists photos
(
    id          bigint unsigned auto_increment comment '사진 고유 ID'
        primary key,
    file_name   text     not null,
    file_path   text     not null,
    captured_dt datetime not null,
    location    point    not null,
    user_id     bigint   not null,
    created_at  datetime not null default current_timestamp
)
