create table if not exists contacts(
    id bigserial not null primary key,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    email varchar(100) not null,
    phone varchar(100)
);