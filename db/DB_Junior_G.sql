CREATE DATABASE  IF NOT EXISTS `junior_g` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `junior_g`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: junior_g
-- ------------------------------------------------------
-- Server version	8.0.36

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
-- Table structure for table `address_tbl`
--

DROP TABLE IF EXISTS `address_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `city` varchar(20) DEFAULT NULL,
  `country` varchar(20) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `zip_code` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_tbl`
--

LOCK TABLES `address_tbl` WRITE;
/*!40000 ALTER TABLE `address_tbl` DISABLE KEYS */;
INSERT INTO `address_tbl` VALUES (2,'Pune','Bharat','Maharshtra','411062'),(3,'Mumbai','India','Maharashtra','400001'),(4,'Delhi','India','Delhi','110001'),(5,'Chennai','India','Tamil Nadu','600001'),(6,'Pune','India','Maharashtra','411001'),(7,'Kolkata','India','West Bengal','700001'),(8,'Hyderabad','India','Telangana','500001');
/*!40000 ALTER TABLE `address_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courses_tbl`
--

DROP TABLE IF EXISTS `courses_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courses_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `capacity` int NOT NULL,
  `end_date` date DEFAULT NULL,
  `fees` double NOT NULL,
  `start_date` date DEFAULT NULL,
  `title` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_3y853ywou6yocptbvix1fk8li` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courses_tbl`
--

LOCK TABLES `courses_tbl` WRITE;
/*!40000 ALTER TABLE `courses_tbl` DISABLE KEYS */;
INSERT INTO `courses_tbl` VALUES (2,20,'2026-03-27',18000,'2025-03-27','Nursery'),(3,20,'2026-03-29',22000,'2025-03-29','LKG'),(4,20,'2026-03-30',15000,'2025-03-29','DayCare'),(5,20,'2026-03-30',22000,'2025-03-29','UKG');
/*!40000 ALTER TABLE `courses_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students_tbl`
--

DROP TABLE IF EXISTS `students_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `students_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(30) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `course_id` bigint NOT NULL,
  `admission_date` date DEFAULT NULL,
  `blood_group` varchar(10) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `emergency_contact` varchar(10) DEFAULT NULL,
  `father_name` varchar(30) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `middle_name` varchar(20) DEFAULT NULL,
  `mother_name` varchar(30) DEFAULT NULL,
  `phone_number` varchar(10) DEFAULT NULL,
  `roll_number` varchar(3) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ei6t3qhfupeehvh0cijbgm28h` (`email`),
  UNIQUE KEY `UK_megpgnhfl5juu3fnbvj785636` (`roll_number`),
  KEY `FKbprbr58o0xuvripnrfebsb47u` (`address_id`),
  KEY `FK6kshspcya0lxhesc72deqm0j` (`course_id`),
  CONSTRAINT `FK6kshspcya0lxhesc72deqm0j` FOREIGN KEY (`course_id`) REFERENCES `courses_tbl` (`id`),
  CONSTRAINT `FKbprbr58o0xuvripnrfebsb47u` FOREIGN KEY (`address_id`) REFERENCES `address_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students_tbl`
--

LOCK TABLES `students_tbl` WRITE;
/*!40000 ALTER TABLE `students_tbl` DISABLE KEYS */;
INSERT INTO `students_tbl` VALUES (6,'aarav.deshmukh@example.com',NULL,3,'2025-03-28','O+','2020-11-15','9123456789','Rajendra Deshmukh','Aarav','Male','Rajendra','Sneha Deshmukh','9876543210','L01','Deshmukh'),(7,'saanvi.joshi@example.com',NULL,3,'2025-03-28','A+','2021-02-20','9876543210','Vinayak Joshi','Saanvi','Female','Vinayak','Priya Joshi','9123456789','L02','Joshi'),(8,'vivaan.patil@example.com',NULL,3,'2025-03-28','B+','2021-06-10','9345678901','Satish Patil','Vivaan','Male','Satish','Anjali Patil','9988776655','L03','Patil'),(9,'isha.kulkarni@example.com',NULL,3,'2025-03-28','AB+','2020-09-25','9098765432','Mahesh Kulkarni','Isha','Female','Mahesh','Neha Kulkarni','8899776655','L04','Kulkarni'),(10,'swapnali@gmail.com',NULL,3,'2025-04-04','AB+','2000-09-14','0747308438','father','Swapnali','Female','','mother','7797775638','L05','Barude');
/*!40000 ALTER TABLE `students_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher_course_tbl`
--

DROP TABLE IF EXISTS `teacher_course_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teacher_course_tbl` (
  `teacher_id` bigint NOT NULL,
  `course_id` bigint NOT NULL,
  KEY `FKl1u8jyrcwr3ooismgeh14qw8d` (`course_id`),
  KEY `FKa887fof4nm89fu9gvdtwks0rc` (`teacher_id`),
  CONSTRAINT `FKa887fof4nm89fu9gvdtwks0rc` FOREIGN KEY (`teacher_id`) REFERENCES `teachers_tbl` (`id`),
  CONSTRAINT `FKl1u8jyrcwr3ooismgeh14qw8d` FOREIGN KEY (`course_id`) REFERENCES `courses_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher_course_tbl`
--

LOCK TABLES `teacher_course_tbl` WRITE;
/*!40000 ALTER TABLE `teacher_course_tbl` DISABLE KEYS */;
INSERT INTO `teacher_course_tbl` VALUES (1,2),(3,2),(4,2),(5,4),(6,4),(7,4),(8,4);
/*!40000 ALTER TABLE `teacher_course_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teachers_tbl`
--

DROP TABLE IF EXISTS `teachers_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `teachers_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_joining` date DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `qualification` varchar(30) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_inyjoj5bu2crgadel4ausurto` (`email`),
  UNIQUE KEY `UK_b8gtt6tmgg5xao9irlvaihefo` (`phone_number`),
  KEY `FKepl44wq2wd38lqa95bpuh3d3u` (`address_id`),
  CONSTRAINT `FKepl44wq2wd38lqa95bpuh3d3u` FOREIGN KEY (`address_id`) REFERENCES `address_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teachers_tbl`
--

LOCK TABLES `teachers_tbl` WRITE;
/*!40000 ALTER TABLE `teachers_tbl` DISABLE KEYS */;
INSERT INTO `teachers_tbl` VALUES (1,'2025-03-27','rajesh.patil@example.com','Rajesh','Patil','9876543210','M.Ed in Mathematics',3),(3,'2025-03-28','anjali.mehta@example.com','Anjali','Mehta','9123456789','B.Ed in English',4),(4,'2025-03-28','amit.patel@example.com','Amit','Patel','9234567890','M.Tech in Computer Science',5),(5,'2025-03-29','sundar@123','Sundar','Pichai','8605344321','CEO Google',NULL),(6,'2025-04-04','abc@gmail.com','abc','xyz','9859899209','none',NULL),(7,'2025-04-03','abcd@gmail.com','abcd','xyzz','9859899208','nonee',NULL),(8,'2025-04-03','abcde@gmail.com','abce','xyzzz','9859899299','PHD',NULL);
/*!40000 ALTER TABLE `teachers_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_tbl`
--

DROP TABLE IF EXISTS `users_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8usegh22yymqae5jjt4pdbd3k` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_tbl`
--

LOCK TABLES `users_tbl` WRITE;
/*!40000 ALTER TABLE `users_tbl` DISABLE KEYS */;
INSERT INTO `users_tbl` VALUES (1,'mohan@gmail.com','$2a$12$taDo2s.UZ22j0r6OWXs1Du60pfx7kcdOfwkO2NOP1VleB.3WzBw/S','ROLE_ADMIN','mohan'),(2,'rahul@gmail.com','$2a$12$SKa4yzVWDr3L8j3f7IASEOTrefd/Dbd6k1oivpaZcGGvU8CHKplHC','ROLE_NORMAL','rahul');
/*!40000 ALTER TABLE `users_tbl` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-06  5:45:11
