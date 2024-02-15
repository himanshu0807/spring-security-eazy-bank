--------- POSTGRESQL DDL & DLL Script ---------
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(500) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

create unique index ix_auth_username on authorities (username,authority);

INSERT INTO USERS
VALUES ('happy', '12345', true)
ON CONFLICT DO NOTHING;

INSERT INTO AUTHORITIES
VALUES ('happy', 'write')
ON CONFLICT DO NOTHING;

------- For Custom JdbcUserDetailManager --------
CREATE TABLE customer (
  id SERIAL PRIMARY KEY,
  email VARCHAR(45) NOT NULL,
  pwd VARCHAR(200) NOT NULL,
  role VARCHAR(45) NOT NULL
);
