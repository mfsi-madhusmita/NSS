package be.nss.vit2print.repository;

import static be.nss.vit2print.security.ApplicationSecurityConstants.INPUT_USERNAME_PARAM;
import static be.nss.vit2print.security.ApplicationSecurityConstants.OUTPUT_USERNAME_PARAM;
import static be.nss.vit2print.security.ApplicationSecurityConstants.SP_GETUSER;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends JdbcDaoSupport {

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
	 * Repository method to load user by username
	 */
	public Map<String, Object> findUserByUsername(String username) {

		Map<String, Object> outParams = new SimpleJdbcCall(getJdbcTemplate())
				.withProcedureName(SP_GETUSER).execute(
						new MapSqlParameterSource().addValue(
								INPUT_USERNAME_PARAM, username));

		if (null == outParams.get(OUTPUT_USERNAME_PARAM)) {
			return null;
		}
		return outParams;
	}
}
