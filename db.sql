CREATE DATABASE `leaderread` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `comment` (
  `idcomment` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `byUserId` int(11) DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT NULL,
  `linkId` int(11) NOT NULL,
  PRIMARY KEY (`idcomment`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;

CREATE TABLE `follow_domain` (
  `idFollow` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(250) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `date_followed` datetime DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFollow`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `follow_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `followUserName` varchar(45) DEFAULT NULL,
  `date_followed` timestamp NULL DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `link` (
  `idlink` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `url` varchar(1500) DEFAULT NULL,
  `title` varchar(1500) DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `votes` int(11) DEFAULT NULL,
  `comments` int(11) DEFAULT NULL,
  `domain` varchar(200) DEFAULT NULL,
  `spam` int(11) DEFAULT NULL,
  `language` varchar(45) DEFAULT 'English',
  `category` varchar(45) DEFAULT 'Other',
  `country` varchar(45) DEFAULT 'India',
  `media_type` varchar(45) DEFAULT 'Text/HTML',
  PRIMARY KEY (`idlink`)
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

CREATE TABLE `link_tag` (
  `id_link_tag` int(11) NOT NULL AUTO_INCREMENT,
  `link_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id_link_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

CREATE TABLE `link_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `linkId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `date_voted` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8;

CREATE TABLE `spam` (
  `userId` int(11) DEFAULT NULL,
  `linkId` int(11) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `tag` (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(125) NOT NULL,
  PRIMARY KEY (`tag_id`),
  UNIQUE KEY `tag_name_UNIQUE` (`tag_name`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(125) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

CREATE TABLE `user_uuid` (
  `uuid` varchar(350) NOT NULL,
  `username` varchar(55) DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

