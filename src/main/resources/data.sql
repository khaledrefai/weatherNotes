INSERT  INTO roles VALUES (1,'USER');
INSERT  INTO roles VALUES (2,'ADMIN');
insert into users(user_id ,  active, name, mobile  ,password ,user_name)
                values (1,true,'user  full name','0527444302' ,
                          '$2a$10$0b7nsrtZZWK7VOJ.0RVkkuGcEjZt/ihxPxb1UsI9pvdgh3XINrtSW',
                          'user@test.com');

insert into users(user_id ,  active, name, mobile  ,password ,user_name)
values (2,true,'admin full name','0527444302' ,
        '$2a$10$0b7nsrtZZWK7VOJ.0RVkkuGcEjZt/ihxPxb1UsI9pvdgh3XINrtSW',
        'admin@test.com');

insert into USER_ROLE values (1,1);
insert into USER_ROLE values (2,2);

insert into range(range_id,mintemp,maxtemp) values(1,1,10);
insert into range(range_id,mintemp,maxtemp) values(2,10 ,15);
insert into range(range_id,mintemp,maxtemp) values(3,15,20);
insert into range(range_id,mintemp, maxtemp) values(4,20,100);
