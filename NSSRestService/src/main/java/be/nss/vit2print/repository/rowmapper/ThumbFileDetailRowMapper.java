package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.ThumbFileDetail;

public class ThumbFileDetailRowMapper implements RowMapper<ThumbFileDetail> {

	@Override
	public ThumbFileDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		ThumbFileDetail thumFileData = new ThumbFileDetail();
		thumFileData.setFileBinaryName(rs.getString("binary_name"));
		thumFileData.setFileName(rs.getString("name"));
		thumFileData.setFilePath(rs.getString("filepath"));
		thumFileData.setFileType(rs.getString("filetype"));

		return thumFileData;
	}
}
