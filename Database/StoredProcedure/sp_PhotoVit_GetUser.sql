DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetUser`$$
CREATE 
	DEFINER=`root`@`%`
PROCEDURE `photovit_prototype`.`sp_PhotoVit_GetUser`(IN in_username varchar(255),
	OUT out_username varchar(255), OUT out_passwd varchar(255), OUT out_isSuperUser varchar(1))
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch user data on the basis of username
	-- Created On: 05-21-2015
	-- Modified On: 05-21-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------
	
	SELECT username,passwd, issuperuser 
		INTO out_username, out_passwd, out_isSuperUser
	FROM `vit2print`.user
	WHERE username = in_username;
    
END $$
DELIMITER ;