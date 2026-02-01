package com.juniorg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // to let spring know this is config file
@EnableWebSecurity // Dont use default now use the following security chain
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//
//        return httpSecurity
//                .cors(Customizer.withDefaults())
//                .csrf(customizer -> customizer.disable())
//                .authorizeHttpRequests(auth -> auth
//                        //Allow swagger and open ai
//                        .requestMatchers(
//                                "/v3/api-docs/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html"
//                        ).permitAll()
//                        // Make /users/login available to everyone
//                        .requestMatchers("/health","/users/login","/enquiry").permitAll()
//                        // For GET requests, allow users with either role NORMAL or ADMIN
//                        .requestMatchers(HttpMethod.GET, "/**").hasAnyRole("NORMAL", "ADMIN")
//                        // All other requests require the ADMIN role
//                        .anyRequest().hasRole("ADMIN")
//                )
////                        request.anyRequest().authenticated())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//
    /// /         httpSecurity.formLogin(Customizer.withDefaults())
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1) Public endpoints
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/health",
                                "/users/login",
                                "/users/forgot-password",
                                "/users/reset-password",
                                "/enquiry"
                        ).permitAll()

                        // 2) Attendance endpoints
                        // - Teachers and Admins can mark, update, and delete
                        // - Everyone with a valid role can view (GET)
                        .requestMatchers(HttpMethod.GET, "/attendances/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/attendances/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/attendances/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/attendances/**")
                        .hasAnyRole("TEACHER", "ADMIN")

                        // 3) Allow GET everywhere to TEACHER or ADMIN
                        .requestMatchers(HttpMethod.GET, "/**")
                        .hasAnyRole("TEACHER", "ADMIN")

                        // 4) Grant student-specific operations to NORMAL or ADMIN
                        //    (admit, update, delete under /students/**)
                        .requestMatchers(HttpMethod.POST, "/students/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/students/**")
                        .hasAnyRole("TEACHER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/students/**")
                        .hasAnyRole("TEACHER", "ADMIN")

                        // 5) Everything else (non-GET, non-students) requires ADMIN
                        .anyRequest()
                        .hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
