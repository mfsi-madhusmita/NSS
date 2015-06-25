package be.nss.vit2print.repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import be.nss.vit2print.exception.APIException;

/**
 * Repositoty class for authstring timestamp operation
 */
@Repository
public class AuthstringUpdaterRepository extends JdbcDaoSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String UPDATE_AUTHSTRING_SQL = "update authstrings set accesstime=? where authstring=?";

	@Autowired
	@Qualifier("vit2printDatasource")
	private DataSource datasource;

	/**
	 * setDatasource into JdbcDaoSupport
	 */
	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}

	public int updateAuthstringTimestamp(String authstring, String newAccessTime) {
		int updateCount = 0;
		logger.info("Going to update accesstime: " + newAccessTime
				+ " for authstring: " + authstring);
		try {
			updateCount = getJdbcTemplate().update(UPDATE_AUTHSTRING_SQL,
					new Object[] { newAccessTime, authstring });
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new APIException(e.getCause().getMessage(),
					HttpStatus.BAD_REQUEST);
		}
		return updateCount;
	}
}
