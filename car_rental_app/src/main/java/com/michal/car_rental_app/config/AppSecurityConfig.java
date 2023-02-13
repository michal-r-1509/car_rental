package com.michal.car_rental_app.config;

import com.michal.car_rental_app.login.security.CustomAuthFailureHandler;
import com.michal.car_rental_app.login.security.CustomAuthSuccessHandler;
import com.michal.car_rental_app.login.security.CustomLogoutSuccessHandler;
import com.michal.car_rental_app.user.tools.MD5Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    private final CustomAuthSuccessHandler authSuccessHandler;
    private final CustomAuthFailureHandler authFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final DataSource dataSource;
    private final MD5Encoder md5Encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and().csrf().csrfTokenRepository(csrfTokenRepository()).disable() //allow to send requests
                .headers().frameOptions().sameOrigin()//allow to open h2.console
                .and()
//                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class)
                .authorizeHttpRequests().requestMatchers("/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .and()
                .formLogin().successHandler(authSuccessHandler).failureHandler(authFailureHandler)
                .usernameParameter("email").passwordParameter("password").permitAll()
                .and()
                .logout().invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler);
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "OPTIONS", "DELETE", "PUT", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT email, password, 'true' FROM users WHERE email=?")
                .authoritiesByUsernameQuery("SELECT email, role FROM users WHERE email=?");
    }

    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return md5Encoder.getMD5Hash(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return md5Encoder.getMD5Hash(rawPassword.toString()).equals(encodedPassword);
            }
        };
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

/*    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {

                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        cookie.setDomain("localhost");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }*/

}


/*@EnableWebMvc
@Configuration
public class AppMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}*/
