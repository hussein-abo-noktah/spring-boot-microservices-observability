INSERT INTO category (name, description)
SELECT 'Electronics', 'Devices and accessories used for day to day testing'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Electronics'
);

INSERT INTO category (name, description)
SELECT 'Home Office', 'Desk setup items for testing checkout and catalog flows'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Home Office'
);

INSERT INTO category (name, description)
SELECT 'Audio', 'Speakers and headphones for product demo scenarios'
WHERE NOT EXISTS (
    SELECT 1
    FROM category
    WHERE name = 'Audio'
);

INSERT INTO product (name, description, available_quantity, price, category_id)
SELECT
    'Mechanical Keyboard',
    'Hot-swappable mechanical keyboard with white backlight',
    25,
    89.99,
    (SELECT id FROM category WHERE name = 'Home Office' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1
    FROM product
    WHERE name = 'Mechanical Keyboard'
);

INSERT INTO product (name, description, available_quantity, price, category_id)
SELECT
    'USB-C Dock',
    '8-in-1 USB-C dock for external monitor and ethernet testing',
    18,
    129.50,
    (SELECT id FROM category WHERE name = 'Electronics' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1
    FROM product
    WHERE name = 'USB-C Dock'
);

INSERT INTO product (name, description, available_quantity, price, category_id)
SELECT
    'Noise Cancelling Headphones',
    'Wireless over-ear headphones for audio product testing',
    12,
    199.00,
    (SELECT id FROM category WHERE name = 'Audio' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1
    FROM product
    WHERE name = 'Noise Cancelling Headphones'
);

INSERT INTO product (name, description, available_quantity, price, category_id)
SELECT
    '27-inch Monitor',
    '4K monitor used in local observability dashboard demos',
    9,
    329.99,
    (SELECT id FROM category WHERE name = 'Electronics' LIMIT 1)
WHERE NOT EXISTS (
    SELECT 1
    FROM product
    WHERE name = '27-inch Monitor'
);
