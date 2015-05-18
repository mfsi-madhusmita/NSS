package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.LevelAction;
import be.nss.vit2print.model.SpecifiedLevel;

public class SpecifiedLevelRowMapper implements RowMapper<SpecifiedLevel> {

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
		levelAction.setAddSubLevel(rs.getString("addSubLevel"));
		levelAction.setApproveAsset(rs.getString("approveAsset"));
		levelAction.setCopyAsset(rs.getString("copyAsset"));
		levelAction.setDeleteLevel(rs.getString("deleteLevel"));
		levelAction.setDisapproveAsset(rs.getString("disapproveAsset"));
		levelAction.setEditLevel(rs.getString("editLevel"));
		levelAction.setMoveAsset(rs.getString("moveAsset"));
		levelAction.setSearchDoubles(rs.getString("searchDoubles"));
		levelAction.setUploadAsset(rs.getString("uploadAsset"));

		specifiedLevel.setLevelAction(levelAction);

		return specifiedLevel;
	}
}
