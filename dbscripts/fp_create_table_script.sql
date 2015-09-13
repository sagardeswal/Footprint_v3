CREATE TABLE  `a6596261_footp`.`fp_user_details` (
`usr_ID` INT( 2 ) NOT NULL AUTO_INCREMENT ,
`usr_fullname` VARCHAR( 100 ) NOT NULL ,
`usr_mobile` VARCHAR( 12 ) NOT NULL ,
`usr_email` VARCHAR( 100 ) NOT NULL ,
`usr_username` VARCHAR( 100 ) NOT NULL ,
`usr_passwordhashcode` VARCHAR( 100 ) NOT NULL ,
'hash' VARCHAR(32) NOT NULL ,
'active' INT(1) NOT NULL DEFAULT '0'
PRIMARY KEY (  `usr_ID` )
) ENGINE = MYISAM