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

CREATE TABLE categories (
                          id bigserial PRIMARY KEY,
                          title VARCHAR(255)
);
INSERT INTO categories (id,title) VALUES
(1,'food'),
(2,'equipment')
;

CREATE TABLE products (
    id bigserial PRIMARY KEY,
    title VARCHAR(255),
    price int,
    category_id bigint REFERENCES categories (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);
INSERT INTO products (id,title, price, category_id) VALUES
(1,'Sugar', 10, 1),
(2,'Salt', 50, 1),
(3,'Bread', 200, 1),
(4,'Batter', 300, 1),
(5,'Chocolate', 1000, 1),
(6,'Milk', 500, 1),
(7,'Cheese', 700, 1),
(8,'Cake', 2000, 1),
(9,'Ice cream', 900, 1),
(10,'Fish', 3000, 1),
(11,'Pasta', 100, 1),
(12,'TV', 10000, 2),
(13,'Toaster', 4000, 2),
(14,'Vacuum cleaner', 7000, 2),
(15,'Gum', 10, 1),
(16,'Chicken', 3500, 1),
(17,'Beef', 3500, 1),
(18,'Pork', 3500, 1),
(19,'Lamp', 2000, 2),
(20,'Flashlight', 500, 2)
;

CREATE TABLE customers (
    id bigserial PRIMARY KEY,
    name VARCHAR(255),
    session_id VARCHAR(255),
    user_id int UNIQUE,
    FOREIGN KEY (user_id) REFERENCES user_table (id)
);
INSERT INTO customers (id,name, user_id) VALUES
(1,'Vasya',1),
(2,'Petya',2)
;

CREATE TABLE orders (
    id bigserial PRIMARY KEY,
    status VARCHAR(255),
    customer_id int,
    created_at timestamp default current_timestamp,
    FOREIGN KEY (customer_id) REFERENCES customers (id));

CREATE TABLE order_items (
    order_id int,
    product_id int,
    count int,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id));

