CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255),
   email VARCHAR(255) UNIQUE NOT NULL,
   avatar VARCHAR(255)

);