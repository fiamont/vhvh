package se.sofiatherese.vhvh.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.sofiatherese.vhvh.user.UserRepository;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final UserRepository userRepository;
    private Logger logger;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                logger = LoggerFactory.getLogger(AppConfig.class);
                try{
                    logger.info("Loading user " + username);
                    return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
                } catch (UsernameNotFoundException e) {
                    logger.error("User not found with username: " + username);
                    throw e;
                }
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationOverride() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(bcryptPasswordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
