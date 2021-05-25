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
                             product_name VARCHAR(255),
                             count int,
                             FOREIGN KEY (order_id) REFERENCES orders (id));