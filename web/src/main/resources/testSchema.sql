create table IF NOT EXISTS gift_certificate
(
    id               serial not null primary key,
    name             varchar(100) unique,
    description      varchar(100),
    price            numeric NOT NULL DEFAULT 0,
    duration         int not null DEFAULT 2,
    create_date      timestamp not null DEFAULT NOW(),
    last_update_date timestamp not null DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS tag
(
    id   serial not null primary key,
    name varchar(100) unique
);

CREATE TABLE IF NOT EXISTS gift_certificate_tag
(
    gift_certificate_id int REFERENCES gift_certificate (id) ON UPDATE CASCADE ON DELETE CASCADE,
    tag_id              int REFERENCES tag (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users
(
    id                  serial not null primary key,
    name                varchar(100) unique,
    password            varchar(100),
    is_active           boolean DEFAULT true,
    registration_date   timestamp not null DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS orders
(
    id                  serial not null primary key,
    order_date          timestamp not null DEFAULT NOW(),
    price               numeric NOT NULL DEFAULT 0,
    status              varchar(100) NOT NULL,
    gift_certificate_id int REFERENCES gift_certificate (id),
    user_id             int REFERENCES users (id)
);