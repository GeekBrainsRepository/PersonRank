use personrank;

/*
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Путин');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Медведев');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Сурков');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Жириновский');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Кудрин');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Песков');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Зюганов');
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Пупкин'); 
commit;
*/

/*
DELETE FROM `personrank`.`persons` WHERE `id`='8';
commit;
*/

/*
update personrank.persons set name='Зюганов' where id = 2;
commit;
*/

/*
select * from persons;
*/

/*
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Путин', '1');
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Путина', '1');
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Путину', '1');
commit;
*/

/*
select * from persons;
select * from `personrank`.`keywords`;
*/

/*
INSERT INTO `personrank`.`person_page_rank` (`page_id`, `rank`) VALUES ('1', '2');
commit;
*/

/*
delete from person_page_rank;
commit;
*/


select * from persons;
select * from keywords;
select * from sites;
select * from pages;
select * from person_page_rank;

/*
select persons.name, keywords.name
from persons, keywords
where persons.id=keywords.person_id
order by persons.name;

select sites.name, pages.url
from sites, pages
where site_id=pages.site_id
order by sites.name;

select persons.name, person_page_rank.rank
from persons, person_page_rank
where persons.id=person_page_rank.person_id
order by persons.name;
*/

/*
select persons.name, sites.name, pages.url, person_page_rank.rank
from persons, sites, pages, person_page_rank
where 	sites.id=pages.site_id 
	and	persons.id=person_page_rank.person_id
    and	pages.id=person_page_rank.page_id	
order by persons.name;
*/
/*
INSERT INTO `personrank`.`persons` (`name`) VALUES ('Пупкин');
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Пупкин', '1');
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Пупкина', '1');
INSERT INTO `personrank`.`keywords` (`name`, `person_id`) VALUES ('Пупкину', '1');
commit;
*/

