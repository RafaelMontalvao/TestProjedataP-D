-- 1. Limpeza
DELETE FROM product_material;
DELETE FROM product;
DELETE FROM raw_material;

-- 2. Matérias-Primas (Raw Materials)
INSERT INTO raw_material (id, name, stock_quantity) VALUES (1, 'Plastico', 1000.00);
INSERT INTO raw_material (id, name, stock_quantity) VALUES (2, 'Algodao', 800.00);
INSERT INTO raw_material (id, name, stock_quantity) VALUES (3, 'Borracha', 1200.00);
INSERT INTO raw_material (id, name, stock_quantity) VALUES (4, 'Couro Sintetico', 300.00);
INSERT INTO raw_material (id, name, stock_quantity) VALUES (5, 'Fibra de Carbono', 50.00);
INSERT INTO raw_material (id, name, stock_quantity) VALUES (6, 'Metal Leve', 200.00);

-- 3. Produtos (Products)
INSERT INTO product (id, name, code, price) VALUES (1, 'Tenis de Corrida Luxo', 'RUN-99', 500.00);
INSERT INTO product (id, name, code, price) VALUES (2, 'Bola de Futebol Profissional', 'BALL-01', 150.00);
INSERT INTO product (id, name, code, price) VALUES (3, 'Chuteira de Carbono', 'BOOT-CB', 800.00);
INSERT INTO product (id, name, code, price) VALUES (4, 'Camiseta Dry-Fit', 'TSHIRT-01', 80.00);
INSERT INTO product (id, name, code, price) VALUES (5, 'Caneleira Basica', 'PROT-01', 30.00);
INSERT INTO product (id, name, code, price) VALUES (6, 'Garrafa Termica', 'BOTT-01', 45.00);
INSERT INTO product (id, name, code, price) VALUES (7, 'Mochila Esportiva', 'BACK-01', 220.00);
INSERT INTO product (id, name, code, price) VALUES (8, 'Raquete de Tenis Leve', 'RACK-01', 600.00);

-- 4. Associações (ProductMaterial) - Agora todos com 'required_quantity'
-- Chuteira
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (3, 5, 5.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (3, 4, 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (3, 3, 1.00);

-- Raquete
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (8, 5, 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (8, 6, 3.00);

-- Tenis de Corrida
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (1, 3, 5.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (1, 2, 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (1, 1, 1.00);

-- Bola
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (2, 4, 4.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (2, 2, 3.00);

-- Camiseta
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (4, 2, 0.50);

-- Garrafa Termica (Corrigido aqui!)
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (6, 1, 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (6, 6, 1.00);

-- Mochila
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (7, 4, 3.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (7, 1, 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity) VALUES (7, 2, 5.00);