package be.nss.vit2print.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import be.nss.vit2print.utility.AuthstringUpdater;

/**
 * Spring Java Configuration
 */
@Configuration
@ComponentScan(basePackages = "be.nss.vit2print")
@EnableWebMvc
@Import(JdbcConfig.class)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthstringUpdater authstringUpdater;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authstringUpdater);
	}
}
