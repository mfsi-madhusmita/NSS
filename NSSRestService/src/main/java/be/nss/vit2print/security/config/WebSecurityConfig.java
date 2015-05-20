package be.nss.vit2print.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import be.nss.vit2print.security.ApplicationAuthenticationFailureHandler;
import be.nss.vit2print.security.ApplicationAuthenticationFilter;
import be.nss.vit2print.security.ApplicationAuthenticationSuccessHandler;
import be.nss.vit2print.security.RemoveRolePrefix;

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
	private AccessDeniedHandler accessDeniedHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/**")
				.access("hasRole('SUPERUSER')").and().exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler).and().logout()
				.logoutSuccessHandler(logoutSuccessHandler);

		http.csrf().disable();

		http.addFilterBefore(getApplicationAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);

		http.sessionManagement().sessionFixation().migrateSession();

	}

	@Override
	public void configure(AuthenticationManagerBuilder authManagerBuilder)
			throws Exception {
		authManagerBuilder.inMemoryAuthentication().withUser("nishant")
				.password("123456").roles("SUPERUSER");
		authManagerBuilder.inMemoryAuthentication().withUser("systemadmin")
				.password("123456").roles("SUPERUSER");
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/**
	 * Build a custom authentication filter, registers default
	 * authenticationManager, custom authenticationSuccessHandler and
	 * authenticationFailureHandler
	 */
	@Bean
	public ApplicationAuthenticationFilter getApplicationAuthenticationFilter()
			throws Exception {
		ApplicationAuthenticationFilter authenticationFilter = new ApplicationAuthenticationFilter();
		authenticationFilter.setAuthenticationManager(authenticationManager());
		authenticationFilter
				.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		authenticationFilter
				.setAuthenticationFailureHandler(authenticationFailureHandler);
		return authenticationFilter;
	}

	/**
	 * To Remove ROLE_ prefix
	 */
	public RemoveRolePrefix getRemoveRolePrefix() {
		return new RemoveRolePrefix();
	}
}
