CREATE USER platops WITH PASSWORD 'platops';
CREATE DATABASE manager_user;
CREATE DATABASE manager_order;
CREATE DATABASE manager_product;
GRANT ALL PRIVILEGES ON DATABASE manager_user TO platops;
GRANT ALL PRIVILEGES ON DATABASE manager_order TO platops;
GRANT ALL PRIVILEGES ON DATABASE manager_product TO platops;