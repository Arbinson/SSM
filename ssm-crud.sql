/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.6.26-log : Database - ssm_crud
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ssm_crud` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ssm_crud`;

/*Table structure for table `tbl_dept` */

DROP TABLE IF EXISTS `tbl_dept`;

CREATE TABLE `tbl_dept` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_dept` */

insert  into `tbl_dept`(`dept_id`,`dept_name`) values (1,'Testing'),(2,'Research'),(3,'Selling'),(4,'Human Resources'),(5,'International '),(6,'Export '),(7,'Import '),(8,'Advertising '),(9,'Planning '),(10,'Secretarial '),(11,'Administration '),(12,'Financial '),(13,'Marketing '),(14,'Service ');

/*Table structure for table `tbl_emp` */

DROP TABLE IF EXISTS `tbl_emp`;

CREATE TABLE `tbl_emp` (
  `emp_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `emp_name` varchar(30) DEFAULT NULL,
  `gender` char(1) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `d_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`emp_id`),
  KEY `fk_emp_dept` (`d_id`),
  CONSTRAINT `fk_emp_dept` FOREIGN KEY (`d_id`) REFERENCES `tbl_dept` (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `tbl_emp` */

insert  into `tbl_emp`(`emp_id`,`emp_name`,`gender`,`email`,`d_id`) values (1,'Arbinson','M','1299690731@qq.com',12),(2,'Jack','M','jack@cn.ibm.com',5),(3,'Lucy','F','lucy@gmail.com',1),(4,'White','M','white@yahoo.com',6),(5,'Black','F','black@yahoo.com',7),(6,'Mack','M','mack@hotmail.com',9),(7,'Alice','F','alice@gmail.com',10),(8,'Jenny','F','jenny@gmail.com',4),(9,'Emma','F','emma@cn.ibm.com',1),(12,'love','M','love@yahoo.com',13),(15,'Micheal','M','micheal@gmail.com',3),(16,'Lebron','M','james@gmail.com',8),(17,'Johnson','M','johnson@cn.ibm.com',2),(18,'Wisely','M','wisely@cn.ibm.com',11),(19,'Trayce','F','trayce@cn.ibm.com',14),(20,'Jackson','M','jackson@goole.com',8),(21,'Peterson','M','perterson@facebook.com',4),(22,'Whiteside','M','whiteside@facebook.com',3);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
