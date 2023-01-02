create database game;
alter database game character set utf8;
create table `user` 
(
	`userName` varchar(20) primary key,
    `nowRoomId` int not null,
    `capacity` int not null
);
drop table `user`;


delimiter $
create procedure `save_user`(in nme varchar(20),in id int,in cap int,out res int)
begin
	if((select `userName` from `user` where `userName`=nme) is null) 
    then 
    insert into `user` values(nme,id,cap);
    set res=1;
    else
    set res=0;
    end if;
end$
delimiter ;
#drop procedure `save_user`;
call save_user('Raven',1,8,@res);
select @res;
select * from `user`;

delimiter $
create procedure `update_user`(in nme varchar(20),in id int,in cap int)
begin
	update `user`
    set `nowRoomId`=id,`capacity`=cap
    where `userName`=nme;
end$
delimiter ;
drop procedure update_user;
call update_user('Raven',1,18);
