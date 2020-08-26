package com.yiqiandewo.config;

import com.yiqiandewo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IRoleService roleService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //不同权限的用户可以访问的页面不同
        http.authorizeRequests()
                .antMatchers("/").permitAll();

        //登录
        http.formLogin()
                .loginPage("/userLogin")   //配置自定义登录页面
                .defaultSuccessUrl("/main", true); //使用successForwardUrl()发送post请求，报错DefaultHandlerExceptionResolver


        http.logout().logoutSuccessUrl("/");

        http.csrf().disable();

        //remember me
        http.rememberMe().rememberMeParameter("remember");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(roleService).passwordEncoder(bCryptPasswordEncoder());
    }

    //不登录就可以访问静态资源
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/asserts/css/**");
        web.ignoring().antMatchers("/asserts/js/**");
        web.ignoring().antMatchers("/asserts/img/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
