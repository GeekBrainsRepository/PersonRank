INSERT INTO `personrank`.`persons` (`Name`) VALUES ('Путин');
INSERT INTO `personrank`.`persons` (`Name`) VALUES ('Медведев');

INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Путин', '1');
INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Путиным ', '1');
INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Путина ', '1');
INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Медведев', '2');
INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Медведевым', '2');
INSERT INTO `personrank`.`keywords` (`Name`, `PersonID`) VALUES ('Медведева', '2');

INSERT INTO `personrank`.`sites` (`Name`) VALUES ('www.1.test');
INSERT INTO `personrank`.`sites` (`Name`) VALUES ('www.2.test');
INSERT INTO `personrank`.`sites` (`Name`) VALUES ('www.3.test');
INSERT INTO `personrank`.`sites` (`Name`) VALUES ('www.4.test');

INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.1.test\\1', '1', STR_TO_DATE('02-01-2017 00:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-08-2017 01:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.1.test\\2', '1', STR_TO_DATE('02-02-2017 01:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-08-2017 02:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.1.test\\3', '1', STR_TO_DATE('02-03-2017 02:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-08-2017 03:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.1.test\\4', '1', STR_TO_DATE('02-04-2017 03:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-08-2017 04:00:00','%m-%d-%Y %H:%i:%s'));

INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.2.test\\1', '2', STR_TO_DATE('02-01-2017 00:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-09-2017 01:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.2.test\\2', '2', STR_TO_DATE('02-02-2017 01:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-09-2017 02:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.2.test\\3', '2', STR_TO_DATE('02-03-2017 02:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-09-2017 03:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.2.test\\4', '2', STR_TO_DATE('02-04-2017 03:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-09-2017 04:00:00','%m-%d-%Y %H:%i:%s'));

INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.3.test\\1', '3', STR_TO_DATE('02-01-2017 00:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-10-2017 01:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.3.test\\2', '3', STR_TO_DATE('02-02-2017 01:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-10-2017 02:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.3.test\\3', '3', STR_TO_DATE('02-03-2017 02:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-10-2017 03:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.3.test\\4', '3', STR_TO_DATE('02-04-2017 03:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-10-2017 04:00:00','%m-%d-%Y %H:%i:%s'));

INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.4.test\\1', '4', STR_TO_DATE('02-01-2017 00:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-11-2017 01:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.4.test\\2', '4', STR_TO_DATE('02-02-2017 01:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-11-2017 02:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.4.test\\3', '4', STR_TO_DATE('02-03-2017 02:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-11-2017 03:00:00','%m-%d-%Y %H:%i:%s'));
INSERT INTO `personrank`.`pages` (`Url`, `SiteID`, `FoundDateTime`, `LastScanDate`) VALUES ('www.4.test\\4', '4', STR_TO_DATE('02-04-2017 03:00:00','%m-%d-%Y %H:%i:%s'), STR_TO_DATE('02-11-2017 04:00:00','%m-%d-%Y %H:%i:%s'));

INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '1', '1');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '1', '2');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '2', '3');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '2', '4');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '3', '5');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '3', '6');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '4', '7');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '4', '8');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '5', '9');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '5', '10');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '6', '11');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '6', '12');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '7', '13');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '7', '14');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '8', '15');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '8', '16');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '9', '17');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '9', '18');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '10', '19');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '10', '20');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '11', '21');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '11', '22');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '12', '23');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '12', '24');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '13', '25');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '13', '26');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '14', '27');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '14', '28');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '15', '29');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '15', '30');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('1', '16', '31');
INSERT INTO `personrank`.`personpagerank` (`PersonID`, `PageID`, `Rank`) VALUES ('2', '16', '32');




