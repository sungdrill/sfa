package com.cellbiotech.config;

import javax.sql.DataSource;

import com.cellbiotech.filter.AjaxSessionCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * spring security 설정
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;
    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAfter(new AjaxSessionCheckFilter(), ExceptionTranslationFilter.class)
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/exmallBatch").permitAll()
                .antMatchers("/exmallBatch2").permitAll()
                .antMatchers("/duolacBatch").permitAll()
                .antMatchers("/duolacBatch2").permitAll()
                .antMatchers("/duzonDeliveryQtBatch").permitAll()
                .antMatchers("/duzonDeliveryQtBatchByCnt").permitAll()
                .antMatchers("/prodBasicStockBatch").permitAll()
                .antMatchers("/prodBasicStockBatchByCnt").permitAll()
                .antMatchers("/sfaBatch").permitAll()
                .antMatchers("/sfaBatchByCnt").permitAll()
                .antMatchers("/home").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/cpList").permitAll()
                .antMatchers("//api/**").permitAll()
                .antMatchers("/mng/**").hasAuthority("MANAGER")
                .antMatchers("/inv/**").hasAuthority("MANAGER")
                .antMatchers("/user/**").hasAuthority("USER").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/home")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").and().exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .and().sessionManagement().maximumSessions(2).expiredUrl("/login")
                .and().invalidSessionUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**", "/vendor/**");
    }

}