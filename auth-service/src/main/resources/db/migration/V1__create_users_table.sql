CREATE TABLE users (
                       id          SERIAL PRIMARY KEY,
                       username    VARCHAR(50) NOT NULL UNIQUE,
                       password    VARCHAR(100) NOT NULL,
                       email       VARCHAR(100) NOT NULL UNIQUE,
                       first_name  VARCHAR(50),
                       last_name   VARCHAR(50),
                       role        VARCHAR(20) NOT NULL
);
