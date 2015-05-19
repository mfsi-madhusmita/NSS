package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.LevelAction;
import be.nss.vit2print.model.SpecifiedLevel;

public class SpecifiedLevelRowMapper implements RowMapper<SpecifiedLevel> {

	private static final String yFlag="Y";
	private static final String nFlag="N";
	
	@Override
	public SpecifiedLevel mapRow(ResultSet rs, int rowNum) throws SQLException {

		SpecifiedLevel specifiedLevel = new SpecifiedLevel();
		specifiedLevel.setAssetCount(rs.getInt("assetCount"));
		specifiedLevel.setHasChildren(rs.getString("hasChildren"));
		specifiedLevel.setHasCollections(rs.getString("hasCollections"));
		specifiedLevel.setId(rs.getInt("id"));
		specifiedLevel.setIdPath(rs.getString("idPath"));
		specifiedLevel.setName(rs.getString("name"));
		specifiedLevel.setNamePath(rs.getString("namePath"));
		specifiedLevel.setScope(rs.getString("scope"));
		specifiedLevel.setType(rs.getString("type"));

		LevelAction levelAction = new LevelAction();
		levelAction.setSearchDoubles(rs.getString("searchDoubles"));
		levelAction.setAddSubLevel(rs.getString("addSubLevel").equals("1")?yFlag:nFlag);
		levelAction.setApproveAsset(rs.getString("approveAsset").equals("1")?yFlag:nFlag);
		levelAction.setCopyAsset(rs.getString("copyAsset").equals("1")?yFlag:nFlag);
		levelAction.setDeleteLevel(rs.getString("deleteLevel").equals("1")?yFlag:nFlag);
		levelAction.setDisapproveAsset(rs.getString("disapproveAsset").equals("1")?yFlag:nFlag);
		levelAction.setEditLevel(rs.getString("editLevel").equals("1")?yFlag:nFlag);
		levelAction.setMoveAsset(rs.getString("moveAsset").equals("1")?yFlag:nFlag);
		levelAction.setUploadAsset(rs.getString("uploadAsset").equals("1")?yFlag:nFlag);

		specifiedLevel.setLevelAction(levelAction);

		return specifiedLevel;
	}
}
