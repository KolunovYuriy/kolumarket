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
                          product_count int,
                          category_id bigint REFERENCES categories (id),
                          created_at timestamp default current_timestamp,
                          updated_at timestamp default current_timestamp
);
INSERT INTO products (id,title, price, category_id, product_count) VALUES
(1,'Sugar', 10, 1, 150),
(2,'Salt', 50, 1, 100),
(3,'Bread', 200, 1, 100),
(4,'Batter', 300, 1, 100),
(5,'Chocolate', 1000, 1, 100),
(6,'Milk', 500, 1, 100),
(7,'Cheese', 700, 1, 100),
(8,'Cake', 2000, 1, 100),
(9,'Ice cream', 900, 1, 100),
(10,'Fish', 3000, 1, 100),
(11,'Pasta', 100, 1, 100),
(12,'TV', 10000, 2, 100),
(13,'Toaster', 4000, 2, 10),
(14,'Vacuum cleaner', 7000, 2, 10),
(15,'Gum', 10, 1, 10),
(16,'Chicken', 3500, 1, 10),
(17,'Beef', 3500, 1, 10),
(18,'Pork', 3500, 1, 10),
(19,'Lamp', 2000, 2, 10),
(20,'Flashlight', 500, 2, 10)
;