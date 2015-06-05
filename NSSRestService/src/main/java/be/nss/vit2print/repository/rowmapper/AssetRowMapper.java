package be.nss.vit2print.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import be.nss.vit2print.model.Asset;
import be.nss.vit2print.model.AssetAction;
import be.nss.vit2print.model.AssetLevel;

public class AssetRowMapper implements RowMapper<Asset> {

	private static final String ASSET_THUMB_FILE_URL = "localhost:8080/NSS/photovit_action/Thumb?assetId=";

	@Override
	public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {

		AssetLevel assetLevel = new AssetLevel();
		assetLevel.setFullId(rs.getString("cat_full_id"));
		assetLevel.setName(rs.getString("cat_name"));
		assetLevel.setParentsAsString(rs.getString("parents"));

		AssetAction assetAction = new AssetAction();
		assetAction.setMail(rs.getString("mail"));
		assetAction.setExport(rs.getString("export"));
		assetAction.setApprove(rs.getString("approve"));
		assetAction.setLightbox(rs.getString("lightbox"));
		assetAction.setZoom(rs.getString("zoom"));
		assetAction.setDisapprove(rs.getString("disapprove"));
		assetAction.setEditinfo(rs.getString("editinfo"));
		assetAction.setExcel(rs.getString("excel"));
		assetAction.setDownload(rs.getString("download"));
		assetAction.setSimilarsearch(rs.getString("similarsearch"));
		assetAction.setPrint(rs.getString("print"));
		assetAction.setDelete(rs.getString("remove"));
		assetAction.setBasket(rs.getString("basket"));
		assetAction.setViewinfo(rs.getString("viewinfo"));
		assetAction.setMove(rs.getString("move"));
		assetAction.setCopy(rs.getString("copy"));

		Asset asset = new Asset();
		asset.setAssetId(rs.getString("asset_id"));
		asset.setName(rs.getString("name"));
		asset.setApproval(rs.getString("approval"));
		asset.setImportTime(rs.getString("importtime"));
		asset.setFileType(rs.getString("filetype"));
		asset.setBrowseAble(rs.getString("browseable"));
		asset.setSize(rs.getLong("size"));
		asset.setAssetLevel(assetLevel);
		asset.setAssetAction(assetAction);
		asset.setBasketAssetCount(rs.getInt("basketassetcount"));

		prepareThumbStringData(asset);

		return asset;
	}

	private void prepareThumbStringData(Asset asset) {
		String assetId = asset.getAssetId();
		StringBuilder thumbStringBuilder = null;
		if (assetId != null) {
			thumbStringBuilder = new StringBuilder(ASSET_THUMB_FILE_URL)
					.append(assetId);
		}
		String thumbString = thumbStringBuilder != null ? thumbStringBuilder
				.toString() : null;
		asset.setThumbString(thumbString.toString());
	}
}
