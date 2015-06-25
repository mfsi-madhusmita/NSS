package be.nss.vit2print.repository;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import be.nss.vit2print.dto.AssetSearchDTO;
import be.nss.vit2print.model.Asset;
import be.nss.vit2print.model.KeywordGroup;
import be.nss.vit2print.repository.rowmapper.AssetRowMapper;
import be.nss.vit2print.repository.rowmapper.KeywordGroupRowMapper;

@Repository
public class AssetSearchRepository extends JdbcDaoSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String GETASSETS_SQL = "call sp_PhotoVit_GetAssets(?,?,?,?,?,?,?,?,?)";
	private static final String GETKEYWORDGROUPS_SQL = "call sp_PhotoVit_GetAssetKeywords(?,?,?)";

	@Autowired
	@Qualifier("photovitPrototypeDatasource")
	private DataSource datasource;

	/**
	 * setDatasource into JdbcDaoSupport
	 */
	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}

	/**
	 * Repository method for findAssets
	 */
	public List<Asset> findAssets(AssetSearchDTO assetSearch, String username) {
		List<Asset> assets = null;
		try {
			logger.info("Executing sql query " + GETASSETS_SQL + ", "
					+ new Date().toString());
			assets = getJdbcTemplate().query(
					GETASSETS_SQL,
					new Object[] { username, assetSearch.getLibraryId(),
							assetSearch.getSearchLevel(),
							assetSearch.getPage(),
							assetSearch.getAssetPerPage(),
							assetSearch.getRecurse(), assetSearch.getSortBy(),
							assetSearch.getSortOrder(),
							assetSearch.getSearchValue() },
					new AssetRowMapper());
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
		}
		logger.info("Execution of sql query " + GETASSETS_SQL
				+ " has completed" + ", " + new Date().toString());
		return assets;
	}

	/**
	 * Repository method for findKeywordGroups
	 */
	public List<KeywordGroup> findKeywordGroups(String username, int libraryId,
			String assetIdsAsAString) {
		List<KeywordGroup> keywordGroups = null;
		try {
			logger.info("Executing sql query " + GETKEYWORDGROUPS_SQL + ", "
					+ new Date().toString());
			keywordGroups = getJdbcTemplate().query(GETKEYWORDGROUPS_SQL,
					new Object[] { username, libraryId, assetIdsAsAString },
					new KeywordGroupRowMapper());
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
		}
		logger.info("Execution of sql query " + GETKEYWORDGROUPS_SQL
				+ " has completed" + ", " + new Date().toString());
		return keywordGroups;
	}
}