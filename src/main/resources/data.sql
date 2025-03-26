-- Insert sample insurance products
INSERT INTO INSURANCE (name, description, premium, coverage_amount, min_age, max_age, gender, min_income)
VALUES
    ('Young Health Plus', 'Health insurance for young adults', 5000.00, 500000.00, 18, 35, NULL, 200000.00),
    ('Family Protect', 'Comprehensive family health coverage', 10000.00, 1000000.00, 30, 55, NULL, 500000.00),
    ('Women Wellness', 'Specialized health insurance for women', 7500.00, 750000.00, 25, 50, 'FEMALE', 300000.00),
    ('Senior Care', 'Health insurance for seniors', 15000.00, 750000.00, 55, 75, NULL, 400000.00);