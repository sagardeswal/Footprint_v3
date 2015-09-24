CREATE TABLE  `a6596261_footp`.`fp_user_details` (
`usr_ID` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`usr_fullname` VARCHAR( 100 ) NOT NULL ,
`usr_mobile` VARCHAR( 12 ) NOT NULL ,
`usr_email` VARCHAR( 100 ) NOT NULL ,
`usr_username` VARCHAR( 100 ) NOT NULL ,
`usr_passwordhashcode` VARCHAR( 100 ) NOT NULL ,
'hash' VARCHAR(32) NOT NULL ,
'active' INT(1) NOT NULL DEFAULT '1'
PRIMARY KEY (  `usr_ID` )
) ENGINE = MYISAM


CREATE TABLE  `a6596261_footp`.`fp_place_details` (
`pl_ID` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`pl_title` VARCHAR( 100 ) NOT NULL ,
`pl_description` VARCHAR( 100 ) NOT NULL ,
`pl_location` VARCHAR( 50 ) NOT NULL ,
`pl_category` VARCHAR( 30 ) NOT NULL ,
`active` INT( 1 ) NOT NULL DEFAULT  '1',
PRIMARY KEY (  `pl_ID` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`fp_usr_images` (
`img_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`img_usr_id` INT( 2 ) NOT NULL ,
`img_blob` LONGBLOB NOT NULL ,
PRIMARY KEY (  `img_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`fp_pl_images` (
`img_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`img_pl_id` INT( 2 ) NOT NULL ,
`img_blob` LONGBLOB NOT NULL ,
PRIMARY KEY (  `img_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`pl_location_details` (
`loc_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`loc_longitude` DOUBLE NOT NULL ,
`loc_latitude` DOUBLE NOT NULL ,
PRIMARY KEY (  `loc_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`fp_pl_ev_checkin` (
`fp_ch_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`fp_ch_usr_id` INT( 2 ) NOT NULL ,
`fp_ch_pl_id` INT( 2 ) NOT NULL ,
`fp_ch_ev_id` INT( 2 ) NOT NULL ,
`fp_ch_st_id` INT( 2 ) NOT NULL ,
`fp_ch_created_by` INT( 2 ) NOT NULL ,
`fp_ch_created_on` DATE NOT NULL ,
`fp_ch_updated_by` INT( 2 ) NOT NULL ,
`fp_ch_updated_on` DATE NOT NULL ,
`fp_ch_time` DATETIME NOT NULL ,
PRIMARY KEY (  `fp_ch_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`fp_story` (
`st_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`st_text` VARCHAR( 1000 ) NOT NULL ,
`st_usr_id` INT( 2 ) NOT NULL ,
`st_loc_id` INT( 2 ) NOT NULL ,
`st_pl_id` INT( 2 ) NOT NULL ,
`st_created_by` INT( 2 ) NOT NULL ,
`st_created_on` DATE NOT NULL ,
`st_updated_by` INT( 2 ) NOT NULL ,
`st_updated_on` DATE NOT NULL ,
`st_is_active` CHAR( 1 ) NOT NULL ,
`st_pr_id` INT( 2 ) NOT NULL ,
PRIMARY KEY (  `st_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`usr_privacy` (
`usr_pr_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`usr_pr_title` VARCHAR( 10 ) NOT NULL ,
`usr_pr_description` VARCHAR( 100 ) NOT NULL ,
`usr_pr_updated_by` INT( 2 ) NOT NULL ,
`usr_pr_updated_on` DATE NOT NULL ,
PRIMARY KEY (  `usr_pr_id` )
) ENGINE = MYISAM

CREATE TABLE  `a6596261_footp`.`fp_story_privacy` (
`st_pr_id` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`st_pr_title` VARCHAR( 10 ) NOT NULL ,
`st_pr_description` VARCHAR( 100 ) NOT NULL ,
`st_pr_updated_by` INT( 2 ) NOT NULL ,
`st_pr_updated_on` DATE NOT NULL ,
PRIMARY KEY (  `st_pr_id` )
) ENGINE = MYISAM
