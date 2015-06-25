package be.nss.vit2print.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import be.nss.vit2print.security.ApplicationAuthenticationFailureHandler;
import be.nss.vit2print.security.ApplicationAuthenticationFilter;
import be.nss.vit2print.security.ApplicationAuthenticationSuccessHandler;
import be.nss.vit2print.security.RemoveRolePrefix;
import be.nss.vit2print.security.SecurityExceptionFilter;

/**
 * Application Security Configuration
 */
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
	private UserDetailsService userService;

	@Autowired
	private SecurityExceptionFilter exceptionFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().anyRequest().authenticated().and()
				.exceptionHandling()
				.authenticationEntryPoint(authenticationEntryPoint).and()
				.logout().logoutSuccessHandler(logoutSuccessHandler).and()
				.csrf().disable();

		// http.authorizeRequests().antMatchers("/**").hasAuthority("SUPERUSER")
		// .and().exceptionHandling()
		// .authenticationEntryPoint(authenticationEntryPoint).and()
		// .logout().logoutSuccessHandler(logoutSuccessHandler).and()
		// .csrf().disable();

		/*
		 * register custom AuthenticationFilter before
		 * UsernamePasswordAuthenticationFilter
		 */
		http.addFilterBefore(getApplicationAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);

		http.addFilterBefore(exceptionFilter, ChannelProcessingFilter.class);

		// http.addFilterAfter(new ApplicationCSRFFilter(), CsrfFilter.class);
	}

	/**
	 * register custom user detail service and password encoder into
	 * AuthenticationManagerBuilder
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userService).passwordEncoder(
				getPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/**
	 * Build a custom authentication filter, registers authenticationManager,
	 * custom authenticationSuccessHandler and authenticationFailureHandler
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
	 * To Remove ROLE_ prefix from user roles
	 */
	public RemoveRolePrefix getRemoveRolePrefix() {
		return new RemoveRolePrefix();
	}

	/**
	 * Password Encoder
	 */
	@Bean
	public Md5PasswordEncoder getPasswordEncoder() {
		return new Md5PasswordEncoder();
	}
}