DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetAssets`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetAssets`(
    v_user_name VARCHAR(255), v_library_id INT(11), v_level_list_str TEXT, v_page INT(11),
    v_asset_per_page INT(11), v_is_recurse BOOL, v_sort_field VARCHAR(100), v_sort_order VARCHAR(25),
    v_search_val TEXT)
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch list of assets based on searched criteria given
	-- Created On: 05-26-2015
	-- Modified On: 05-28-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------

    DECLARE v_cur_position INT(11) DEFAULT 1 ;
	DECLARE v_remainder TEXT;
	DECLARE v_cur_string TEXT;
	DECLARE delimiter_length TINYINT UNSIGNED;
	DECLARE v_delimiter VARCHAR(1) DEFAULT ",";
    DECLARE v_asset_recurse_logic TEXT DEFAULT "";
    
    DECLARE v_lower_limit INT(11) DEFAULT 0;
    DECLARE v_upper_limit INT(11) DEFAULT 0;
    DECLARE v_limit_logic TEXT DEFAULT "";    
    
    DECLARE v_sort_logic TEXT DEFAULT ""; 
    
	SET v_remainder = v_level_list_str;
	SET delimiter_length = CHAR_LENGTH(v_delimiter);

	# Prepare recursive search criteria
	IF (v_is_recurse = false) THEN
		SET v_asset_recurse_logic = CONCAT(" a.category IN (" , REPLACE(QUOTE(v_level_list_str), ",","','") , ")");
    
    ELSE
		-- -----------------------------------------------------
		-- Loop thru each value of string separated by specified by delimeter
		-- -----------------------------------------------------
		WHILE CHAR_LENGTH(v_remainder) > 0 AND v_cur_position > 0 DO
			SET v_cur_position = INSTR(v_remainder, v_delimiter);

			IF v_cur_position = 0 THEN
			   SET v_cur_string = v_remainder;
			ELSE
              SET v_cur_string = LEFT(v_remainder, v_cur_position - 1);
			END IF;
			
			#SELECT v_cur_string;
            
            IF (v_asset_recurse_logic <> '') THEN
				SET v_asset_recurse_logic = CONCAT(v_asset_recurse_logic , " OR ");
            END IF;
            
			SET v_asset_recurse_logic = CONCAT(v_asset_recurse_logic , " (a.category" ,
				" LIKE '" ,  TRIM(v_cur_string) , "%')");
            
			SET v_remainder = SUBSTRING(v_remainder, v_cur_position + delimiter_length);
		END WHILE;
        
        SET v_asset_recurse_logic = CONCAT("(" , v_asset_recurse_logic , ")");
        
    END IF;
    
    #SELECT v_asset_recurse_logic;
    
    # Prepare pagination logic
    IF (v_page < 1) THEN
		SET v_page = v_page = 1;
    END IF;
    
    IF (v_asset_per_page < 1) THEN
		SET v_asset_per_page = 0;
    END IF;
    
    SET v_lower_limit = (v_page - 1) * v_asset_per_page;
    SET v_upper_limit = v_asset_per_page;
    SET v_limit_logic = CONCAT(" LIMIT " , v_lower_limit , "," , v_upper_limit);
    
    #SELECT v_limit_logic;
    
    # Prepare sort order logic
    IF ((v_sort_field <> '') AND (v_sort_order <> '')) THEN
		SET v_sort_logic = CONCAT(" ORDER BY " , v_sort_field , " " , v_sort_order);
	END IF;
    
    #SELECT v_sort_logic;
    
    SET @final_query = CONCAT("SELECT CONCAT(" , v_library_id , ", '_' , a.asset_id) AS asset_id, a.binary_name AS name, " ,
		"a.size, a.importtime, a.filetype, " , 
		"(IF (a.filetype = 'PDF' AND a.pages < 0, 'N', 'Y')) AS browseable, " ,
        "IF(a.approval <> '', a.approval, 'N') AS approval, ",
        " COUNT(ba.id) AS basketassetcount, cat.pcategory_id," ,
        "IF(cat.category = '0y', l.library_id, CONCAT(l.library_id, '_', cat.category_id)) AS cat_full_id, " ,
        "IF(cat.category = '0y', l.reference, cat.reference) AS cat_name,",
        " 'Y' AS zoom, " , 
        " 'Y' AS viewinfo, " ,
        "IF(cat.pcategory_id = 0, CONCAT(l.library_id, '+', l.reference), " ,
        "(SELECT category_id AS parent_col FROM photovit.category_" , v_library_id ,
		" WHERE category_id IN(REPLACE(QUOTE(cat.category), \"y\",\"','\")) AND category_id != cat.category_id)) AS parents," ,        
        
        " IF(IF(COUNT(uc.id) > 0, uc.mail, (IF(COUNT(ul.id) > 0, ul.mail, 'N'))) = 'Y', 'ENABLED', 'DISABLED') AS mail, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.export_hotfolder != '' AND uc.export_hotfolder = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.export_hotfolder != '' AND ul.export_hotfolder = 'Y', 'Y', 'N'), 'N'))) AS export, " , 
        " IF(COUNT(uc.id) > 0, IF(uc.approve != '' AND uc.approve = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.approve != '' AND ul.approve = 'Y', 'Y', 'N'), 'N'))) AS approve, " , 
        " IF(COUNT(uc.id) > 0, IF(uc.disapprove != '' AND uc.disapprove = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.disapprove != '' AND ul.disapprove = 'Y', 'Y', 'N'), 'N'))) AS disapprove, " , 
        " IF(COUNT(uc.id) > 0, IF(uc.excel != '' AND uc.excel = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.excel != '' AND ul.excel = 'Y', 'Y', 'N'), 'N'))) AS excel, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.download != '' AND uc.download = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.download != '' AND ul.download = 'Y', 'Y', 'N'), 'N'))) AS download, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.print != '' AND uc.print = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.print != '' AND ul.print = 'Y', 'Y', 'N'), 'N'))) AS print, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.remove != '' AND uc.remove = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.remove != '' AND ul.remove = 'Y', 'Y', 'N'), 'N'))) AS remove, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.copy != '' AND uc.copy = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.copy != '' AND ul.copy = 'Y', 'Y', 'N'), 'N'))) AS copy, " ,
        " IF(COUNT(uc.id) > 0, IF(uc.move != '' AND uc.move = 'Y', 'Y', 'N'), (IF(COUNT(ul.id) > 0, IF(ul.move != '' AND ul.move = 'Y', 'Y', 'N'), 'N'))) AS move, " ,
        " IF(l.scope = 'PRIVATE', 'N', 'Y') AS editinfo, " ,
        " IF(COUNT(la.id) > 0, 'DELETE', 'NOT APPLICABLE') AS lightbox, " ,
        " IF(COUNT(ba.id) > 0, 'DELETE', 'ADD') AS basket, " , 
        " IF(COUNT(lk.id) > 0, 'Y', 'N') AS similarsearch " ,
        " FROM photovit.asset_" , v_library_id , " a " ,
        " INNER JOIN photovit.library l ON l.library_id =  " , v_library_id ,
        " INNER JOIN photovit.category_" , v_library_id , " cat ON cat.category = a.category " ,
        " LEFT JOIN photovit.basket_asset ba ON ba.asset_id = a.asset_id " ,
		" LEFT JOIN vit2print.user usr ON usr.user_id = ba.user_id " , 
		" LEFT JOIN photovit.library_keyword lk ON lk.library_id = l.library_id AND similar = 'Y'" ,
		" LEFT JOIN photovit.lightbox_asset la ON la.asset_id = a.asset_id" ,
        " LEFT JOIN photovit.user_library ul ON ul.library_id = l.library_id " ,
        " LEFT JOIN photovit.user_category uc ON uc.category_id = cat.category_id "

        " WHERE a.status = 'active' AND a.googlefield LIKE '%" , 
			v_search_val , "%' AND " ,
			v_asset_recurse_logic ,
            " AND usr.username = '" , v_user_name , "'" ,
		" GROUP BY ba.asset_id " ,
            " " , v_sort_logic , " " , v_limit_logic);
        
	SELECT @final_query;
    
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