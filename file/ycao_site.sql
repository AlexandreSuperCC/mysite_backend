-- MySQL dump 10.13  Distrib 8.0.26, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: ycao_site
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_attach`
--

DROP TABLE IF EXISTS `t_attach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_attach` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `fname` varchar(500) DEFAULT NULL,
                            `ftype` varchar(50) DEFAULT '',
                            `fkey` varchar(500) DEFAULT NULL,
                            `authorid` int DEFAULT NULL,
                            `created` varchar(50) DEFAULT NULL,
                            `dr` varchar(2) DEFAULT '0',
                            `ts` varchar(50) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_category` (
                              `cid` int unsigned NOT NULL AUTO_INCREMENT,
                              `cname` varchar(100) NOT NULL DEFAULT '',
                              `creation_time` varchar(100) NOT NULL,
                              `author_id` int DEFAULT NULL,
                              `ts` varchar(100) NOT NULL,
                              `dr` varchar(2) DEFAULT '0',
                              PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb3 */ ;
/*!50003 SET character_set_results = utf8mb3 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `delete_md` AFTER UPDATE ON `t_category` FOR EACH ROW begin
    if (new.dr = 1) then
        update t_markdown set dr=1 where t_markdown.pk_category=old.cid;
    end if;
end */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_constant`
--

DROP TABLE IF EXISTS `t_constant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_constant` (
                              `id` int unsigned NOT NULL AUTO_INCREMENT,
                              `code` varchar(100) NOT NULL DEFAULT '',
                              `name` varchar(100) NOT NULL DEFAULT '',
                              `creation_time` varchar(100) NOT NULL,
                              `creator_id` int DEFAULT NULL,
                              `content` text,
                              `ts` varchar(100) NOT NULL,
                              `dr` varchar(2) DEFAULT '0',
                              `domain` varchar(100) DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_constant`
--

LOCK TABLES `t_constant` WRITE;
/*!40000 ALTER TABLE `t_constant` DISABLE KEYS */;
INSERT INTO `t_constant` VALUES (1,'f258e34029d94adb8b0e9e79ec7896ac','signForAboutMe','2021-12-26 12:35:21',0,'A FULLSTACK DEVELOPER','2021-12-26 12:35:21','0','AboutMe');
INSERT INTO `t_constant` VALUES (3,'f258','adminPages','2023-1-23 10:55:00',0,'[\'/home/markdown\',\'/home/dashboard\',\'/home/uploadFile\']','2023-1-23 10:56:00','0','App');
/*!40000 ALTER TABLE `t_constant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_contents`
--

DROP TABLE IF EXISTS `t_contents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_contents` (
                              `cid` int unsigned NOT NULL AUTO_INCREMENT,
                              `title` varchar(200) DEFAULT NULL,
                              `titlePic` varchar(200) DEFAULT NULL,
                              `content` text COMMENT 'content of file',
                              `status` varchar(16) DEFAULT 'publish',
                              `tags` varchar(200) DEFAULT NULL,
                              `categories` varchar(200) DEFAULT NULL,
                              `dr` int DEFAULT '0',
                              `uid` int unsigned DEFAULT '0',
                              PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_contents`
--

LOCK TABLES `t_contents` WRITE;
/*!40000 ALTER TABLE `t_contents` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_contents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_log`
--

DROP TABLE IF EXISTS `t_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_log` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `component` varchar(100) DEFAULT NULL,
                         `content` text,
                         `user_id` int DEFAULT NULL,
                         `ts` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_login_log`
--

DROP TABLE IF EXISTS `t_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_login_log` (
                               `lgid` int unsigned NOT NULL AUTO_INCREMENT,
                               `user_id` varchar(64) DEFAULT NULL,
                               `login_ip` varchar(64) DEFAULT NULL,
                               `login_time` varchar(64) DEFAULT NULL,
                               PRIMARY KEY (`lgid`)
) ENGINE=InnoDB AUTO_INCREMENT=400 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_markdown`
--

DROP TABLE IF EXISTS `t_markdown`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_markdown` (
                              `mid` int unsigned NOT NULL AUTO_INCREMENT,
                              `mname` varchar(100) NOT NULL DEFAULT '',
                              `creation_time` varchar(100) NOT NULL,
                              `author_id` int DEFAULT NULL,
                              `ts` varchar(100) NOT NULL,
                              `dr` varchar(2) DEFAULT '0',
                              `pk_category` int unsigned NOT NULL,
                              `rate` varchar(10) DEFAULT NULL,
                              `content` text,
                              `html_text` text,
                              PRIMARY KEY (`mid`),
                              KEY `pk_category` (`pk_category`),
                              CONSTRAINT `t_markdown_ibfk_1` FOREIGN KEY (`pk_category`) REFERENCES `t_category` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=2022110919 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_users`
--

DROP TABLE IF EXISTS `t_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_users` (
                           `uid` int unsigned NOT NULL AUTO_INCREMENT,
                           `username` varchar(32) DEFAULT NULL,
                           `password` varchar(64) DEFAULT NULL,
                           `email` varchar(200) DEFAULT NULL,
                           `role` int DEFAULT NULL,
                           PRIMARY KEY (`uid`),
                           UNIQUE KEY `name` (`username`),
                           UNIQUE KEY `mail` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-05 20:02:59