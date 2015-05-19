package be.nss.vit2print.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import be.nss.vit2print.security.ApplicationAuthenticationFailureHandler;
import be.nss.vit2print.security.ApplicationAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private ApplicationAuthenticationFailureHandler authenticationFailureHandler;

	@Autowired
	private ApplicationAuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().withUser("nishant").password("123456")
				.roles("ADMIN");
		auth.inMemoryAuthentication().withUser("systemadmin")
				.password("123456").roles("ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/**")
				.access("hasRole('ROLE_ADMIN')");
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(
				authenticationEntryPoint);
		http.formLogin().failureHandler(authenticationFailureHandler);
		http.formLogin().successHandler(authenticationSuccessHandler);
		http.logout().logoutSuccessHandler(logoutSuccessHandler);
	}

}
