package be.nss.vit2print.repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import be.nss.vit2print.exception.APIException;
import static be.nss.vit2print.exception.ExceptionMessages.UNKNOWN_ASSET_ID;
import be.nss.vit2print.model.ThumbFileDetail;
import be.nss.vit2print.repository.rowmapper.ThumbFileDetailRowMapper;

@Repository
public class ThumbFileRepository extends JdbcDaoSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String GETASSETFILE_SQL = "call sp_PhotoVit_GetAssetFileDetail(?,?)";

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
	 * repository method to find Asset File Details by libraryId and assetId
	 */
	public ThumbFileDetail findAssetFileData(String libraryId, String assetId) {
		ThumbFileDetail thumbFileData = null;
		try {
			thumbFileData = getJdbcTemplate().queryForObject(GETASSETFILE_SQL,
					new Object[] { libraryId, assetId },
					new ThumbFileDetailRowMapper());
		} catch (EmptyResultDataAccessException e) {
			logger.warn(e.getMessage());
			throw new APIException(UNKNOWN_ASSET_ID, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException e) {
			logger.warn(e.getMessage());
			throw new APIException(e.getCause().getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return thumbFileData;
	}
}
