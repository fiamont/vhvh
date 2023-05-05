package se.sofiatherese.vhvh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import se.sofiatherese.vhvh.user.UserService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AppSecurityConfig {

    private final AppPasswordConfig bcrypt;

    private final UserService userService;

    @Autowired
    public AppSecurityConfig(AppPasswordConfig bcrypt, UserService userService) {
        this.bcrypt = bcrypt;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests( requests -> requests
                        .requestMatchers("/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
                )
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .authenticationProvider(authenticationOverride());
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

    public DaoAuthenticationProvider authenticationOverride() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bcrypt.bcryptPasswordEncoder());

        return provider;
    }
}
