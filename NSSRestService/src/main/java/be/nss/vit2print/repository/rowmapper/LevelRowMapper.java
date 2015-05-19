package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.Level;
import be.nss.vit2print.model.LevelAction;

public class LevelRowMapper implements RowMapper<Level> {

	private static final String DEFAULT_TYPE = "CATEGORY";

	@Override
	public Level mapRow(ResultSet rs, int rowNum) throws SQLException {

		Level level = new Level();
		level.setAssestCount(rs.getInt("cntAsset"));
		level.setHasChildren(rs.getString("hasChildren"));
		level.setId(rs.getString("id"));
		level.setIdPath(rs.getString("idPath"));
		level.setName(rs.getString("name"));
		level.setNamePath(rs.getString("pathName"));
		level.setParentId(rs.getInt("parentId"));
		level.setScope(rs.getString("scope"));
		level.setType(DEFAULT_TYPE);

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

		level.setLevelAction(levelAction);

		return level;
	}
}
