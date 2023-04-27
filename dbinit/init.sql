CREATE TABLE IF NOT EXISTS `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user` (`name`, `password`, `role`) VALUES ('admin', 'admin', 'Admin');
INSERT INTO `user` (`name`, `password`, `role`) VALUES ('so', 'so', 'Science Observer');
INSERT INTO `user` (`name`, `password`, `role`) VALUES ('astronomer', 'astronomer', 'Astronomer');
