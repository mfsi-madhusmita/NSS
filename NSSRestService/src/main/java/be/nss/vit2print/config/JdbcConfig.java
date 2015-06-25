package be.nss.vit2print.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Jdbc Config Class
 * 
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:config.properties")
public class JdbcConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Environment environment;

	/**
	 * Return datasource after jndi lookup
	 */
	@Bean(name = "photovitPrototypeDatasource")
	public DataSource getDataSource() {
		return getDatasourceByDatasourceName(environment
				.getProperty("datasourceName"));
	}

	/**
	 * Return vit2print datasource after jndi lookup
	 */
	@Bean(name = "vit2printDatasource")
	public DataSource getVIT2PRINTDataSource() {
		return getDatasourceByDatasourceName(environment
				.getProperty("vit2printDatasourceName"));
	}

	/**
	 * Register transaction manager using datasource
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(getDataSource());
	}

	/**
	 * Register transaction manager using vit2print datasource
	 */
	@Bean
	public PlatformTransactionManager transactionManagerForVit2print() {
		return new DataSourceTransactionManager(getVIT2PRINTDataSource());
	}

	/**
	 * 
	 * Helper method to return datasource by datasourceName
	 */
	private DataSource getDatasourceByDatasourceName(String datasourceName) {
		DataSource dataSource = null;
		JndiTemplate jndiTemplate = new JndiTemplate();
		try {
			dataSource = (DataSource) jndiTemplate.lookup(datasourceName);
		} catch (NamingException e) {
			logger.error("JNDI NamingException for " + datasourceName, e);
		}
		return dataSource;
	}
}