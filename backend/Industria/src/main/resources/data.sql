


INSERT INTO raw_material (name, stock_quantity) VALUES ('Plastico', 1000.00);
INSERT INTO raw_material (name, stock_quantity) VALUES ('Algodao', 800.00);
INSERT INTO raw_material (name, stock_quantity) VALUES ('Borracha', 1200.00);
INSERT INTO raw_material (name, stock_quantity) VALUES ('Couro Sintetico', 300.00);
INSERT INTO raw_material (name, stock_quantity) VALUES ('Fibra de Carbono', 50.00);
INSERT INTO raw_material (name, stock_quantity) VALUES ('Metal Leve', 200.00);


INSERT INTO product (name, code, price) VALUES ('Tenis de Corrida Luxo', 'RUN-99', 500.00);
INSERT INTO product (name, code, price) VALUES ('Bola de Futebol Profissional', 'BALL-01', 150.00);
INSERT INTO product (name, code, price) VALUES ('Chuteira de Carbono', 'BOOT-CB', 800.00);
INSERT INTO product (name, code, price) VALUES ('Camiseta Dry-Fit', 'TSHIRT-01', 80.00);
INSERT INTO product (name, code, price) VALUES ('Caneleira Basica', 'PROT-01', 30.00);
INSERT INTO product (name, code, price) VALUES ('Garrafa Termica', 'BOTT-01', 45.00);
INSERT INTO product (name, code, price) VALUES ('Mochila Esportiva', 'BACK-01', 220.00);
INSERT INTO product (name, code, price) VALUES ('Raquete de Tenis Leve', 'RACK-01', 600.00);

-- 4. Associações (ProductMaterial) usando Subqueries
-- Isso garante que funcione mesmo sem sabermos o ID gerado pelo banco

-- Chuteira de Carbono
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BOOT-CB'), (SELECT id FROM raw_material WHERE name = 'Fibra de Carbono'), 5.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BOOT-CB'), (SELECT id FROM raw_material WHERE name = 'Couro Sintetico'), 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BOOT-CB'), (SELECT id FROM raw_material WHERE name = 'Borracha'), 1.00);

-- Raquete de Tenis Leve
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'RACK-01'), (SELECT id FROM raw_material WHERE name = 'Fibra de Carbono'), 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'RACK-01'), (SELECT id FROM raw_material WHERE name = 'Metal Leve'), 3.00);

-- Tenis de Corrida Luxo
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'RUN-99'), (SELECT id FROM raw_material WHERE name = 'Borracha'), 5.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'RUN-99'), (SELECT id FROM raw_material WHERE name = 'Algodao'), 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'RUN-99'), (SELECT id FROM raw_material WHERE name = 'Plastico'), 1.00);

-- Bola de Futebol
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BALL-01'), (SELECT id FROM raw_material WHERE name = 'Couro Sintetico'), 4.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BALL-01'), (SELECT id FROM raw_material WHERE name = 'Algodao'), 3.00);

-- Camiseta Dry-Fit
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'TSHIRT-01'), (SELECT id FROM raw_material WHERE name = 'Algodao'), 0.50);

-- Garrafa Termica
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BOTT-01'), (SELECT id FROM raw_material WHERE name = 'Plastico'), 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BOTT-01'), (SELECT id FROM raw_material WHERE name = 'Metal Leve'), 1.00);

-- Mochila Esportiva
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BACK-01'), (SELECT id FROM raw_material WHERE name = 'Couro Sintetico'), 3.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BACK-01'), (SELECT id FROM raw_material WHERE name = 'Plastico'), 2.00);
INSERT INTO product_material (product_id, raw_material_id, required_quantity)
VALUES ((SELECT id FROM product WHERE code = 'BACK-01'), (SELECT id FROM raw_material WHERE name = 'Algodao'), 5.00);