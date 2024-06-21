INSERT INTO users (id, email, firstname, lastname, password)
VALUES (100, 'kira@miu.edu', 'kira', 'mengesha', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, firstname, lastname, password)
VALUES (200, 'john@miu.edu', 'john', 'haile', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, firstname, lastname, password)
VALUES (300, 'erick@miu.edu', 'erick', 'mgongo', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123

INSERT INTO role (id, role)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role)
VALUES (2, 'SELLER');
INSERT INTO role (id, role)
VALUES (3, 'BUYER');

--
INSERT INTO users_roles (user_id, roles_id)
VALUES (100, 1);
INSERT INTO users_roles (user_id, roles_id)
VALUES (200, 2);
INSERT INTO users_roles (user_id, roles_id)
VALUES (300, 3);
--
-- INSERT INTO product (id, name, price, id_user)
-- VALUES (1, 'iPhone', 1200, 1);
-- INSERT INTO product (id, name, price, id_user)
-- VALUES (2, 'iPad', 900, 1);
-- INSERT INTO product (id, name, price, id_user)
-- VALUES (3, 'Pen', 5, 1);


-- -- Insert into users
-- INSERT INTO users (id, username, email, password, role)
-- VALUES (1, 'uinan', 'uinan@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'ADMIN'); -- 123
--
-- INSERT INTO users (id, username, email, password, role)
-- VALUES (2, 'jdoe', 'john@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'SELLER'); -- 123
--
-- INSERT INTO users (id, username, email, password, role)
-- VALUES (3, 'daltarawneh', 'dean@miu.edu', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2', 'BUYER'); -- 123
--
-- -- Insert into roles (if using a separate roles table for one-to-one relationship)
-- INSERT INTO role (id, role)
-- VALUES (1, 'ADMIN');
--
-- INSERT INTO role (id, role)
-- VALUES (2, 'SELLER');
--
-- INSERT INTO role (id, role)
-- VALUES (3, 'BUYER');
--
-- -- Insert into role assignments (if using a many-to-many relationship)
-- -- Assuming a join table named users_roles
-- INSERT INTO users_roles (user_id, role_id)
-- VALUES (1, 1);
--
-- INSERT INTO users_roles (user_id, role_id)
-- VALUES (2, 2);
--
-- INSERT INTO users_roles (user_id, role_id)
-- VALUES (3, 3);
--
-- -- Insert into sellers (assuming user 2 is a seller)
-- INSERT INTO sellers (id, approved, store_name, store_description)
-- VALUES (2, true, 'John\'s Electronics', 'A trusted seller of quality electronic products.');
--
-- -- Insert into buyers (assuming user 3 is a buyer)
-- INSERT INTO buyers (id, shipping_address, billing_address)
-- VALUES (3, '123 Main St, Springfield, IL', '123 Main St, Springfield, IL');
--
-- -- Insert into products
-- INSERT INTO products (id, seller_id, name, description, price, stock_quantity, category, subcategory, brand, created_at, updated_at, deleted)
-- VALUES (1, 2, 'iPhone', 'Latest model of iPhone', 1200, 100, 'Electronics', 'Mobile Phones', 'Apple', NOW(), NOW(), false);
--
-- INSERT INTO products (id, seller_id, name, description, price, stock_quantity, category, subcategory, brand, created_at, updated_at, deleted)
-- VALUES (2, 2, 'iPad', 'Latest model of iPad', 900, 50, 'Electronics', 'Tablets', 'Apple', NOW(), NOW(), false);
--
-- INSERT INTO products (id, seller_id, name, description, price, stock_quantity, category, subcategory, brand, created_at, updated_at, deleted)
-- VALUES (3, 2, 'Pen', 'Smooth writing pen', 5, 500, 'Stationery', 'Writing Instruments', 'Pilot', NOW(), NOW(), false);
