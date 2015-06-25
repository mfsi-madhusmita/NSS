package be.nss.vit2print.repository;

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

import be.nss.vit2print.model.Level;
import be.nss.vit2print.model.SearchKeyword;
import be.nss.vit2print.model.SpecifiedLevel;
import be.nss.vit2print.repository.rowmapper.LevelRowMapper;
import be.nss.vit2print.repository.rowmapper.SearchKeywordRowMapper;
import be.nss.vit2print.repository.rowmapper.SpecifiedLevelRowMapper;

@Repository
public class LevelRepository extends JdbcDaoSupport {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String GETLEVELS_SQL = "call sp_PhotoVit_GetLevels(?,?,?)";
	private static final String GETSEARCHKEYWORDS_SQL = "call sp_PhotoVit_GetSearchKeywords(?)";
	private static final String GETSPECIFIEDLEVEL_SQL = "call sp_PhotoVit_GetSpecifiedLevel(?,?,?)";

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
	 * repository method to find levels by libraryid and categoryid
	 */
	public List<Level> findLevels(String username, String libraryId,
			String categoryId) {
		List<Level> levels = null;
		try {
			levels = getJdbcTemplate().query(GETLEVELS_SQL,
					new Object[] { username, libraryId, categoryId },
					new LevelRowMapper());
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
		}
		return levels;
	}

	/**
	 * repository method to find searchkeywords by libraryid and categoryid
	 */
	public List<SearchKeyword> findSearchKeywords(String libraryId) {
		List<SearchKeyword> searchKeywords = null;
		try {
			searchKeywords = getJdbcTemplate().query(GETSEARCHKEYWORDS_SQL,
					new Object[] { libraryId }, new SearchKeywordRowMapper());
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
		}
		return searchKeywords;
	}

	/**
	 * repository method to find specifiedlevel by libraryid and categoryid
	 */
	public SpecifiedLevel findSpecifiedLevel(String username, String libraryId,
			String categoryId) {
		SpecifiedLevel specifiedLevel = null;
		try {
			specifiedLevel = getJdbcTemplate().queryForObject(
					GETSPECIFIEDLEVEL_SQL,
					new Object[] { username, libraryId, categoryId },
					new SpecifiedLevelRowMapper());
		} catch (DataAccessException e) {
			logger.warn(e.getMessage(), e);
		}
		return specifiedLevel;
	}
}
