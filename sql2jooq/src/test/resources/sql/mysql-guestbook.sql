DROP SCHEMA IF EXISTS guestbook
;
CREATE SCHEMA guestbook
;
USE guestbook
;


CREATE TABLE `posts` (
  `id` bigint(20) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
;

INSERT INTO posts VALUES(1, 'Hello World', '2003-10-01 00:24:08', 'jooq')
;

CREATE TABLE `mails` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
;

INSERT INTO mails VALUES(1, 'cnfree2000@hotmail.com')
;