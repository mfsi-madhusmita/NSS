package be.nss.vit2print.service;

import static be.nss.vit2print.security.ApplicationSecurityConstants.OUTPUT_IS_SUPERUSER_PARAM;
import static be.nss.vit2print.security.ApplicationSecurityConstants.OUTPUT_PASSWORD_PARAM;
import static be.nss.vit2print.security.ApplicationSecurityConstants.OUTPUT_USERNAME_PARAM;
import static be.nss.vit2print.security.ApplicationSecurityConstants.SUPER_USER_ROLE;
import static be.nss.vit2print.security.ApplicationSecurityConstants.USER_ROLE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import be.nss.vit2print.repository.UserRepository;

/**
 * Custom UserDetailsService to load user data from database into
 * org.springframework.security.core.userdetails.User object
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Map<String, Object> output = userRepository
				.findUserByUsername(username);

		if (null == output) {
			throw new UsernameNotFoundException("Wrong Username");
		}
		return new User((String) output.get(OUTPUT_USERNAME_PARAM),
				(String) output.get(OUTPUT_PASSWORD_PARAM), true, true, true,
				true,
				getAuthorities((String) output.get(OUTPUT_IS_SUPERUSER_PARAM)));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(
			String isSuperUser) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		String role = "Y".equals(isSuperUser) ? SUPER_USER_ROLE : USER_ROLE;
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}
}