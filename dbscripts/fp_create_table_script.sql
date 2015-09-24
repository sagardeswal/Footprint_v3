DROP TABLE IF EXISTS `fp_event_details`;
CREATE TABLE IF NOT EXISTS `fp_event_details` (
  `ev_id` int(2) NOT NULL AUTO_INCREMENT,
  `ev_loc_id` int(2) NOT NULL,
  `ev_title` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `ev_description` varchar(1000) COLLATE latin1_general_ci NOT NULL,
  `ev_repeat_weekly` varchar(5) COLLATE latin1_general_ci NOT NULL,
  `ev_start_date` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_end_date` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_start_time_hour` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_start_time_minute` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_end_time_hour` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_end_time_minute` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `ev_address` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`ev_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=10 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_place_details`
--

DROP TABLE IF EXISTS `fp_place_details`;
CREATE TABLE IF NOT EXISTS `fp_place_details` (
  `pl_ID` int(2) NOT NULL AUTO_INCREMENT,
  `pl_loc_id` int(2) NOT NULL,
  `pl_title` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `pl_description` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `pl_category` varchar(30) COLLATE latin1_general_ci NOT NULL,
  `active` int(1) NOT NULL DEFAULT '1',
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`pl_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=23 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_pl_ev_checkin`
--

DROP TABLE IF EXISTS `fp_pl_ev_checkin`;
CREATE TABLE IF NOT EXISTS `fp_pl_ev_checkin` (
  `fp_ch_id` int(2) NOT NULL AUTO_INCREMENT,
  `fp_ch_usr_id` int(2) NOT NULL,
  `fp_ch_pl_id` int(2) NOT NULL,
  `fp_ch_ev_id` int(2) NOT NULL,
  `fp_ch_st_id` int(2) NOT NULL,
  `fp_ch_created_by` int(2) NOT NULL,
  `fp_ch_created_on` date NOT NULL,
  `fp_ch_updated_by` int(2) NOT NULL,
  `fp_ch_updated_on` date NOT NULL,
  `fp_ch_time` datetime NOT NULL,
  PRIMARY KEY (`fp_ch_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_pl_images`
--

DROP TABLE IF EXISTS `fp_pl_images`;
CREATE TABLE IF NOT EXISTS `fp_pl_images` (
  `img_id` int(2) NOT NULL AUTO_INCREMENT,
  `img_pl_id` int(2) NOT NULL,
  `img_blob` longblob NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_story`
--

DROP TABLE IF EXISTS `fp_story`;
CREATE TABLE IF NOT EXISTS `fp_story` (
  `st_id` int(2) NOT NULL AUTO_INCREMENT,
  `st_text` varchar(1000) COLLATE latin1_general_ci NOT NULL,
  `st_usr_id` int(2) NOT NULL,
  `st_loc_id` int(2) NOT NULL,
  `st_pl_id` int(2) NOT NULL,
  `st_created_by` int(2) NOT NULL,
  `st_created_on` date NOT NULL,
  `st_updated_by` int(2) NOT NULL,
  `st_updated_on` date NOT NULL,
  `st_is_active` char(1) COLLATE latin1_general_ci NOT NULL,
  `st_pr_id` int(2) NOT NULL,
  PRIMARY KEY (`st_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_story_privacy`
--

DROP TABLE IF EXISTS `fp_story_privacy`;
CREATE TABLE IF NOT EXISTS `fp_story_privacy` (
  `st_pr_id` int(2) NOT NULL AUTO_INCREMENT,
  `st_pr_title` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `st_pr_description` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `st_pr_updated_by` int(2) NOT NULL,
  `st_pr_updated_on` date NOT NULL,
  PRIMARY KEY (`st_pr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_user_details`
--

DROP TABLE IF EXISTS `fp_user_details`;
CREATE TABLE IF NOT EXISTS `fp_user_details` (
  `usr_ID` int(2) NOT NULL AUTO_INCREMENT,
  `usr_fullname` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `usr_mobile` varchar(12) COLLATE latin1_general_ci NOT NULL,
  `usr_email` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `usr_passwordhashcode` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `hash` varchar(32) COLLATE latin1_general_ci NOT NULL,
  `active` int(1) NOT NULL DEFAULT '1',
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`usr_ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=23 ;

-- --------------------------------------------------------

--
-- Table structure for table `fp_usr_images`
--

DROP TABLE IF EXISTS `fp_usr_images`;
CREATE TABLE IF NOT EXISTS `fp_usr_images` (
  `img_id` int(2) NOT NULL AUTO_INCREMENT,
  `img_usr_id` int(2) NOT NULL,
  `img_blob` longblob NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`img_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `pl_location_details`
--

DROP TABLE IF EXISTS `pl_location_details`;
CREATE TABLE IF NOT EXISTS `pl_location_details` (
  `loc_id` int(2) NOT NULL AUTO_INCREMENT,
  `loc_longitude` varchar(40) COLLATE latin1_general_ci NOT NULL,
  `loc_latitude` varchar(40) COLLATE latin1_general_ci NOT NULL,
  `created_on` datetime NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=23 ;

-- --------------------------------------------------------

--
-- Table structure for table `usr_privacy`
--

DROP TABLE IF EXISTS `usr_privacy`;
CREATE TABLE IF NOT EXISTS `usr_privacy` (
  `usr_pr_id` int(2) NOT NULL AUTO_INCREMENT,
  `usr_pr_title` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `usr_pr_description` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `usr_pr_updated_by` int(2) NOT NULL,
  `usr_pr_updated_on` date NOT NULL,
  PRIMARY KEY (`usr_pr_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci AUTO_INCREMENT=1 ;