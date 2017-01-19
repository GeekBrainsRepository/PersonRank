-- MySQL dump 10.13  Distrib 5.7.12, for osx10.9 (x86_64)
--
-- Host: localhost    Database: personrank
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `keywords`
--

DROP TABLE IF EXISTS `keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор ключевого слова',
  `name` varchar(2048) NOT NULL COMMENT 'Название ключевого слова',
  `person_id` int(11) NOT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово.\n\nЯвляется внешним ключом к таблице Persons.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `KeywordsPersonFK_idx` (`person_id`),
  CONSTRAINT `KeywordsPersonFK` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keywords`
--

LOCK TABLES `keywords` WRITE;
/*!40000 ALTER TABLE `keywords` DISABLE KEYS */;
INSERT INTO `keywords` VALUES (1,'Путин',1),(2,'Путина',1),(3,'Путину',1),(4,'Путиным',1),(5,'Медведев',2),(6,'Медведева',2),(7,'Медведеву',2),(8,'Медведевым',2),(9,'Навальный',3),(10,'Навального',3),(11,'Навальному',3),(12,'Навальным',3),(13,'Пупкин',5),(14,'Пупкина',5),(15,'Пупкину',5);
/*!40000 ALTER TABLE `keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pages`
--

DROP TABLE IF EXISTS `pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор страницы сайта',
  `url` varchar(2048) NOT NULL COMMENT 'Полный URL адрес страницы',
  `site_id` int(11) NOT NULL COMMENT 'Идентификатор сайта (ресурса), \nкоторый предоставлен  администратором для анализа. \n\nЯвляется внешним ключом к таблице Sites',
  `found_data_time` datetime NOT NULL COMMENT 'Дата и время обнаружения страницы системой',
  `last_scan_date` datetime NOT NULL COMMENT 'Дата и время последней проверки на упоминания',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `PagesSitesFK_idx` (`site_id`),
  CONSTRAINT `PagesSiteFK` FOREIGN KEY (`site_id`) REFERENCES `sites` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pages`
--

LOCK TABLES `pages` WRITE;
/*!40000 ALTER TABLE `pages` DISABLE KEYS */;
INSERT INTO `pages` VALUES (1,'http://lenta.ru/rubrics/world/',1,'2010-10-20 15:15:23','2003-04-20 15:15:23');
/*!40000 ALTER TABLE `pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person_page_rank`
--

DROP TABLE IF EXISTS `person_page_rank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person_page_rank` (
  `person_id` int(11) DEFAULT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово. \n\nЯвляется внешним ключом к таблице Persons.',
  `page_id` int(11) DEFAULT NULL COMMENT 'Идентификатор страницы сайта, \nна которой найдены упоминания о персонах. \n\nЯвляется внешним ключом к таблице Pages\n',
  `rank` int(11) DEFAULT NULL COMMENT 'Количество упоминаний личности на странице ',
  KEY `PersonPersonsPageRankFK_idx` (`person_id`),
  KEY `PagePersonPageRankFK_idx` (`page_id`),
  CONSTRAINT `PagePersonPageRankFK` FOREIGN KEY (`page_id`) REFERENCES `pages` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PersonPersonsPageRankFK` FOREIGN KEY (`person_id`) REFERENCES `persons` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person_page_rank`
--

LOCK TABLES `person_page_rank` WRITE;
/*!40000 ALTER TABLE `person_page_rank` DISABLE KEYS */;
INSERT INTO `person_page_rank` VALUES (1,1,2);
/*!40000 ALTER TABLE `person_page_rank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persons`
--

DROP TABLE IF EXISTS `persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persons` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор личности',
  `name` varchar(2048) NOT NULL COMMENT 'Наименование личности',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persons`
--

LOCK TABLES `persons` WRITE;
/*!40000 ALTER TABLE `persons` DISABLE KEYS */;
INSERT INTO `persons` VALUES (1,'Путин'),(2,'Медведев'),(3,'Навальный'),(5,'Пупкин');
/*!40000 ALTER TABLE `persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sites`
--

DROP TABLE IF EXISTS `sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sites` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор сайта',
  `name` varchar(2048) NOT NULL COMMENT 'Наименование сайта',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sites`
--

LOCK TABLES `sites` WRITE;
/*!40000 ALTER TABLE `sites` DISABLE KEYS */;
INSERT INTO `sites` VALUES (1,'lenta.ru');
/*!40000 ALTER TABLE `sites` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-19 14:37:33
