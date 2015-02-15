CREATE DATABASE `tbuzz` /*!40100 DEFAULT CHARACTER SET utf8 */;

CREATE TABLE `comment` (
  `idcomment` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(1000) NOT NULL,
  `byUserId` int(11) DEFAULT NULL,
  `date_created` timestamp NULL DEFAULT NULL,
  `linkId` int(11) NOT NULL,
  `votes` int(11) DEFAULT NULL,
  PRIMARY KEY (`idcomment`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

CREATE TABLE `comment_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `date_voted` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

CREATE TABLE `follow_domain` (
  `idFollow` int(11) NOT NULL AUTO_INCREMENT,
  `domain` varchar(250) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  `date_followed` datetime DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`idFollow`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `follow_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `followUserName` varchar(45) DEFAULT NULL,
  `date_followed` timestamp NULL DEFAULT NULL,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

CREATE TABLE `link` (
  `idlink` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) DEFAULT NULL,
  `url` varchar(1500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `title` varchar(1500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date_created` datetime DEFAULT NULL,
  `votes` int(11) DEFAULT NULL,
  `comments` int(11) DEFAULT NULL,
  `domain` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `spam` int(11) DEFAULT NULL,
  `language` varchar(45) COLLATE utf8_unicode_ci DEFAULT 'English',
  `category` varchar(45) COLLATE utf8_unicode_ci DEFAULT 'Other',
  `country` varchar(45) COLLATE utf8_unicode_ci DEFAULT 'India',
  `media_type` varchar(45) COLLATE utf8_unicode_ci DEFAULT 'Text/HTML',
  PRIMARY KEY (`idlink`)
) ENGINE=InnoDB AUTO_INCREMENT=628 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `link_tag` (
  `id_link_tag` int(11) NOT NULL AUTO_INCREMENT,
  `link_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id_link_tag`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;
CREATE TABLE `link_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `linkId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `date_voted` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=963 DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(125) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=151 DEFAULT CHARSET=utf8;
CREATE TABLE `user_uuid` (
  `uuid` varchar(350) NOT NULL,
  `username` varchar(55) DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
