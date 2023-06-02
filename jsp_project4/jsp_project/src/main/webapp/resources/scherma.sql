--DB--
create user 'jspuser2'@'localhost' identified by 'mysql';
create database jsp2db;

--member--
create table member(
id varchar(100) not null,
password varchar(100) not null,
email varchar(100) not null,
age int,
reg_date datetime default now(),
last_login datetime,
primary key(id));

--board--
create table board(
bno int not null auto_increment,
title varchar(100) not null,
writer varchar(100) not null,
content text,
reg_date datetime default now(),
readCount int default 0,
primary key(bno));

--comment

create table comment(
cno int not null auto_increment,
bno int default 0,
writer varchar(100) default “unknown”,
content varchar(1000) not null,
reg_date datetime default now(), 
primary key(cno)
);

--board에 추가
alter table board add image_file text;

