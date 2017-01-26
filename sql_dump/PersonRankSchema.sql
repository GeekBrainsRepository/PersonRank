
DROP TABLE IF EXISTS `keywords`;
 
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор ключевого слова',
  `name` varchar(2048) NOT NULL COMMENT 'Название ключевого слова',
  `person_id` int(11) NOT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово.\n\nЯвляется внешним ключом к таблице Persons.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `KeywordsPersonFK_idx` (`person_id`)
  
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

 
  

DROP TABLE IF EXISTS `pages`;
 
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор страницы сайта',
  `url` varchar(2048) NOT NULL COMMENT 'Полный URL адрес страницы',
  `site_id` int(11) NOT NULL COMMENT 'Идентификатор сайта (ресурса), \nкоторый предоставлен  администратором для анализа. \n\nЯвляется внешним ключом к таблице Sites',
  `found_data_time` datetime NOT NULL COMMENT 'Дата и время обнаружения страницы системой',
  `last_scan_date` datetime NOT NULL COMMENT 'Дата и время последней проверки на упоминания',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `PagesSitesFK_idx` (`site_id`)
 
) ENGINE=InnoDB   DEFAULT CHARSET=utf8;
 

DROP TABLE IF EXISTS `person_page_rank`;
 
CREATE TABLE `person_page_rank` (
  `person_id` int(11) DEFAULT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово. \n\nЯвляется внешним ключом к таблице Persons.',
  `page_id` int(11) DEFAULT NULL COMMENT 'Идентификатор страницы сайта, \nна которой найдены упоминания о персонах. \n\nЯвляется внешним ключом к таблице Pages\n',
  `rank` int(11) DEFAULT NULL COMMENT 'Количество упоминаний личности на странице ',
  KEY `PersonPersonsPageRankFK_idx` (`person_id`),
  KEY `PagePersonPageRankFK_idx` (`page_id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  


  
DROP TABLE IF EXISTS `persons`; 

CREATE TABLE `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор личности',
  `name` varchar(2048) NOT NULL COMMENT 'Наименование личности',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8;  

DROP TABLE IF EXISTS `sites`; 

CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор сайта',
  `name` varchar(2048) NOT NULL COMMENT 'Наименование сайта',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB   DEFAULT CHARSET=utf8; 


ALTER TABLE  `keywords` 
 ADD CONSTRAINT `KeywordsPersonFK` 
 FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
 ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE  `pages` 
ADD  CONSTRAINT `PagesSiteFK` 
FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) 
ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE  `person_page_rank` 
ADD  CONSTRAINT `PagePersonPageRankFK` 
FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`) 
ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE  `person_page_rank` 
ADD  CONSTRAINT `PersonPersonsPageRankFK` 
FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`)
 ON DELETE CASCADE ON UPDATE CASCADE;