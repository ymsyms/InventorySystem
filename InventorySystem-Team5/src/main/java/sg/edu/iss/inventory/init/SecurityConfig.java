package sg.edu.iss.inventory.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import sg.edu.iss.inventory.service.UserLoginService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier("UserLoginService")
	UserLoginService userLoginService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userLoginService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http.authorizeRequests().antMatchers("/product/**")
		.access("hasRole('administrator')").and().formLogin()
		.loginPage("/login").failureUrl("/login?error")
		.usernameParameter("userId")
		.passwordParameter("password")
		.and().logout().logoutSuccessUrl("/login?logout")
		.and().csrf()
		.and().exceptionHandling().accessDeniedPage("/403");
		
//        http.authorizeRequests().antMatchers("/",
//                "/login").permitAll().anyRequest().authenticated();
//        
//        http.formLogin().loginPage("/login").defaultSuccessUrl(
//                "/default").usernameParameter("email").passwordParameter(
//                        "password");
//
//        http.logout().logoutRequestMatcher(
//                new AntPathRequestMatcher("/logout")).logoutSuccessUrl(
//                        "/login");
	}

	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
