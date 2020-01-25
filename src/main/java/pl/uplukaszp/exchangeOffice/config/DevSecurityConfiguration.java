package pl.uplukaszp.exchangeOffice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Profile("dev")
@Order(value=1)
public class DevSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/h2_console/**").permitAll()
        .antMatchers("/user").permitAll();
        http.formLogin();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}
