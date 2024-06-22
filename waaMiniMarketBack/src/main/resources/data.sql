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


INSERT INTO public.images (id, link) VALUES (1, 'http://localhost:8081/uploads/cat-4.jpg');
INSERT INTO public.images (id, link) VALUES (2, 'http://localhost:8081/uploads/cat-5.jpg');
INSERT INTO public.images (id, link) VALUES (3, 'http://localhost:8081/uploads/cat-6.jpg');
INSERT INTO public.images (id, link) VALUES (4, 'http://localhost:8081/uploads/iphone.jpg');
INSERT INTO public.images (id, link) VALUES (5, 'http://localhost:8081/uploads/LAPTOP.jpg');
INSERT INTO public.images (id, link) VALUES (6, 'http://localhost:8081/uploads/TV.jpg');
INSERT INTO public.images (id, link) VALUES (7, 'http://localhost:8081/uploads/offer-2.png');

INSERT INTO public.products (id, best_seller, brand, category, color, compatibility, created_at, deleted, description, discount, features, material, model, name, new_arrival, on_sale, price, rating, size, stock_quantity, subcategory, updated_at, year, image_id, seller_id) VALUES (1, null, 'hp', 'Electronics', null, null, '2024-06-22 12:50:11.638129', null, 'great camera', null, null, null, null, 'camera', null, null, 333, null, null, 333, 'camera', '2024-06-22 12:50:11.638129', null, 1, 200);
INSERT INTO public.products (id, best_seller, brand, category, color, compatibility, created_at, deleted, description, discount, features, material, model, name, new_arrival, on_sale, price, rating, size, stock_quantity, subcategory, updated_at, year, image_id, seller_id) VALUES (2, null, 'gucci', 'Accessory', null, null, '2024-06-22 12:51:12.969732', null, 'leather bag', null, null, null, null, 'Bag', null, null, 320, null, null, 3, 'bag', '2024-06-22 12:51:12.969732', null, 2, 200);
INSERT INTO public.products (id, best_seller, brand, category, color, compatibility, created_at, deleted, description, discount, features, material, model, name, new_arrival, on_sale, price, rating, size, stock_quantity, subcategory, updated_at, year, image_id, seller_id) VALUES (3, null, 'samsung', 'Electronics', null, null, '2024-06-22 12:51:56.129023', null, 'Monitor', null, null, null, null, 'TV', null, null, 2938, null, null, 8, 'tv', '2024-06-22 12:51:56.129023', null, 6, 200);

INSERT INTO public.reviews (id, comment, created_at, rating, updated_at, buyer_id, product_id) VALUES (1, 'worst bag ever', '2024-06-22 13:12:10.781457', 3, '2024-06-22 13:12:10.781457', 300, 2);
