package emsi.ebanking.security;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().usersByUsernameQuery("select email, password from compte where email = ?")
				.authoritiesByUsernameQuery("select email, role from compte where email = ?").dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/user/**", "/budget/**").hasRole("CLIENT")
				.antMatchers("/compte/**", "/transaction/**").hasRole("ADMIN")
				.antMatchers("/login/**").permitAll()
				.antMatchers("/**").authenticated();

		http.csrf().ignoringAntMatchers("/h2-console/**");
		http.headers().frameOptions().disable();
	}

}
