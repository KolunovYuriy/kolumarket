create table role_table
(
    id   serial      not null
        constraint role_table_pk
            primary key,
    name varchar(20) not null
);

create table user_table
(
    id       serial not null
        constraint user_table_pk
            primary key,
    login    varchar(50) not null,
    password varchar(500) not null,
    email    varchar(50) unique,
    role_id  integer
        constraint user_table_role_table_id_fk
            references role_table
);

create
unique index user_table_login_uindex
    on user_table (login);

insert into role_table(id, name) values (1, 'ROLE_ADMIN');
insert into role_table(id, name) values (2, 'ROLE_USER');

insert into user_table (id, login, password, email, role_id)
values
(1, 'user1', '$2a$10$XBF043cDt.u2kbhOllqYJeP9vId6j4KNqOgUFPUl9mh28ejBjY7WO', 'user1@gmail.com', 1), -- Q1w2e3
(2, 'user2', '$2a$10$amgTQTk4P97KHInX0LYQBu1pFdM2EJEZD6SlVgYleukXvE9DML5/y', 'user2@gmail.com', 2); -- qazwsx

