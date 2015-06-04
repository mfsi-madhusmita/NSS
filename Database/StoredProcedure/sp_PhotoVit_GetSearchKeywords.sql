DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetSearchKeywords`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetSearchKeywords`(v_library_id INT(11))
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch Specified level detail keywords only for library not for category
	-- Created On: 05-19-2015
	-- Modified On: 05-19-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------
	
	SELECT k.keyword_id, 
		k.name, 
		k.keywordgroup
    FROM photovit.library_keyword lk
    INNER JOIN photovit.keyword k
		ON k.keyword_id = lk.keyword_id
	WHERE lk.library_id = v_library_id
		AND lk.search = 'Y';
    
END $$
DELIMITER ;