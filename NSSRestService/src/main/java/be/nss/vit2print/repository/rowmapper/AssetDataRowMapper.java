package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.AssetData;
import be.nss.vit2print.model.BasketAction;

public class AssetDataRowMapper implements RowMapper<AssetData> {

	@Override
	public AssetData mapRow(ResultSet rs, int rowNum) throws SQLException {

		BasketAction basketAction = new BasketAction();
		basketAction.setMail(rs.getString("mail"));
		basketAction.setApprove(rs.getString("approve"));
		basketAction.setExport(rs.getString("export"));
		basketAction.setDisapprove(rs.getString("disapprove"));
		basketAction.setDelete(rs.getString("delete"));
		basketAction.setExcel(rs.getString("excel"));
		basketAction.setDownload(rs.getString("download"));
		basketAction.setUpload(rs.getString("upload"));
		basketAction.setPrint(rs.getString("print"));
		basketAction.setMove(rs.getString("move"));
		basketAction.setCopy(rs.getString("copy"));

		AssetData assetData = new AssetData();
		assetData.setPages(rs.getInt("pages"));
		assetData.setAssetCount(rs.getInt("assetcount"));
		assetData.setBasketAssetCount(rs.getInt("basketassetcount"));
		assetData.setBasketAction(basketAction);

		return assetData;
	}
}
