DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetLevels`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetLevels`(
    v_user_name VARCHAR(255), v_library_id INT(11), v_category_id INT(11))
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch list of levels against a library
	-- Created On: 05-14-2015
	-- Modified On: 05-19-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------

	DECLARE v_level_select_query TEXT DEFAULT '';
    DECLARE v_cnt_asset_query TEXT DEFAULT '';
    
    -- Action details --
	DECLARE v_user_type VARCHAR(50) DEFAULT 'USER';
    
    DECLARE v_searchDoubles VARCHAR(50) DEFAULT 'NOT ALLOWED';
    DECLARE v_uploadAsset BOOLEAN DEFAULT true;
    DECLARE v_moveAsset BOOLEAN DEFAULT true;
    DECLARE v_copyAsset BOOLEAN DEFAULT true;
    DECLARE v_addSubLevel BOOLEAN DEFAULT true;
    DECLARE v_editLevel BOOLEAN DEFAULT false;
    DECLARE v_deleteLevel BOOLEAN DEFAULT false;
    DECLARE v_approveAsset BOOLEAN DEFAULT true;
    DECLARE v_disapproveAsset BOOLEAN DEFAULT true;
    
    DECLARE v_is_super_user VARCHAR(2) DEFAULT 'N';
    DECLARE v_is_admin VARCHAR(2) DEFAULT 'N';
    
    # Check for SuperUser
    SELECT issuperuser INTO v_is_super_user
	FROM vit2print.user usr
	/*INNER JOIN vit2print.authstrings auth
		ON auth.user_id = usr.user_id*/
	WHERE /*auth.authstring = v_authstring*/
		usr.username = v_user_name;
    
    #SELECT v_is_super_user, (v_is_super_user IS NULL), (isempty(v_is_super_user));
    
    # Check for ADMIN
    IF ((v_is_super_user IS NULL) OR (v_is_super_user = 'N')) THEN 
		#SELECT 'admin check';
		SELECT IF (COUNT(admin_ap.id) > 0, 1, 0) INTO v_is_admin
		FROM vit2print.administrator_application admin_ap
		INNER JOIN vit2print.application ap
			ON ap.application_id = admin_ap.application_id
		/*INNER JOIN vit2print.authstrings auth
			ON auth.user_id = admin_ap.user_id*/
		INNER JOIN vit2print.user usr
			ON usr.user_id = admin_ap.user_id
		WHERE ap.name = 'PhotoVit'
			/*AND auth.authstring = v_authstring*/
            AND usr.username = v_user_name;
            
            IF ((v_is_admin > 0) AND (v_is_admin IS NOT NULL) AND (! isempty(v_is_admin))) THEN
				SET v_user_type = 'ADMIN';
            END IF;
    ELSE
		#SELECT 'super user done';
		SET v_user_type = 'SUPERUSER';    
    END IF;
    
    IF ((v_library_id > 0) AND (v_category_id > 0)) THEN
		SET @get_action_query =  "";
    ELSE
		SET @get_action_query =  "";
    END IF;
    
    # Search Doubles
    IF (v_user_type = 'USER') THEN
		SET v_searchDoubles = 'ENABLED';
    END IF;  
    
    # Allow Edit and Delete
    IF (v_category_id > 0) THEN
		SET v_editLevel = true;
        SET v_deleteLevel =  true;
    END IF;
    
    SET @v_action_detail_query = CONCAT("'" , v_searchDoubles , "' AS searchDoubles,",
		v_uploadAsset , " AS uploadAsset,",
        v_moveAsset , " AS moveAsset,",
        v_copyAsset , " AS copyAsset,",
        v_addSubLevel , " AS addSubLevel,",
        v_editLevel , " AS editLevel,",
        v_deleteLevel , " AS deleteLevel,",
        v_approveAsset , " AS approveAsset,",
        v_disapproveAsset , " AS disapproveAsset");    
        
	-- End of Action details --
    /*
    SET v_cnt_asset_query = IF (v_category_id > 0 ,
    	CONCAT("(SELECT COUNT(ass.asset_id) " , 
		"FROM photovit.asset_" , v_library_id , " ass " ,
		"WHERE ass.category LIKE  CONCAT((SELECT category FROM photovit.category_" , v_library_id , " cat " , 
			"WHERE cat.category_id = " , v_category_id ,
			"), '%') " ,
		") cntAsset"),
        
        CONCAT("(SELECT COUNT(ass.asset_id) " , 
		"FROM photovit.asset_" , v_library_id , " ass ",
		") cntAsset"));
	*/
    SET v_cnt_asset_query = CONCAT("(SELECT COUNT(ass.asset_id) " , 
		"FROM photovit.asset_" , v_library_id , " ass " ,
		"WHERE ass.category LIKE  CONCAT((SELECT category FROM photovit.category_" , v_library_id , " cat " , 
			"WHERE cat.category_id = cat1.category_id), '%') " ,
		") cntAsset");        
        
	#SELECT v_cnt_asset_query;
    
    SET @get_level_query =  CONCAT("SELECT id, name, idPath, scope, ", 
		" IF (pcategory_id > 0, CONCAT(parentId, '_', pcategory_id), parentId) AS parentId, " ,
		" IF (pathName != null, pathName, CONCAT(reference , ' > ' , name)) AS pathName, cntAsset," ,
        " IF (cntAsset > 0 , 'Y' , 'N') AS hasChildren, ",
        @v_action_detail_query ,
	" FROM (",
	"SELECT cat1.pcategory_id, cat1.reference AS name, cat1.category, " , 
    	" cat1.category AS idPath, CONCAT(lib.library_id, '_', cat1.category_id) AS id, " ,
        " lib.library_id AS parentId, ",
        " IF (" , v_category_id , " > 0, 'CATEGORY', 'LIBRARY') AS type, ",
        " lib.*," ,
    	"(SELECT GROUP_CONCAT(reference SEPARATOR ' > ') AS pathName FROM (" , 
			" SELECT @r AS _id," ,
				"(SELECT @r := pcategory_id FROM photovit.category_" , v_library_id , " WHERE category_id = _id LIMIT 1) AS parent_id," ,
				" @l := @l + 1 AS lvl " ,
			" FROM " ,
				"(SELECT @r := " , v_category_id , " , @l := 0) vars," ,
				" photovit.category_" , v_library_id , " m " ,
			"WHERE @r <> 0) T1 " ,
		"JOIN photovit.category_" , v_library_id , " T2 " ,
		"ON T1._id = T2.category_id " , 
		"ORDER BY T1.lvl DESC"        
	") AS pathName," , 
	v_cnt_asset_query ,
	" FROM photovit.category_" , v_library_id , " cat1 ", 
	" LEFT JOIN photovit.category_" , v_library_id , " cat2 " , 
		" ON cat2.category_id = cat1.pcategory_id " ,
	" LEFT JOIN photovit.asset_" , v_library_id , " ass " , 
		" ON ass.asset_id IN (" ,
				"SELECT category " ,
				" FROM photovit.category_" , v_library_id , " cat" ,
				" WHERE cat.pcategory_id >= " , v_category_id ,
			") " ,
	" INNER JOIN photovit.library lib " ,
		" ON lib.library_id = " , v_library_id ,
	" WHERE cat1.pcategory_id = " , v_category_id , 
	" GROUP BY cat1.category_id) baseTable"
    );
    
    #SELECT @get_level_query;
    
 	-- -----------------------------------------------------
	-- Execute the above query string
	-- -----------------------------------------------------
	PREPARE STMT FROM @get_level_query;
	EXECUTE STMT;
  
    -- -----------------------------------------------------
	-- Deallocate prepared statement
	-- -----------------------------------------------------
	DEALLOCATE PREPARE STMT;
	
END $$
DELIMITER ;