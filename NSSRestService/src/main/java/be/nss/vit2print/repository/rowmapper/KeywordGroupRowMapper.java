package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.Keyword;
import be.nss.vit2print.model.KeywordGroup;

public class KeywordGroupRowMapper implements RowMapper<KeywordGroup> {

	@Override
	public KeywordGroup mapRow(ResultSet rs, int rowNum) throws SQLException {

		Keyword keyword = new Keyword();
		keyword.setId(rs.getInt("id"));
		keyword.setSortIndex(rs.getInt("sort_index"));
		keyword.setTranslation(rs.getString("name"));
		keyword.setName(rs.getString("name"));
		keyword.setValue(rs.getString("value"));
		keyword.setEditAble(rs.getString("editable"));

		KeywordGroup keywordGroup = new KeywordGroup();
		keywordGroup.setKeywordGroupAsString(rs.getString("keywordgroup"));
		keywordGroup.setKeyword(keyword);
		keywordGroup.setAssetId(rs.getString("asset_id"));

		return keywordGroup;
	}
}
