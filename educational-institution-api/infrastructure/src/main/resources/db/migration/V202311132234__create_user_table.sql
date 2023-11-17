create table if not exists user_profile
(
    id         varchar(36)                                         not null
        primary key,
    name       varchar(50)                                         not null,
    profile    enum ('ADMIN', 'COORDINATOR', 'STUDENT', 'TEACHER') not null,
    username   varchar(50)                                         not null
        UNIQUE,
    password   varchar(8)                                          not null,
    active     bit                                                 not null,
    created_at datetime(6)                                         not null,
    updated_at datetime(6)                                         not null,
    deleted_at datetime(6)                                         null
);

