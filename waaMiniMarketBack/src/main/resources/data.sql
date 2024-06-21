-- INSERT INTO users (id, email, firstname, lastname, password)
-- VALUES (100, 'kira@miu.edu', 'kira', 'mengesha', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
-- INSERT INTO users (id, email, firstname, lastname, password)
-- VALUES (200, 'john@miu.edu', 'john', 'haile', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
-- INSERT INTO users (id, email, firstname, lastname, password)
-- VALUES (300, 'erick@miu.edu', 'erick', 'mgongo', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
--
-- INSERT INTO role (id, role)
-- VALUES (1, 'ADMIN');
-- INSERT INTO role (id, role)
-- VALUES (2, 'SELLER');
-- INSERT INTO role (id, role)
-- VALUES (3, 'BUYER');
--

-- Insert users into the users table
INSERT INTO users (id, email, firstname, lastname, password)
VALUES (100, 'kira@miu.edu', 'kira', 'mengesha', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, firstname, lastname, password)
VALUES (200, 'john@miu.edu', 'john', 'haile', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123
INSERT INTO users (id, email, firstname, lastname, password)
VALUES (300, 'erick@miu.edu', 'erick', 'mgongo', '$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2'); --123

-- Insert roles into the role table
INSERT INTO role (id, role)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role)
VALUES (2, 'SELLER');
INSERT INTO role (id, role)
VALUES (3, 'BUYER');

-- Assign roles to users
INSERT INTO users_roles (user_id, roles_id)
VALUES (100, 1);
INSERT INTO users_roles (user_id, roles_id)
VALUES (200, 2);
INSERT INTO users_roles (user_id, roles_id)
VALUES (300, 3);

-- Insert sellers into the sellers table
--
INSERT INTO sellers (id, approved, store_name, store_description)
VALUES (200, true, 'John Store', 'We sell all kinds of electronics and gadgets');
--
-- -- Insert buyers into the buyers table
INSERT INTO buyers (id, shipping_address, billing_address, credit_card_id)
VALUES (300, '123 Main St', '123 Main St', NULL); -- Assuming no credit card information
