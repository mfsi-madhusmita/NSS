DELIMITER $$
DROP PROCEDURE IF EXISTS `photovit_prototype`.`sp_PhotoVit_GetParentcategoryList`$$
CREATE 
DEFINER=`root`@`%`
PROCEDURE  `photovit_prototype`.`sp_PhotoVit_GetParentcategoryList`(v_library_id INT(11))
BEGIN
DECLARE v_finished INTEGER DEFAULT 0;
DECLARE v_ids TEXT DEFAULT NULL;
DECLARE v_category_id TEXT DEFAULT NULL;
 
 -- declare cursor for employee email
 DEClARE ids_cursor CURSOR FOR 
 SELECT ids, category_id FROM photovit.tmp;
 
 -- declare NOT FOUND handler
 DECLARE CONTINUE HANDLER 
        FOR NOT FOUND SET v_finished = 1;
 
 OPEN ids_cursor;
 
 get_ids: LOOP
 
 FETCH ids_cursor INTO v_ids, v_category_id;
 
 IF v_finished = 1 THEN 
	LEAVE get_ids;
 END IF;
 
 SET @parent_category_list = CONCAT("UPDATE photovit.tmp tmp SET tmp.category= (SELECT GROUP_CONCAT(CONCAT(" , v_library_id, ", '_', cat.category_id, '+', cat.reference) SEPARATOR '|')" ,
	" FROM photovit.category_3 cat WHERE cat.category_id IN(" , left(v_ids,length(v_ids)-1) , ") AND cat.category_id <> " , v_category_id , 
    ") WHERE tmp.category_id = " , v_category_id);

#SELECT @parent_category_list;
PREPARE STMT FROM @parent_category_list;
EXECUTE STMT;

 END LOOP get_ids;
 
 CLOSE ids_cursor;
 
END$$
 
DELIMITER ;