DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetSpecifiedLevel`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetSpecifiedLevel`(
    v_user_name VARCHAR(255), v_library_id INT(11), v_category_id INT(11))
BEGIN

	-- -----------------------------------------------------
	-- Desc: Fetch Specified level detail
	-- Created On: 05-19-2015
	-- Modified On: 06-08-2015
	-- Created By: MINDFIRE SOLUTIONS
	-- -----------------------------------------------------
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
    DECLARE v_user_id INT(11) DEFAULT 0;
    
    # Check for SuperUser
    SELECT issuperuser, user_id INTO v_is_super_user, v_user_id
	FROM vit2print.user usr
	/*INNER JOIN vit2print.authstrings auth
		ON auth.user_id = usr.user_id*/
	WHERE /*auth.authstring = v_authstring*/
		usr.username = v_user_name;
    
    #SELECT @v_is_super_user;
    
    # Check for ADMIN
    IF ((v_is_super_user IS NULL) OR (v_is_super_user = 'N')) THEN 
		SELECT IF (COUNT(admin_ap.id) > 0, 1, 0) INTO @v_is_admin
		FROM vit2print.administrator_application admin_ap
		INNER JOIN vit2print.application ap
			ON ap.application_id = admin_ap.application_id
		/*INNER JOIN vit2print.authstrings auth
			ON auth.user_id = admin_ap.user_id*/
		INNER JOIN user usr
			ON usr.user_id = admin_ap.user_id
		WHERE ap.name = 'PhotoVit'
			/*AND auth.authstring = v_authstring*/
            AND usr.username = v_user_name;
            
            IF ((v_is_admin > 0) AND (v_is_admin IS NOT NULL) AND (! isempty(v_is_admin))) THEN
				SET v_user_type = 'ADMIN';
            END IF;
    ELSE
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
    
    IF (v_library_id > 0) THEN
		SET @v_specified_level_query = CONCAT("SELECT lib.library_id AS id," ,
			"lib.reference AS name,",
			"lib.reference AS namePath," ,
			"IF (COUNT(coll.id) > 0, 'Y', 'N') AS hasCollections," ,
			"COUNT(DISTINCT ass.asset_id) AS assetCount," ,
			"lib.usergrouppath AS idPath," ,
			"IF (" , v_category_id , " > 0  , 'CATEGORY', 'LIBRARY') AS type," ,
			"IF (COUNT(cat.category_id) > 0, 'Y', 'N') AS hasChildren," ,
			"lib.scope AS scope, '",
			v_searchDoubles , "' AS searchDoubles," ,
			v_uploadAsset , " AS uploadAsset," ,
			v_moveAsset , " AS moveAsset," ,
			v_copyAsset , " AS copyAsset," ,
			v_addSubLevel , " AS addSubLevel," ,
			v_editLevel , " AS editLevel," ,
			v_deleteLevel , " AS deleteLevel," ,
			v_approveAsset , " AS approveAsset," ,
			v_disapproveAsset , " AS disapproveAsset " ,
		"FROM photovit.library lib " ,
		"LEFT JOIN photovit.collection coll " ,
			"ON coll.library_id = lib.library_id AND coll.parent_id = 0 " ,
		"LEFT JOIN photovit.category_" , v_library_id , " cat " ,
			"ON cat.pcategory_id = " , v_category_id ,
		" LEFT JOIN photovit.asset_" , v_library_id , " ass "
			" ON 1", 
		" WHERE lib.library_id = " , v_library_id);
        
	ELSE
    
		DROP TABLE IF EXISTS photovit.asset_tmp;
		SET @tmp_table_query = CONCAT("CREATE TEMPORARY TABLE IF NOT EXISTS photovit.asset_tmp  AS (SELECT library_id AS id, 0 AS cntAsset" ,
       " FROM photovit.library " ,
		"WHERE (user_id = " , v_user_id , ") " , 
			"OR ((usergrouppath='0y' AND scope = 'PUBLIC') " ,
            "OR (usergrouppath LIKE '0y", v_user_id , "%' AND scope = 'PUBLIC')))");
         #SELECT @tmp_table_query;
         
		-- -----------------------------------------------------
		-- Execute the above query string
		-- -----------------------------------------------------
		PREPARE STMT FROM @tmp_table_query;
		EXECUTE STMT;  
        
        # Manipulate asset count against all available library based on criteria
        call photovit_prototype.sp_PhotoVit_GetAssetCountForLevels();
        
        #SELECT * FROM photovit.asset_tmp;  
    
		SET @v_specified_level_query = CONCAT("SELECT lib.library_id AS id," ,
			"lib.reference AS name,",
			"lib.reference AS namePath," ,
			"IF (COUNT(coll.id) > 0, 'Y', 'N') AS hasCollections," ,
			"COUNT(DISTINCT tmp.id) AS assetCount," ,
			"lib.usergrouppath AS idPath," ,
			"IF (" , v_category_id , " > 0  , 'CATEGORY', 'LIBRARY') AS type," ,
			"'N' AS hasChildren," ,
			"lib.scope AS scope, '",
			v_searchDoubles , "' AS searchDoubles," ,
			v_uploadAsset , " AS uploadAsset," ,
			v_moveAsset , " AS moveAsset," ,
			v_copyAsset , " AS copyAsset," ,
			v_addSubLevel , " AS addSubLevel," ,
			v_editLevel , " AS editLevel," ,
			v_deleteLevel , " AS deleteLevel," ,
			v_approveAsset , " AS approveAsset," ,
			v_disapproveAsset , " AS disapproveAsset " ,
		"FROM photovit.library lib " ,
		"LEFT JOIN photovit.collection coll " ,
			"ON coll.library_id = lib.library_id AND coll.parent_id = 0 " ,
		" LEFT JOIN photovit.asset_tmp tmp ON tmp.id = lib.library_id "
		" WHERE lib.library_id = " , v_library_id);		
    
	END IF;
    
     #SELECT @v_specified_level_query;
 	-- -----------------------------------------------------
	-- Execute the above query string
	-- -----------------------------------------------------
	PREPARE STMT FROM @v_specified_level_query;
	EXECUTE STMT;
  
    -- -----------------------------------------------------
	-- Deallocate prepared statement
	-- -----------------------------------------------------
	DEALLOCATE PREPARE STMT;
    
    DROP TABLE IF EXISTS photovit.asset_tmp;
	
END $$
DELIMITER ;