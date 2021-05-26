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