DROP DATABASE IF EXISTS `PersonRankDB`;
CREATE DATABASE  IF NOT EXISTS `PersonRankDB`; 
USE `PersonRankDB`;

-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: PersonRankDB
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
-- Table structure for table `Keywords`
--
DROP TABLE IF EXISTS `Keywords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Keywords` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор ключевого слова',
  `Name` varchar(2048) NOT NULL COMMENT 'Название ключевого слова',
  `PersonID` int(11) NOT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово.\n\nЯвляется внешним ключом к таблице Persons.',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `KeywordsPersonFK_IDx` (`PersonID`),
  CONSTRAINT `KeywordsPersonFK` FOREIGN KEY (`PersonID`) REFERENCES `Persons` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Keywords`
--

LOCK TABLES `Keywords` WRITE;
/*!40000 ALTER TABLE `Keywords` DISABLE KEYS */;
INSERT INTO `Keywords` VALUES (1,'Путин',1),(2,'Путина',1),(3,'Путину',1),(4,'Путиным',1),(5,'Медведев',2),(6,'Медведева',2),(7,'Медведеву',2),(8,'Медведевым',2),(9,'Навальный',3),(10,'Навального',3),(11,'Навальному',3),(12,'Навальным',3),(13,'Пупкин',5),(14,'Пупкина',5),(15,'Пупкину',5);
/*!40000 ALTER TABLE `Keywords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pages`
--

DROP TABLE IF EXISTS `Pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pages` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор страницы сайта',
  `Url` varchar(2048) NOT NULL COMMENT 'Полный Url адрес страницы',
  `SiteID` int(11) NOT NULL COMMENT 'Идентификатор сайта (ресурса), \nкоторый предоставлен  администратором для анализа. \n\nЯвляется внешним ключом к таблице Sites',
  `FoundDateTime` datetime NOT NULL COMMENT 'Дата и время обнаружения страницы системой',
  `LastScanDate` datetime NOT NULL COMMENT 'Дата и время последней проверки на упоминания',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `PagesSitesFK_IDx` (`SiteID`),
  CONSTRAINT `PagesSiteFK` FOREIGN KEY (`SiteID`) REFERENCES `Sites` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pages`
--

LOCK TABLES `Pages` WRITE;
/*!40000 ALTER TABLE `Pages` DISABLE KEYS */;
INSERT INTO `Pages` VALUES (1,'http://lenta.ru/rubrics/world/',1,'2010-10-20 15:15:23','2003-04-20 15:15:23');
/*!40000 ALTER TABLE `Pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PersonPageRank`
--

DROP TABLE IF EXISTS `PersonPageRank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PersonPageRank` (
  `PersonID` int(11) DEFAULT NULL COMMENT 'Идентификатор личности, \nкоторой соответствует данное ключевое слово. \n\nЯвляется внешним ключом к таблице Persons.',
  `PageID` int(11) DEFAULT NULL COMMENT 'Идентификатор страницы сайта, \nна которой найдены упоминания о персонах. \n\nЯвляется внешним ключом к таблице Pages\n',
  `Rank` int(11) DEFAULT NULL COMMENT 'Количество упоминаний личности на странице ',
  KEY `PersonPersonsPageRankFK_IDx` (`PersonID`),
  KEY `PagePersonPageRankFK_IDx` (`PageID`),
  CONSTRAINT `PagePersonPageRankFK` FOREIGN KEY (`PageID`) REFERENCES `Pages` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PersonPersonsPageRankFK` FOREIGN KEY (`PersonID`) REFERENCES `Persons` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PersonPageRank`
--

LOCK TABLES `PersonPageRank` WRITE;
/*!40000 ALTER TABLE `PersonPageRank` DISABLE KEYS */;
INSERT INTO `PersonPageRank` VALUES (1,1,2);
/*!40000 ALTER TABLE `PersonPageRank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Persons`
--

DROP TABLE IF EXISTS `Persons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Persons` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор личности',
  `Name` varchar(2048) NOT NULL COMMENT 'Наименование личности',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Persons`
--

LOCK TABLES `Persons` WRITE;
/*!40000 ALTER TABLE `Persons` DISABLE KEYS */;
INSERT INTO `Persons` VALUES (1,'Путин'),(2,'Медведев'),(3,'Навальный'),(5,'Пупкин');
/*!40000 ALTER TABLE `Persons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Sites`
--

DROP TABLE IF EXISTS `Sites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Sites` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор сайта',
  `Name` varchar(2048) NOT NULL COMMENT 'Наименование сайта',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Sites`
--

LOCK TABLES `Sites` WRITE;
/*!40000 ALTER TABLE `Sites` DISABLE KEYS */;
INSERT INTO `Sites` VALUES (1,'lenta.ru');
/*!40000 ALTER TABLE `Sites` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-26 17:23:01