package se.sofiatherese.vhvh.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import se.sofiatherese.vhvh.config.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppSecurityConfig {

    private final AppConfig appConfig;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests( requests -> requests
                        .requestMatchers("http://localhost:3000/login")
                                .anonymous()
                        .requestMatchers("/api/**", "http://localhost:3000/", "http://localhost:3000/404", "http://localhost:3000/comingSoon", "http://localhost:3000/createAccount").permitAll()
                        .requestMatchers("http://localhost:3000/admin").hasRole("ADMIN")
                        .requestMatchers("http://localhost:3000/myPage").hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .loginPage("http://localhost:3000/login")
                .permitAll()
                .and()
                .authenticationProvider(appConfig.authenticationOverride())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors().configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
            corsConfiguration.addAllowedMethod("DELETE");
            corsConfiguration.addAllowedMethod("POST");
            corsConfiguration.addAllowedMethod("GET");
            corsConfiguration.addAllowedMethod("OPTIONS");
            corsConfiguration.addAllowedMethod("PUT");
            return corsConfiguration;
        });
        return http.build();
    }

}
