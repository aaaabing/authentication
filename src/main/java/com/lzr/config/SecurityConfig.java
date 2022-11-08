package com.lzr.config;


import com.lzr.filter.PathAccessDecisionManager;
import com.lzr.filter.RolePermissionMetadataSource;
import com.lzr.filter.SecurityAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;



/**
 * @author lzr
 * @date 2021/9/8 19:31
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SecurityAuthenticationTokenFilter securityAuthenticationTokenFilter;
    @Autowired
    private RolePermissionMetadataSource permissionMetadataSource;
    @Autowired
    private PathAccessDecisionManager pathAccessDecisionManager;

    /**
     * 白名单
     */
    private String[] white_url = {
            "/login"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF
                .csrf().disable()
                .exceptionHandling()
                .and()
                .authorizeRequests()
                // 放行白名单
                .antMatchers(white_url).permitAll()
                // OPTIONS请求不验证
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 剩下所有请求都需要认证
                .anyRequest().authenticated()
                .accessDecisionManager(pathAccessDecisionManager)
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(permissionMetadataSource);
                        return o;
                    }
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth
                // 设置UserDetailsService
                .userDetailsService(userDetailsService)
                // 使用BCrypt进行加密
                .passwordEncoder(passwordEncoder);
    }


    /**
     * AuthenticationManager 是一个接口，是认证方法的入口，接收一个Authentication对象作为参数
     * ProviderManager 它是AuthenticationManager的一个实现类，实现了authenticate(Authentication authentication)方法，还有一个成员变量
     * List<AuthenticationProvider> providers
     * providers就是所有的认证器，ProviderManager会逐一调用所有认证器，只要其中一个认证器认证通过，则认证通过
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}