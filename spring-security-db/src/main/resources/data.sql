
-- User entries

insert into users (username, password, enabled) values ('foo','foo',true);
insert into users (username, password, enabled) values ('admin','admin',true);

-- Authorities table
insert into authorities (username, authority) values ('foo','ROLE_USER');
insert into authorities (username, authority) values ('admin','ROLE_ADMIN');