package hu.webuni.transportation.config;

import hu.webuni.transportation.security.JwtSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static hu.webuni.transportation.config.RightConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtSecurityFilter jwtSecurityFilter;

    @Bean
    PasswordEncoder getPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(inMemoryDaoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
     //   http.httpBasic()
     //           .and()
                http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                .antMatchers("/api/addresses/search").hasAuthority(GENERAL.name())
                .antMatchers("/api/addresses/**").hasAuthority(ADDRESS_MANAGER.name())
                .antMatchers("/api/transportPlans/**").hasAuthority(TRANSPORT_MANAGER.name())
                .anyRequest().denyAll();

                http.addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
    }

    public UserDetailsService inMemoryUserDetailsService() {
        UserDetails user1 = User.builder()
                .username("TestAddress")
                .password("address")
                .authorities(ADDRESS_MANAGER.name())
                .passwordEncoder((password) -> getPassWordEncoder().encode(password))
                .build();

        UserDetails user2 = User.builder()
                .username("TestTransport")
                .password("transport")
                .authorities(TRANSPORT_MANAGER.name())
                .passwordEncoder((password) -> getPassWordEncoder().encode(password))
                .build();

        UserDetails user3 = User.builder()
                .username("TestDefault")
                .password("any")
                .authorities(GENERAL.name())
                .passwordEncoder((password) -> getPassWordEncoder().encode(password))
                .build();

        return new InMemoryUserDetailsManager(user1,user2,user3);
    }

    @Bean
    public DaoAuthenticationProvider inMemoryDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(inMemoryUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPassWordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
