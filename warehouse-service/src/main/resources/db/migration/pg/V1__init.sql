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
                               FOREIGN KEY (warehouse_id) REFERENCES warehouses (id));
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
(2,1,50),
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