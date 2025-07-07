package readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class SecurityConfig {

    private final ReaderRepository readerRepository;

    @Autowired
    public SecurityConfig(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    /**
     * Tells Spring Security how to load user data.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> readerRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    /**
     * Configures the HTTP security chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/{reader}", "/style.css").hasRole("READER")
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Authentication manager bean.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
