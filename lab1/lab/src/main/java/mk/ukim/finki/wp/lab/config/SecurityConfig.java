package mk.ukim.finki.wp.lab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public pages and basic viewing
                        .requestMatchers(
                                "/",
                                "/books",
                                "/bookReservation",
                                "/reservationConfirmation",
                                "/css/**",
                                "/js/**"
                        ).permitAll()

                        // Admin-only operations for books
                        .requestMatchers(
                                "/books/book-form",
                                "/books/book-form/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/books/add",
                                "/books/edit/**",
                                "/books/delete/**"
                        ).hasRole("ADMIN")

                        // Admin-only operations for authors
                        .requestMatchers(
                                "/authors/author-form",
                                "/authors/author-form/**"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/authors/add",
                                "/authors/edit/**",
                                "/authors/delete/**"
                        ).hasRole("ADMIN")

                        // Everyone can see list of authors
                        .requestMatchers("/authors").permitAll()

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/books", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/books")
                        .permitAll()
                );

        return http.build();
    }
}


