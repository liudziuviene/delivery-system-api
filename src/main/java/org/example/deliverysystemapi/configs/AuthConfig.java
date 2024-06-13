package org.example.deliverysystemapi.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableMethodSecurity
public class AuthConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests
                                .requestMatchers("/couriers/**").hasAnyRole("COURIER", "ADMINISTRATOR")
                                .requestMatchers("/customers/**").hasAnyRole("ADMINISTRATOR", "COURIER")
                                .requestMatchers("/orders/**").hasAnyRole("ADMINISTRATOR", "COURIER")
                                .requestMatchers("/deliveryAssignments/**").hasAnyRole("COURIER", "ADMINISTRATOR")
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails courier = User
                .withUsername("courier")
                .password(passwordEncoder().encode("courier1"))
                .roles("COURIER")
                .build();

        UserDetails administrator = User
                .withUsername("administrator")
                .password(passwordEncoder().encode("administrator1"))
                .roles("ADMINISTRATOR")
                .build();

        return new InMemoryUserDetailsManager(courier, administrator);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
