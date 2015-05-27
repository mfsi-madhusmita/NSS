package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.Keyword;
import be.nss.vit2print.model.KeywordGroup;

public class KeywordGroupRowMapper implements RowMapper<KeywordGroup> {

	@Override
	public KeywordGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Keyword keyword=new Keyword();
		keyword.setId(rs.getInt("id"));
		keyword.setUniqueId(rs.getLong("uniqueid"));
		keyword.setSortIndex(rs.getInt("sortindex"));
		keyword.setTranslation(rs.getString("translation"));
		keyword.setName(rs.getString("name"));
		keyword.setValue(rs.getString("value"));
		keyword.setEditAble(rs.getString("editable"));
		
		KeywordGroup keywordGroup=new KeywordGroup();
		keywordGroup.setKeywordGroupAsString(rs.getString("keywordGroupAsString"));
		keywordGroup.setKeyword(keyword);
		
		return null;
	}
}
