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
import org.springframework.jndi.JndiTemplate;

/**
 * Jdbc Config Class
 * 
 */
@Configuration
@PropertySource("classpath:config.properties")
public class JdbcConfig {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private Environment environment;

	/**
	 * Return datasource after jndi lookup
	 */
	@Bean
	public DataSource getDataSource() {
		DataSource dataSource = null;
		JndiTemplate jndiTemplate = new JndiTemplate();
		String datasourceName = environment.getProperty("datasourceName");
		try {
			dataSource = (DataSource) jndiTemplate.lookup(datasourceName);
		} catch (NamingException e) {
			logger.error("NamingException for " + datasourceName, e);
		}
		return dataSource;
	}
}
