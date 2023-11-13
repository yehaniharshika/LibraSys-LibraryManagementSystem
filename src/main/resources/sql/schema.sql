drop database if exists LibraSys;
create database if not exists LibraSys;
use LibraSys;

create table librarian(
    sNumber varchar(15) primary key ,
    firstName varchar(200) not null,
    lastName varchar(250) not null,
    nic varchar(20) not null ,
    eAddress text not null ,
    username varchar(30) not null ,
    pw varchar(20) not null
);


create table membershipFee(
    fee_id varchar(20) primary key ,
    name varchar(100) not null ,
    amount double not null,
    date date not null,
    status varchar(100)
);


create  table member(
    mid varchar(10) primary key ,
    name varchar(200) not null ,
    address varchar(150) not null,
    gender  varchar(25)  not null,
    tel   varchar(15) not null ,
    feeId  varchar(20) not null,
    sNumber  varchar(10) not null ,
    constraint foreign key(feeId) references membershipFee(fee_id) on update cascade on delete cascade,
    constraint foreign key (sNumber) references librarian (sNumber) on delete cascade on update cascade
);


create  table bookRack(
    rackCode varchar(10) primary key ,
    qtyBooks int not null ,
    categoryOfBooks varchar(50) not null,
    nameOfBooks text not null
);

insert  into bookRack values ("R001",20,"Novel","gamperaliya,kaliyugaya,yuganthaya,lamakaw Kalaba,Madoldoowa,GuruGeethaya,GiripuraAththo,Mahawesi,Adara Andaraya,Awwa,Holman Walawwa");


create  table book(
    ISBN varchar(10) primary key ,
    bookName varchar(100) not null ,
    category varchar(20) not null,
    qtyOnHand int not null,
    rackCode varchar(10),
    constraint foreign key(rackCode) references bookRack(rackCode) on update cascade on delete cascade
);

create table reservation(
    reservationId varchar(10) primary key ,
    borrowedDate date not null ,
    dueDate date,
   bookReturnDate date ,
    memberId varchar(10),
    ISBN varchar(10),
    constraint foreign key(memberId) references member(mId) on update cascade on delete cascade,
    constraint  foreign key(ISBN) references book(ISBN) on update cascade on delete cascade
);

create table author(
    author_id varchar(10) primary key ,
    author_name varchar(100) not null ,
    authorsBook varchar(10000) not null
);

create  table bookAuthor_detail(
    author_id varchar(10),
    ISBN varchar(10),
    bookName varchar(100) not null ,
    authorName varchar(100) not null,
    constraint foreign key(author_id) references author(author_id) on update cascade on delete cascade ,
    constraint foreign key(ISBN) references book(ISBN) on update cascade on delete cascade
);

create table supplier(
    supplierId varchar(10) primary key ,
    supplierName varchar(100) not null,
    telNumber varchar(15) not null
);

create table bookSupplier_detail(
    supplierId varchar(10) not null ,
    ISBN varchar(10) not null,
    qty int not null,
    constraint foreign key(supplierId) references supplier(supplierId) on update cascade on delete cascade,
    constraint foreign key(ISBN) references book(ISBN) on update cascade on delete cascade
);

insert into membershipFee values("F004","Nayanathara",100,"anually",'2023-1-3');

insert into member values ("M001","Nayanathara","Panadura","Mala",0764450928,"SN001","F004");