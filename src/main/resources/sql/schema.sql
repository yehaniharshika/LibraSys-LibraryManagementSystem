drop database if exists LibraSys;
create database if not exists LibraSys;

create table membershipFee(
    fee_id varchar(10) primary key ,
    name varchar(100) not null ,
    amount double not null,
    date date not null ,
    status varchar(100)
);

