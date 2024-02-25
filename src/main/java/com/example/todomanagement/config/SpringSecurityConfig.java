package com.example.todomanagement.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SpringSecurityConfig {
    private UserDetailsService userDetailsService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // disable csrf
        http.csrf(AbstractHttpConfigurer::disable)
                // authorization
                .authorizeHttpRequests((authorize) ->{
//                    // ADMIN ROLE
//                    authorize.requestMatchers(HttpMethod.POST, "/v1/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.PUT, "v1/api/**").hasRole("ADMIN");
//                    authorize.requestMatchers(HttpMethod.DELETE, "v1/api/**").hasRole("ADMIN");
//                    // ADMIN, USER ROLE
//                    authorize.requestMatchers(HttpMethod.GET, "v1/api/**").hasAnyRole("ADMIN", "USER");
//                    authorize.requestMatchers(HttpMethod.PATCH, "v1/api/**").hasAnyRole("ADMIN", "USER");

                    authorize.requestMatchers(HttpMethod.GET, "v1/api/**").permitAll();

                    authorize.anyRequest().authenticated();
        }).httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails phuquocchamp = User.builder()
//                .username("phuquocchamp")
//                .password(passwordEncoder().encode("user@12345"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("user@12345"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(phuquocchamp, admin);
//    }
}
