DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetAssetKeywords`$$
CREATE 
DEFINER=`root`@`%` 
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetAssetKeywords`(
    v_user_name VARCHAR(255), v_library_id INT(11), v_asset_id_list TEXT)
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch list of keyword with keyword groups
	-- Created On: 05-28-2015
	-- Modified On: 05-29-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------
	SET @final_query = CONCAT("SELECT ak.asset_id, lk.id, lk.library_id, lk.sort_index, k.name, ak.value, " , 
		"lk.edit AS editable,CONCAT(lkg.sort_index, '+', lkg.keywordgroup) AS keywordgroup " ,
		"FROM photovit.library_keyword lk ",
		"INNER JOIN photovit.keyword k ON k.keyword_id = lk.keyword_id ",
		"INNER JOIN photovit.asset_keyword ak ON ak.library_id = lk.library_id AND ak.keyword_id = lk.keyword_id " ,
		"INNER JOIN photovit.library_keywordgroups lkg ON lkg.keywordgroup = k.keywordgroup " ,
		"WHERE lk.library_id = " , v_library_id , " AND k.keywordgroup = 'TECHNICAL' " ,
			"AND ak.asset_id IN(" , REPLACE(QUOTE(v_asset_id_list), ",","','") , ")" ,
		"GROUP BY ak.asset_id, k.name");
    
	#SELECT @final_query;
    
     	-- -----------------------------------------------------
	-- Execute the above query string
	-- -----------------------------------------------------
	PREPARE STMT FROM @final_query;
	EXECUTE STMT;
  
    -- -----------------------------------------------------
	-- Deallocate prepared statement
	-- -----------------------------------------------------
	DEALLOCATE PREPARE STMT;
    
END $$
DELIMITER ;