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
    EmailAddress varchar(450) not null ,
    IDNumber varchar(400) not null ,
    feeId  varchar(20) not null,
    sNumber  varchar(15) not null ,
    constraint foreign key(feeId) references membershipFee(fee_id) on update cascade on delete cascade,
    constraint foreign key (sNumber) references librarian (sNumber) on update cascade on delete cascade
);


create  table bookRack(
    rackCode varchar(10) primary key ,
    qtyBooks int not null ,
    categoryOfBooks varchar(50) not null,
    nameOfBooks text not null
);


create table reservation(
    reservationId varchar(10) primary key ,
    borrowedDate date not null ,
    dueDate date,
    bookReturnDate date ,
    fineStatus text not null ,
    fineAmount double not null ,
    mid varchar(10),
    ISBN varchar(10),
    constraint foreign key(mid) references member(mid) on update cascade on delete cascade,
    constraint  foreign key(ISBN) references book(ISBN) on update cascade on delete cascade
);


create table author(
    authorId varchar(10) primary key ,
    authorName varchar(500) not null ,
    text varchar(100) not null ,
    nationality varchar(200) not null ,
    currentlyBooksWrittenQty int not null
);


create  table book(
     ISBN varchar(10) primary key ,
     bookName varchar(100) not null ,
     category varchar(20) not null,
     qtyOnHand int not null,
     rackCode varchar(10),
     authorId varchar(10),
     constraint foreign key(rackCode) references bookRack(rackCode) on update cascade on delete cascade,
     constraint foreign key(authorId) references  author(authorId) on update cascade on delete cascade
);


create table supplier(
    supplierId varchar(10) primary key ,
    supplierName varchar(1000) not null,
    contactNumber varchar(15) not null,
    email varchar(500) not null
);


create table booksSupplier_detail(
    supplierId varchar(10) ,
    ISBN varchar(10) not null,
    bookName varchar(200) not null,
    qty int not null,
    supplierDate date,
    constraint foreign key(supplierId) references supplier(supplierId) on update cascade on delete cascade,
    constraint foreign key(ISBN) references book(ISBN) on update cascade on delete cascade
);


