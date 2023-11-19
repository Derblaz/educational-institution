create table if not exists course
(
    id              varchar(255) not null
        primary key,
    name            varchar(255) not null,
    description     varchar(255) null,
    months_duration int          not null,
    places          int          not null,
    active          bit          not null,
    created_at      datetime(6)  not null,
    deleted_at      datetime(6)  null,
    updated_at      datetime(6)  not null
);

create table if not exists courses_disciplines
(
    course_id      varchar(255) not null,
    discipline_id  varchar(255) not null,
    primary key (course_id, discipline_id),
    constraint FK3ecmc1kg8xtfju8pf6stuh1ow
        foreign key (course_id) references course (id),
    semester_index int          null
);