DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetAssetCountForLevels`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetAssetCountForLevels`()
BEGIN

	-- -----------------------------------------------------
	-- Desc: Manipulate assets count against some specific library and update on temporary table 'photovit.asset_tmp'
	-- Created On: 06-05-2015
	-- Modified On: 06-05-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------

	DECLARE v_finished INTEGER DEFAULT 0;
	DECLARE v_id TEXT DEFAULT NULL;
	DECLARE v_cntAsset TEXT DEFAULT NULL;
 
	-- declare cursor for employee email
	DEClARE id_cursor CURSOR FOR 
	SELECT id, cntAsset FROM photovit.asset_tmp;

	-- declare NOT FOUND handler
	DECLARE CONTINUE HANDLER 
		FOR NOT FOUND SET v_finished = 1;
 
	OPEN id_cursor;

	get_id: LOOP

	FETCH id_cursor INTO v_id, v_cntAsset;

	IF v_finished = 1 THEN 
		LEAVE get_id;
	END IF;

	SET @update_asset_cnt = CONCAT("UPDATE photovit.asset_tmp tmp SET tmp.cntAsset= (SELECT COUNT(asset_id)" ,
		" FROM photovit.asset_" , v_id , ") WHERE id = " , v_id);

	#SELECT @update_asset_cnt;
	PREPARE STMT FROM @update_asset_cnt;
	EXECUTE STMT;

	END LOOP get_id;

	CLOSE id_cursor;
	
END $$
DELIMITER ;