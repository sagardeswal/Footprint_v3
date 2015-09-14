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