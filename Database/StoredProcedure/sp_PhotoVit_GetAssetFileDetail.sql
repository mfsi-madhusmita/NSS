DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetAssetFileDetail`$$
CREATE 
DEFINER=`root`@`%` 
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetAssetFileDetail`(
    v_library_id INT(11), v_asset_id INT(11))
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch list of keyword with keyword groups
	-- Created On: 05-28-2015
	-- Modified On: 05-29-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------
	SET @final_query = CONCAT("SELECT binary_name, name, filepath, filetype " ,
		" FROM photovit.asset_" , v_library_id , " a ",
		" WHERE a.asset_id = " , v_asset_id);
    
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