package by.ivankov.delivery.config;

import by.ivankov.delivery.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserServiceImpl userService;
    private final WebMvcConfig mvcConfig;

    @Autowired
    public WebSecurityConfig(UserServiceImpl userService, WebMvcConfig mvcConfig) {
        this.userService = userService;
        this.mvcConfig = mvcConfig;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(mvcConfig.getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers( "/guest/**", "/img/**", "/css/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/guest/login")
                .loginProcessingUrl("/guest/login")
                .defaultSuccessUrl("/guest/home")
                .failureUrl("/guest/login?error")
                .and()
                .logout()
                .logoutUrl("/guest/logout")
                .logoutSuccessUrl("/guest/home")
                .permitAll();
    }
}
