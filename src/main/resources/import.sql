-- You can use this file to load seed data into the database using SQL statements
insert into User (id, firstName, lastName, phone_number) values (1, 'admin', 'first', '2125551212')  
insert into Principal (loginName, password, role, user_id) values ('admin', '21232f297a57a5a743894a0e4a801fc3', 'ADMIN', '1')  