package com.bhishma.ams.authentication.config;


import com.bhishma.ams.service.student.StudentService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/teacher/**").hasRole("TEACHER")
                                .requestMatchers("/faculty/**").hasAnyRole("ADMIN","TEACHER")
                                .requestMatchers(POST,"/faculty").hasRole("ADMIN")
                                .requestMatchers(PUT,"/faculty").hasRole("ADMIN")
                                .requestMatchers(POST, "/attendance").hasRole("TEACHER")
                                .requestMatchers(GET, "/attendance").hasRole("TEACHER")
                                .requestMatchers(GET,"attendance/subject").hasRole("TEACHER")
                                .requestMatchers("/attendance/admin/**").hasRole("ADMIN")
                                .requestMatchers("/student/admin/**").hasRole("ADMIN")
                                .requestMatchers("/subject/admin/**").hasRole("ADMIN")
                                .requestMatchers(GET, "/subject").hasAnyRole("TEACHER", "ADMIN")
                                .requestMatchers("/student-profile/**").hasAnyRole("STUDENT")
                                .requestMatchers(POST,"/subject-teacher").hasRole("ADMIN")
                                .requestMatchers(PUT,"/subject-teacher/primary").hasRole("ADMIN")
                                .requestMatchers("/subject-teacher/**").hasRole("TEACHER")
                                .requestMatchers(POST,"/routine").hasRole("ADMIN")
                                .requestMatchers(PUT,"/routine").hasRole("ADMIN")
                                .requestMatchers(GET,"/routine/**").hasAnyRole("TEACHER","ADMIN")
                                .requestMatchers(PUT,"/routine/substitute").hasAnyRole("TEACHER")

                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http
                .addFilterBefore((Filter) new CollegeIdAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Custom HandlerInterceptor for CollegeId authorization
    public class CollegeIdAuthorizationFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (request.getRequestURI().startsWith("/student-profile")) {
                String collegeId = request.getParameter("collegeId");
                if (collegeId != null) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                        String userId = userDetails.getUsername();
                        if (userId.equals(collegeId)) {
                            filterChain.doFilter(request, response);
                            return;
                        }
                    }
                }
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
