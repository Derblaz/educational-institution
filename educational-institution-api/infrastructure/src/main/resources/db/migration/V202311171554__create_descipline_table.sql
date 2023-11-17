create table if not exists discipline
(
    active      bit          not null,
    credits     double       not null,
    presential  bit          not null,
    created_at  datetime(6)  not null,
    deleted_at  datetime(6)  null,
    updated_at  datetime(6)  not null,
    description varchar(255) null,
    id          varchar(36)  not null
        primary key,
    name        varchar(100) not null,
    program     text         null
);