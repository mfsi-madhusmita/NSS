package be.nss.vit2print.repository;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class LevelRepository extends JdbcDaoSupport {

	@Autowired
	private DataSource datasource;

	/**
	 * setDatasource into JdbcDaoSupport
	 */
	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}

	/**
	 * Repository method for getLevels API
	 */
	public String getLevels(String authenticationString, String libraryId,
			Map<String, Object> optionData) {
		return authenticationString + ", " + libraryId;
	}

}
