-- MySQL Script generated by MySQL Workbench
-- Wed May 13 14:36:53 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Library
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Library` DEFAULT CHARACTER SET utf8 ;
USE `Library` ;

-- -----------------------------------------------------
-- Table `Library`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`user_role` ;

CREATE TABLE IF NOT EXISTS `Library`.`user_role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`user` ;

CREATE TABLE IF NOT EXISTS `Library`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `role_id` INT NOT NULL DEFAULT 2,
  `email` VARCHAR(45) NOT NULL,
  `login` VARCHAR(25) NOT NULL,
  `password` CHAR(49) NOT NULL,
  `firstname` NVARCHAR(25) NOT NULL,
  `lastname` NVARCHAR(25) NOT NULL,
  `passport_serial_number` VARCHAR(9) NOT NULL,
  `address` NVARCHAR(25) NOT NULL,
  `phone` VARCHAR(15) NULL,
  `is_banned` TINYINT(1) NOT NULL DEFAULT 0,
  `registration_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `log_in_token` VARCHAR(60) NULL,
  PRIMARY KEY (`id`),
  INDEX `login` (`login` ASC) VISIBLE,
  UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `role_id_idx` (`role_id` ASC) INVISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  CONSTRAINT `fk_role_id`
    FOREIGN KEY (`role_id`)
    REFERENCES `Library`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_genre`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_genre` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_genre` (
  `uuid` CHAR(36) NOT NULL,
  `genre` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_author`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_author` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_author` (
  `uuid` CHAR(36) NOT NULL,
  `author` NVARCHAR(40) NOT NULL,
  PRIMARY KEY (`uuid`),
  UNIQUE INDEX `author_UNIQUE` (`author` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_publisher`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_publisher` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_publisher` (
  `uuid` CHAR(36) NOT NULL,
  `title` NVARCHAR(45) NOT NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_language` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_language` (
  `uuid` CHAR(36) NOT NULL,
  `language` VARCHAR(15) NOT NULL,
  PRIMARY KEY (`uuid`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book` ;

CREATE TABLE IF NOT EXISTS `Library`.`book` (
  `uuid` CHAR(36) NOT NULL,
  `genre_uuid` CHAR(36) NOT NULL,
  `language_uuid` CHAR(36) NOT NULL,
  `publisher_uuid` CHAR(36) NOT NULL,
  `author_uuid` CHAR(36) NOT NULL,
  `title` NVARCHAR(45) NOT NULL,
  `publish_year` SMALLINT NOT NULL,
  `pages_quantity` SMALLINT NOT NULL,
  `description` NVARCHAR(500) NOT NULL,
  PRIMARY KEY (`uuid`),
  INDEX `genre_id_idx` (`genre_uuid` ASC) VISIBLE,
  INDEX `author_uuid_idx` (`author_uuid` ASC) VISIBLE,
  INDEX `publisher_uuid_idx` (`publisher_uuid` ASC) VISIBLE,
  INDEX `language_id_idx` (`language_uuid` ASC) VISIBLE,
  INDEX `title` (`title` ASC) VISIBLE,
  UNIQUE INDEX `book_UNIQUE` (`genre_uuid` ASC, `language_uuid` ASC, `publisher_uuid` ASC, `author_uuid` ASC, `title` ASC, `publish_year` ASC, `pages_quantity` ASC) INVISIBLE,
  CONSTRAINT `fk_genre_uuid`
    FOREIGN KEY (`genre_uuid`)
    REFERENCES `Library`.`book_genre` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_author_uuid`
    FOREIGN KEY (`author_uuid`)
    REFERENCES `Library`.`book_author` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_publisher_uuid`
    FOREIGN KEY (`publisher_uuid`)
    REFERENCES `Library`.`book_publisher` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_uuid`
    FOREIGN KEY (`language_uuid`)
    REFERENCES `Library`.`book_language` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_instance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_instance` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_instance` (
  `uuid` CHAR(36) NOT NULL,
  `book_uuid` CHAR(36) NOT NULL,
  `is_available` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`uuid`),
  INDEX `fk_book_uuid_idx` (`book_uuid` ASC) VISIBLE,
  CONSTRAINT `fk_book_uuid`
    FOREIGN KEY (`book_uuid`)
    REFERENCES `Library`.`book` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`order_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`order_type` ;

CREATE TABLE IF NOT EXISTS `Library`.`order_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`order_status`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`order_status` ;

CREATE TABLE IF NOT EXISTS `Library`.`order_status` (
  `id` INT NOT NULL,
  `status` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Library`.`book_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Library`.`book_order` ;

CREATE TABLE IF NOT EXISTS `Library`.`book_order` (
  `uuid` CHAR(36) NOT NULL,
  `user_id` INT NOT NULL,
  `book_instance_uuid` CHAR(36) NOT NULL,
  `order_type_id` INT NOT NULL,
  `order_status_id` INT NOT NULL DEFAULT 1,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`uuid`),
  INDEX `fk_user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_book_instance_uuid_idx` (`book_instance_uuid` ASC) VISIBLE,
  INDEX `fk_order_type_idx` (`order_type_id` ASC) VISIBLE,
  INDEX `fk_order_status_idx` (`order_status_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `Library`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_book_instance_uuid`
    FOREIGN KEY (`book_instance_uuid`)
    REFERENCES `Library`.`book_instance` (`uuid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_type`
    FOREIGN KEY (`order_type_id`)
    REFERENCES `Library`.`order_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_status`
    FOREIGN KEY (`order_status_id`)
    REFERENCES `Library`.`order_status` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `Library`.`user_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`user_role` (`id`, `role`) VALUES (1, 'ADMIN');
INSERT INTO `Library`.`user_role` (`id`, `role`) VALUES (2, 'USER');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`user` (`id`, `role_id`, `email`, `login`, `password`, `firstname`, `lastname`, `passport_serial_number`, `address`, `phone`, `is_banned`, `registration_date`, `log_in_token`) VALUES (1, 1, 'libmail1@live.com', 'admin1', 'o81lBJNj1FZ5XffW35RExQ==:UE99xzi44a7yQZPLafTy+Q==', 'Admin1', 'Adminius', 'MN1234567', 'some address', '+37555225522', 0, '2008-01-01 00:00:01', NULL);
INSERT INTO `Library`.`user` (`id`, `role_id`, `email`, `login`, `password`, `firstname`, `lastname`, `passport_serial_number`, `address`, `phone`, `is_banned`, `registration_date`, `log_in_token`) VALUES (2, 1, 'libmail2@live.com', 'admin2', 'hUoDA8e92qbMa1xPTCfjZA==:v8qGRKeF90bjf72pZL8APA==', 'Admin2', 'Adminius', 'MN7654321', 'some address', '+37555225522', 0, '2008-01-01 00:00:01', NULL);
INSERT INTO `Library`.`user` (`id`, `role_id`, `email`, `login`, `password`, `firstname`, `lastname`, `passport_serial_number`, `address`, `phone`, `is_banned`, `registration_date`, `log_in_token`) VALUES (3, 2, 'usar1mail@gmail.com', 'user1', 'b/hzZkMk57NHMUKjV3uC1g==:4hVb++Z96GCMXIzvZoJihQ==', 'User1', 'Userus', 'MM1234567', 'some address', '+37555222222', 0, '2008-01-01 00:00:01', NULL);
INSERT INTO `Library`.`user` (`id`, `role_id`, `email`, `login`, `password`, `firstname`, `lastname`, `passport_serial_number`, `address`, `phone`, `is_banned`, `registration_date`, `log_in_token`) VALUES (4, 2, 'usar2mail@gmail.com', 'user2', 'aog+w3028mSObe+LbXX1Ug==:X1Cmk63BAZIQvl6VD8BrsA==', 'User2', 'Userus', 'MA1234567', 'some address', '+37555222333', 0, '2008-01-01 00:00:01', NULL);
INSERT INTO `Library`.`user` (`id`, `role_id`, `email`, `login`, `password`, `firstname`, `lastname`, `passport_serial_number`, `address`, `phone`, `is_banned`, `registration_date`, `log_in_token`) VALUES (5, 2, 'usar3mail@gmail.com', 'user3user3', 'aog+w3028mSObe+LbXX1Ug==:X1Cmk63BAZIQvl6VD8BrsA==', 'User3', 'Userus', 'MA1234567', 'some address', '+37555222333', 0, '2008-01-01 00:00:01', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_genre`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('680ce01e-cf9b-4fae-8534-0b378160d116', 'Dramma');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('7459bd12-65cb-4b8d-9ee1-97697a80e914', 'Philosopfy');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('da57873c-74f3-49e5-a57f-c9ecbe46f383', 'Sci-Fi');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('e22fdd8b-caa9-418a-ab98-3022c94a97ea', 'Thriller');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('ffc04c64-2ad0-465c-971f-c24952d98c53', 'Historical');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('fc45b805-39c3-4e75-962b-5abc03285e2c', 'Novel');
INSERT INTO `Library`.`book_genre` (`uuid`, `genre`) VALUES ('bb3a6bc6-0c64-476d-b24e-875eeb8397e2', 'Tragedy');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_author`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('1311b091-11df-479c-8ad1-ba7c54559fe3', 'Jack London');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('76eef696-78bc-49f4-b306-f4d24eae93ac', 'Ницше Фридрих');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('d053b00e-75d6-40ea-a7c5-10c3df5bf2bb', 'Оруэлл Джордж');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('81d91d3a-0061-43f9-ac02-c6843c040d10', 'Каттнер Генри');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('fa42ca6c-fca5-4fbd-a854-20afa6a2fd9a', 'Alex Michaelides');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('76264a98-a680-4f8d-b422-ba086e6222c4', 'John M. Barry');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('4990d94c-b664-4e22-b060-f94bdf0477e1', 'Stephen King');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('6daedf98-40aa-4ca0-b6e9-4636bd01fd86', 'Miguel de Cervantes');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('9078a4fc-7c83-4d55-a3f3-05140317fefb', 'F. Scott Fitzgerald');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('d5281fd8-4775-4ccc-b897-16568974a838', 'Gabriel García Márquez');
INSERT INTO `Library`.`book_author` (`uuid`, `author`) VALUES ('219f2e52-6e3a-48bf-80f2-608979723e20', 'Leo Tolstoy');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_publisher`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('b412a16d-d1c8-4535-8e0f-7b0856148c27', 'AST');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('2a21c06e-4d3f-418e-885b-45918549220b', 'Азбука');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('55a37883-fe90-4617-ad2d-21006b3dbb79', 'АСТ');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('bf95ff09-6f8e-4791-8809-7c4850a6eb51', 'Эксмо');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('de08be7d-b5a7-485a-a96b-50f678e9a9b4', 'Celadon Books');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('a0444654-8bbd-4577-8985-672d0d0359c9', 'Penguin Books');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('f5664137-08fd-4b78-81c3-21dd547ab3ea', 'Signet Books');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('54614c2d-1895-4932-8eac-9345831bb9f8', 'Francisco de Robles');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('38c20a79-e5e1-4f20-9ddc-d1cc0327e314', 'Charles Scribner\'s Sons');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('715ef667-6a1f-49ee-85d8-53b3ca7ba80b', 'Editorial Sudamericana');
INSERT INTO `Library`.`book_publisher` (`uuid`, `title`) VALUES ('9a386e4a-e7d5-4685-8d52-6767710cea14', 'The Russian Messenger');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_language`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_language` (`uuid`, `language`) VALUES ('0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'English');
INSERT INTO `Library`.`book_language` (`uuid`, `language`) VALUES ('6d46728f-793f-4cb5-8051-345c23582f33', 'Russian');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('35836518-3355-4424-8335-8ba8d8b42239', '680ce01e-cf9b-4fae-8534-0b378160d116', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'b412a16d-d1c8-4535-8e0f-7b0856148c27', '1311b091-11df-479c-8ad1-ba7c54559fe3', 'White fang', 1996, 206, 'The story begins before the wolf-dog hybrid is born, with two men and their sled dog team on a journey to deliver the coffin of Lord Alfred to a remote town named Fort McGurry in the higher area of the Yukon Territory. The men, Bill and Henry, are stalked by a large pack of starving wolves over the course of several days. Finally, after all of their dogs and Bill have been eaten, four more teams find Henry escaping from the wolves; the wolf pack scatters when they hear the large group of people');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('2a32e038-e84e-4438-b77b-8e0b4a2b308f', '680ce01e-cf9b-4fae-8534-0b378160d116', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'b412a16d-d1c8-4535-8e0f-7b0856148c27', '1311b091-11df-479c-8ad1-ba7c54559fe3', 'Marthin Iden', 1999, 266, 'Living in Oakland at the beginning of the 20th century, Martin Eden struggles to rise above his destitute, proletarian circumstances through an intense and passionate pursuit of self-education, hoping to achieve a place among the literary elite. His principal motivation is his love for Ruth Morse. Because Eden is a rough, uneducated sailor from a working-class background[5] and the Morses are a bourgeois family, a union between them would be impossible unless.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('3ad47e0b-4c0b-492d-acf9-6ce1dbe5a32e', '7459bd12-65cb-4b8d-9ee1-97697a80e914', '6d46728f-793f-4cb5-8051-345c23582f33', '2a21c06e-4d3f-418e-885b-45918549220b', '76eef696-78bc-49f4-b306-f4d24eae93ac', 'Так говорил Заратустра', 2010, 311, 'Фридрих Ницше - немецкий философ, филолог-классик, поэт, автор таких известных трудов, как \"Рождение трагедии из духа музыки\", \"По ту сторон добра и зла\", \"Генеалогия морали\", \"Антихрист\" и др. ');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('ef926661-630a-4604-b798-bb4faee15e76', '680ce01e-cf9b-4fae-8534-0b378160d116', '6d46728f-793f-4cb5-8051-345c23582f33', '55a37883-fe90-4617-ad2d-21006b3dbb79', 'd053b00e-75d6-40ea-a7c5-10c3df5bf2bb', '1984', 2017, 312, 'Своеобразный антипод второй великой антиутопии XX века - \"О дивный новый мир\" Олдоса Хаксли. Что, в сущности, страшнее: доведенное до абсурда \"общество потребления\" - или доведенное до абсолюта \"общество идеи\"? По Оруэллу, нет и не может быть ничего ужаснее тотальной несвободы');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('91866985-fa5d-4962-9880-5a363ae5bec2', 'da57873c-74f3-49e5-a57f-c9ecbe46f383', '6d46728f-793f-4cb5-8051-345c23582f33', 'bf95ff09-6f8e-4791-8809-7c4850a6eb51', '81d91d3a-0061-43f9-ac02-c6843c040d10', 'Ярость', 2006, 411, 'Шестьсот лет прошло с момента гибели Земли. Наука смогла создать искусственную среду на дне океана Венеры. В башнях живут бессмертные, исчисляющие свои года столетиями. Они мудры, поэтому они могут принимать ответственные решения, управлять жизнью короткоживущих. ');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('e7a11f0a-8176-49a2-bb45-7638979fe8c2', 'e22fdd8b-caa9-418a-ab98-3022c94a97ea', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'de08be7d-b5a7-485a-a96b-50f678e9a9b4', 'fa42ca6c-fca5-4fbd-a854-20afa6a2fd9a', 'The Silent Patient', 2018, 316, 'The Silent Patient is a shocking psychological thriller of a woman’s act of violence against her husband―and of the therapist obsessed with uncovering her motive.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('5e2c07ec-569a-41f8-a0ec-ff04e72e0c30', 'ffc04c64-2ad0-465c-971f-c24952d98c53', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'a0444654-8bbd-4577-8985-672d0d0359c9', '76264a98-a680-4f8d-b422-ba086e6222c4', 'The Great Influenza', 2019, 312, 'The Great Influenza: The Story of the Deadliest Pandemic in History');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('e2f4094d-31ac-4d77-95c0-11306f020be2', 'e22fdd8b-caa9-418a-ab98-3022c94a97ea', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', 'f5664137-08fd-4b78-81c3-21dd547ab3ea', '4990d94c-b664-4e22-b060-f94bdf0477e1', 'Rage', 1980, 315, 'Rage (written as Getting It On; the title was changed before publication) is a psychological thriller novel by American writer Stephen King, the first he published under the pseudonym Richard Bachman. It was first published in 1977 and then was collected in 1985 in the hardcover omnibus The Bachman Books. The novel describes a school shooting, and has been associated with actual high school shooting incidents in the 1980s and 1990s. In response King allowed the novel to fall out of print.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('a7c37251-36c4-4eca-8c13-8f19945b5818', 'fc45b805-39c3-4e75-962b-5abc03285e2c', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', '54614c2d-1895-4932-8eac-9345831bb9f8', '6daedf98-40aa-4ca0-b6e9-4636bd01fd86', 'Don Quixote', 1896, 751, 'Cervantes wrote that the first chapters were taken from \"the archives of La Mancha\", and the rest were translated from an Arabic text by the Moorish author Cide Hamete Benengeli. This metafictional trick appears to give a greater credibility to the text, implying that Don Quixote is a real character and that the events related truly occurred several decades prior to the recording of this account. However, it was also common practice in that era for fictional works to make some pretense.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('8d4bc4a5-da50-45a7-b525-4f21d32d6e41', 'bb3a6bc6-0c64-476d-b24e-875eeb8397e2', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', '38c20a79-e5e1-4f20-9ddc-d1cc0327e314', '9078a4fc-7c83-4d55-a3f3-05140317fefb', 'The Great Gatsby', 1971, 351, 'Great Gatsby is a 1925 novel written by American author F. Scott Fitzgerald that follows a cast of characters living in the fictional towns of West Egg and East Egg on prosperous Long Island in the summer of 1922. The story primarily concerns the young and mysterious millionaire Jay Gatsby and his quixotic passion and obsession with the beautiful former debutante Daisy Buchanan. Considered to be Fitzgerald\'s magnum opus.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('4fe82081-d570-4a53-b54d-962bd778bdb9', 'fc45b805-39c3-4e75-962b-5abc03285e2c', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', '715ef667-6a1f-49ee-85d8-53b3ca7ba80b', 'd5281fd8-4775-4ccc-b897-16568974a838', 'One Hundred Years of Solitude', 1999, 512, 'One Hundred Years of Solitude (Spanish: Cien años de soledad, American Spanish: [sjen ˈaɲos ðe soleˈðað]) is a landmark 1967 novel by Colombian author Gabriel García Márquez that tells the multi-generational story of the Buendía family, whose patriarch, José Arcadio Buendía, founded the town of Macondo, a fictitious town in the country of Colombia.');
INSERT INTO `Library`.`book` (`uuid`, `genre_uuid`, `language_uuid`, `publisher_uuid`, `author_uuid`, `title`, `publish_year`, `pages_quantity`, `description`) VALUES ('71981d30-8e3d-4551-ac4c-1b447ae40818', 'fc45b805-39c3-4e75-962b-5abc03285e2c', '0b98b2b1-eb65-4ccc-9a2e-bfe2dfb6a856', '9a386e4a-e7d5-4685-8d52-6767710cea14', '219f2e52-6e3a-48bf-80f2-608979723e20', 'War and Peace', 1891, 1271, 'The novel chronicles the French invasion of Russia and the impact of the Napoleonic era on Tsarist society through the stories of five Russian aristocratic families. Portions of an earlier version, titled The Year 1805, were serialized in The Russian Messenger from 1865 to 1867, then published in its entirety in 1869.');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_instance`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('a4047b86-dcb2-49c3-bd81-ca3c55c0f9d8', '35836518-3355-4424-8335-8ba8d8b42239', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('48952368-b299-4df4-89fb-9c532812fdf5', '35836518-3355-4424-8335-8ba8d8b42239', 0);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('3ba447cd-b222-4838-93a4-1720d3e02718', '35836518-3355-4424-8335-8ba8d8b42239', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('746079cc-7bee-4138-bc59-cc14ccd36465', '2a32e038-e84e-4438-b77b-8e0b4a2b308f', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('123079cc-7bee-4138-bc59-cc14ccd31111', '2a32e038-e84e-4438-b77b-8e0b4a2b308f', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('ae4c315b-d968-4cae-802c-e901395b746f', '3ad47e0b-4c0b-492d-acf9-6ce1dbe5a32e', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('7856718b-662b-4ad9-ba0e-be11508859cc', '3ad47e0b-4c0b-492d-acf9-6ce1dbe5a32e', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('7afa5331-4258-469d-9033-67bcac7f4637', 'ef926661-630a-4604-b798-bb4faee15e76', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('43dc325a-fe0a-4b1d-bda7-60604a5e310f', 'ef926661-630a-4604-b798-bb4faee15e76', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('816e5bf4-a927-4bbe-aa00-b0d89c9863b2', '91866985-fa5d-4962-9880-5a363ae5bec2', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('581fbd73-a6f7-43c7-a2e8-497ef4c7c657', '91866985-fa5d-4962-9880-5a363ae5bec2', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('2f9a42ba-e829-4c63-894e-530f504ed7fb', 'e7a11f0a-8176-49a2-bb45-7638979fe8c2', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('9c96fc3b-2423-44d0-a554-fd55a9e69a94', '5e2c07ec-569a-41f8-a0ec-ff04e72e0c30', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('56259804-0a0f-4031-a3b6-acabb51f851f', '5e2c07ec-569a-41f8-a0ec-ff04e72e0c30', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('f7d31487-b6c4-4b39-a237-bd9bcd0be01e', 'e2f4094d-31ac-4d77-95c0-11306f020be2', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('9520e6ef-4188-429b-9bda-5acc9df733b1', 'a7c37251-36c4-4eca-8c13-8f19945b5818', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('19ce193f-5eb0-481d-acdc-32b22b3bdd18', '8d4bc4a5-da50-45a7-b525-4f21d32d6e41', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('434b6a97-0419-4eda-9697-dca3e07b8b44', '8d4bc4a5-da50-45a7-b525-4f21d32d6e41', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('86527717-6c23-49cb-8345-d552a9ba7d81', '4fe82081-d570-4a53-b54d-962bd778bdb9', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('2e2a2ee7-8272-438a-a3ad-250b2183dcd4', '4fe82081-d570-4a53-b54d-962bd778bdb9', 1);
INSERT INTO `Library`.`book_instance` (`uuid`, `book_uuid`, `is_available`) VALUES ('e4f7633c-6544-4f69-b668-f9eaba45ee36', '71981d30-8e3d-4551-ac4c-1b447ae40818', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`order_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`order_type` (`id`, `type`) VALUES (1, 'SUBSCRIPTION');
INSERT INTO `Library`.`order_type` (`id`, `type`) VALUES (2, 'READING_HOLE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`order_status`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`order_status` (`id`, `status`) VALUES (1, 'PENDING');
INSERT INTO `Library`.`order_status` (`id`, `status`) VALUES (2, 'ISSUED_BY');
INSERT INTO `Library`.`order_status` (`id`, `status`) VALUES (3, 'RETURNED');
INSERT INTO `Library`.`order_status` (`id`, `status`) VALUES (4, 'CLOSE');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Library`.`book_order`
-- -----------------------------------------------------
START TRANSACTION;
USE `Library`;
INSERT INTO `Library`.`book_order` (`uuid`, `user_id`, `book_instance_uuid`, `order_type_id`, `order_status_id`, `date`) VALUES ('48952368-b299-4df4-89fb-9c532812fdf5', 3, '48952368-b299-4df4-89fb-9c532812fdf5', 1, 1, '2008-01-01 00:00:01');

COMMIT;
