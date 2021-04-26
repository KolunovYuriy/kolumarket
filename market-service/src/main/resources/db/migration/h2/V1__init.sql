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
    user_id int UNIQUE
);
INSERT INTO customers (id,name, user_id) VALUES
(1,'Vasya',1),
(2,'Petya',2)
;

CREATE TABLE orders (
    id bigserial GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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

CREATE TABLE warehouses (
                             id bigserial PRIMARY KEY,
                             location VARCHAR(255)
                             );
INSERT INTO warehouses (id,location) VALUES
(1,'Moscow'),
(2,'Saint Petersburg')
;

CREATE TABLE product_items (
                            warehouse_id int,
                            product_id int,
                            count int,
                            FOREIGN KEY (warehouse_id) REFERENCES warehouses (id),
                            FOREIGN KEY (product_id) REFERENCES products (id));
INSERT INTO product_items (warehouse_id,product_id,count) VALUES
(1,1,100),
(1,3,100),
(1,5,100),
(1,7,100),
(1,9,100),
(1,11,100),
(1,13,10),
(1,15,10),
(1,17,10),
(1,19,10),
(2,2,100),
(2,4,100),
(2,6,100),
(2,8,100),
(2,10,100),
(2,12,100),
(2,14,10),
(2,16,10),
(2,18,10),
(2,20,10)
;