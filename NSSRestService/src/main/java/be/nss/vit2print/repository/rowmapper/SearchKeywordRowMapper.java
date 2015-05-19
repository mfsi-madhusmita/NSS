package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.SearchKeyword;

public class SearchKeywordRowMapper implements RowMapper<SearchKeyword> {

	@Override
	public SearchKeyword mapRow(ResultSet rs, int rowNum) throws SQLException {
		SearchKeyword searchKeyword = new SearchKeyword();
		searchKeyword.setId(rs.getInt("keyword_id"));
		searchKeyword.setGroup(rs.getString("keywordgroup"));
		searchKeyword.setName(rs.getString("name"));
		return searchKeyword;
	}
}
