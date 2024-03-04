package com.clothes_store.configurations;

import com.clothes_store.filter.JwtFilter;
import com.clothes_store.model.UserRoles;
import com.clothes_store.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/authenticate").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/products/all").permitAll()
                .antMatchers("/api/products/details/**").permitAll() // Permit access to product details by ID
                .antMatchers("/api/products/getByCategory").permitAll()
                .antMatchers("/api/products/delete/").hasAuthority(UserRoles.admin_role.name())
                .antMatchers("/api/products/add").hasAuthority(UserRoles.admin_role.name())
                .antMatchers("/api/products/update/**").permitAll()
                .antMatchers("/api/cart/removeCartItem").permitAll()
                .antMatchers("api/cart/add").hasAuthority(UserRoles.user_role.name())
                .antMatchers("api/cart/get").hasAuthority(UserRoles.user_role.name())
                .antMatchers("api/orders/add").hasAuthority(UserRoles.user_role.name())
                .antMatchers("api/orders/get**").permitAll()
                .antMatchers("api/orders/delete/**").permitAll()
                .antMatchers("/api/categories/all").permitAll()
                .antMatchers(HttpMethod.POST, "/api/orders/add").hasAuthority(UserRoles.user_role.name()) // Restrict addOrder to user_role
                .antMatchers("/api/orders/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails ramesh = User.builder().username("ramesh").password(passwordEncoder()
//                .encode("password")).roles("USER").build();
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//                .encode("admin")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(ramesh, admin);
//    }

}
