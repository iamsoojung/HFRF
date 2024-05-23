-- MySQL Workbench Forward Engineering
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hfrf
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hfrf` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `hfrf` ;

-- -----------------------------------------------------
-- Table `hfrf`.`members`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`members` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`members` (
  `user_id` VARCHAR(16) NOT NULL,
  `user_name` VARCHAR(20) NOT NULL,
  `user_password` VARCHAR(16) NOT NULL,
  `email_id` VARCHAR(20) NULL DEFAULT NULL,
  `email_domain` VARCHAR(45) NULL DEFAULT NULL,
  `join_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `token` VARCHAR(1000) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `hfrf`.`members` (user_id, user_name, user_password, email_id, email_domain, join_date)
VALUES ('ssafy', '김싸피', '1234', 'ssafy', 'ssafy.com', now()), 
       ('admin', '관리자', '1234', 'admin', 'google.com', now());

SELECT * FROM members;
COMMIT;

-- -----------------------------------------------------
-- Table `hfrf`.`board`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`board` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`board` (
  `article_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `hit` INT NULL DEFAULT 0,
  `register_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`article_no`),
  INDEX `board_to_members_user_id_fk` (`user_id` ASC) VISIBLE,
  CONSTRAINT `board_to_members_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `hfrf`.`members` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO `hfrf`.`board` (`user_id`, `subject`, `content`, `hit`, `register_time`) 
VALUES ('ssafy', '꽁꽁 얼어붙은 한강위로', '손민락이 돌아다닙니다.', 0, CURRENT_TIMESTAMP);

INSERT INTO `hfrf`.`board` (`user_id`, `subject`, `content`, `hit`, `register_time`) 
VALUES ('ssafy', '꽁꽁 얼어붙은 한강위로', '손민락이 돌아다닙니다.', 0, CURRENT_TIMESTAMP);

INSERT INTO `hfrf`.`board` (`user_id`, `subject`, `content`, `hit`, `register_time`) 
VALUES ('ssafy', '부동산 시장 전망', '부동산 시장의 전반적인 전망에 대해 알아보려고 합니다. 현재 추세와 예상되는 변화에 대해 설명해주실 수 있나요?', 27, '2024-05-18 11:55:00'),
('ssafy', '부동산 관련 법률 문의', '부동산 거래시 유의해야 할 법적인 사항이 있는지 알고 싶습니다. 관련 법률 정보를 제공해주세요.', 18, '2024-05-19 14:10:00'),
('ssafy', '아파트 관련 정보 필요', '특정 아파트 단지의 시세 및 관련 정보를 알고 싶습니다. 혹시 자료를 공유할 수 있나요?', 32, '2024-05-20 09:20:00'),
('ssafy', '부동산 중개 수수료에 대하여', '부동산 거래시 중개 수수료에 대해 궁금합니다. 일반적으로 어느 정도인가요?', 15, '2024-05-21 15:45:00'),
('ssafy', '부동산 대출 문의', '부동산을 구매하기 위해 대출을 받고 싶습니다. 어떤 절차가 필요한지 알려주세요.', 23, '2024-05-22 10:30:00');

INSERT INTO board (user_id, subject, content, hit, register_time) VALUES
('admin', '이용안내', '서비스 이용 안내사항입니다. 이용 중에 궁금한 점이 있으면 언제든지 문의해주세요.', 30, '2024-05-20 15:20:00'),
('admin', '서비스 업데이트 안내', '서비스가 업그레이드되었습니다. 새로운 기능과 개선된 기능을 확인해보세요!', 25, '2024-05-19 14:10:00'),
('admin', '이벤트 안내', '특별한 이벤트가 진행됩니다. 참여하시면 푸짐한 상품을 드립니다!', 40, '2024-05-18 11:30:00'),
('admin', '일시적인 서비스 중단 안내', '서비스 점검으로 인해 잠시 접속이 불가능할 수 있습니다. 양해 부탁드립니다.', 35, '2024-05-17 10:45:00');

-- -----------------------------------------------------
-- Table `hfrf`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`comment` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`comment` (
  `comment_no` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(16) NOT NULL,
  `article_no` INT NOT NULL,
  `content` VARCHAR(2000) NOT NULL,
  `register_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_no`),
  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
  INDEX `article_no_idx` (`article_no` ASC) VISIBLE,
  CONSTRAINT `article_no_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `hfrf`.`board` (`article_no`)
    ON DELETE CASCADE,
  CONSTRAINT `user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `hfrf`.`members` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `hfrf`.`file_info`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`file_info` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`file_info` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `article_no` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `original_file` VARCHAR(50) NULL,
  `save_file` VARCHAR(50) NULL,
  PRIMARY KEY (`idx`),
  CONSTRAINT `file_info_to_board_article_no_fk`
    FOREIGN KEY (`article_no`)
    REFERENCES `hfrf`.`board` (`article_no`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hfrf`.`aptlike`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hfrf`.`aptlike` (
  `user_id` VARCHAR(16) NOT NULL,
  `aptCode` BIGINT NOT NULL,
  `dongCode` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`user_id`, `aptCode`),
  CONSTRAINT `aptlike_user_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `hfrf`.`members` (`user_id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `hfrf`.`dongcode`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hfrf`.`dongcode` (
  `dongCode` VARCHAR(10) NOT NULL,
  `sidoName` VARCHAR(30) NULL DEFAULT NULL,
  `gugunName` VARCHAR(30) NULL DEFAULT NULL,
  `dongName` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `hfrf`.`housesell`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`housesell` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`housesell` (
    idx INT PRIMARY KEY AUTO_INCREMENT,
    id VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    price VARCHAR(50) NOT NULL,
    admin_price VARCHAR(50),
    admin_desc TEXT,
    room_type VARCHAR(50) NOT NULL,
    floor_now INT NOT NULL,
    floor_tot INT NOT NULL,
    size DOUBLE NOT NULL,
    content TEXT,
    keyword VARCHAR(255),
    address VARCHAR(255) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    lat VARCHAR(50),
    lng VARCHAR(50)
);

-- -----------------------------------------------------
-- Table `hfrf`.`file_info_sell`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hfrf`.`file_info_sell` ;

CREATE TABLE IF NOT EXISTS `hfrf`.`file_info_sell` (
  `idx` INT NOT NULL AUTO_INCREMENT,
  `sell_no` INT NULL,
  `save_folder` VARCHAR(45) NULL,
  `original_file` VARCHAR(50) NULL,
  `save_file` VARCHAR(50) NULL,
  PRIMARY KEY (`idx`),
  CONSTRAINT `file_info_sell_to_sell_idx_fk`
    FOREIGN KEY (`sell_no`)
    REFERENCES `hfrf`.`housesell` (`idx`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `hfrf`.`houseinfo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hfrf`.`houseinfo` (
  `aptCode` BIGINT NOT NULL,
  `buildYear` INT NULL DEFAULT NULL,
  `roadName` VARCHAR(40) NULL DEFAULT NULL,
  `roadNameBonbun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameBubun` VARCHAR(5) NULL DEFAULT NULL,
  `roadNameSeq` VARCHAR(2) NULL DEFAULT NULL,
  `roadNameBasementCode` VARCHAR(1) NULL DEFAULT NULL,
  `roadNameCode` VARCHAR(7) NULL DEFAULT NULL,
  `dong` VARCHAR(40) NULL DEFAULT NULL,
  `bonbun` VARCHAR(4) NULL DEFAULT NULL,
  `bubun` VARCHAR(4) NULL DEFAULT NULL,
  `sigunguCode` VARCHAR(5) NULL DEFAULT NULL,
  `eubmyundongCode` VARCHAR(5) NULL DEFAULT NULL,
  `dongCode` VARCHAR(10) NULL DEFAULT NULL,
  `landCode` VARCHAR(1) NULL DEFAULT NULL,
  `apartmentName` VARCHAR(40) NULL DEFAULT NULL,
  `jibun` VARCHAR(10) NULL DEFAULT NULL,
  `lng` VARCHAR(30) NULL DEFAULT NULL,
  `lat` VARCHAR(30) NULL DEFAULT NULL,
  PRIMARY KEY (`aptCode`),
  UNIQUE INDEX `UNIQUE` (`buildYear` ASC, `apartmentName` ASC, `jibun` ASC, `sigunguCode` ASC, `eubmyundongCode` ASC) INVISIBLE,
  INDEX `houseinfo_dongCode_dongcode_dongCode_fk_idx` (`dongCode` ASC) INVISIBLE,
  CONSTRAINT `houseinfo_dongCode_dongcode_dongCode_fk`
    FOREIGN KEY (`dongCode`)
    REFERENCES `hfrf`.`dongcode` (`dongCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `hfrf`.`housedeal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hfrf`.`housedeal` (
  `no` BIGINT NOT NULL,
  `dealAmount` VARCHAR(20) NULL DEFAULT NULL,
  `dealYear` INT NULL DEFAULT NULL,
  `dealMonth` INT NULL DEFAULT NULL,
  `dealDay` INT NULL DEFAULT NULL,
  `area` VARCHAR(20) NULL DEFAULT NULL,
  `floor` VARCHAR(4) NULL DEFAULT NULL,
  `cancelDealType` VARCHAR(1) NULL DEFAULT NULL,
  `aptCode` BIGINT NULL DEFAULT NULL,
  PRIMARY KEY (`no`),
  INDEX `housedeal_aptCode_houseinfo_aptCode_fk_idx` (`aptCode` ASC) VISIBLE,
  CONSTRAINT `housedeal_aptCode_houseinfo_aptCode_fk`
    FOREIGN KEY (`aptCode`)
    REFERENCES `hfrf`.`houseinfo` (`aptCode`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- View `hfrf`.`apt_info_deal`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hfrf`.`apt_info_deal`;
CREATE OR REPLACE VIEW `hfrf`.`apt_info_deal` AS
    SELECT 
        `i`.`aptCode` AS `aptCode`,
        `i`.`dongCode` AS `dongCode`,
        `i`.`apartmentName` AS `apartmentName`,
        `d`.`sidoName` AS `sidoName`,
        `d`.`gugunName` AS `gugunName`,
        `i`.`roadName` AS `roadName`,
        `d`.`dongName` AS `dong`,
        `i`.`lng` AS `lng`,
        `i`.`lat` AS `lat`
    FROM
        `hfrf`.`dongcode` `d`
        JOIN `hfrf`.`houseinfo` `i`
    WHERE
        `d`.`dongCode` = `i`.`dongCode`;

-- -----------------------------------------------------
-- View `hfrf`.`apt`
-- -----------------------------------------------------
DROP VIEW IF EXISTS `hfrf`.`apt`;
CREATE OR REPLACE VIEW `hfrf`.`apt` AS
    SELECT 
        `a`.`dongCode` AS `dongCode`,
        `a`.`sidoName` AS `sidoName`,
        `a`.`gugunName` AS `gugunName`,
        `a`.`roadName` AS `roadName`,
        `d`.`dealYear` AS `dealYear`,
        `d`.`dealMonth` AS `dealMonth`,
        `a`.`apartmentName` AS `apartmentName`,
        `d`.`floor` AS `floor`,
        `d`.`area` AS `area`,
        `a`.`dong` AS `dong`,
        `d`.`dealAmount` AS `dealAmount`,
        `a`.`lat` AS `lat`,
        `a`.`lng` AS `lng`
    FROM
        `hfrf`.`apt_info_deal` `a`
        JOIN `hfrf`.`housedeal` `d`
    WHERE
        `a`.`aptCode` = `d`.`aptCode`;


-- 시군구동 셀렉박스 처리 (데이터 삽입 후 처리)
-- 시도 코드 테이블 생성
CREATE TABLE hfrf.sidocode AS 
SELECT LEFT(dongCode, 2) AS sidoCode, sidoName
FROM hfrf.dongcode
WHERE gugunName IS NULL;

-- 구군 코드 테이블 생성
CREATE TABLE hfrf.guguncode AS 
SELECT LEFT(dongCode, 5) AS gugunCode, gugunName
FROM hfrf.dongcode
WHERE gugunName IS NOT NULL AND dongName IS NULL;

-- 부산 기준으로 구군 코드 조회
SELECT *
FROM hfrf.guguncode
WHERE guguncode LIKE '26%';

-- 종로구 기준으로 동 코드 조회
SELECT * 
FROM hfrf.dongcode
WHERE dongcode LIKE '11110%' AND dongName IS NOT NULL;

select * from sidocode;
select * from guguncode;
select * from dongcode;

select * from aptlike;
select * from houseinfo;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
