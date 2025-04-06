package sn.bfrost.myafiback.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sn.bfrost.myafiback.models.Role;
import sn.bfrost.myafiback.models.User;
import sn.bfrost.myafiback.repository.UserRepository;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/auth/**", "/actuator/**", "/error", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                .requestMatchers("/notes/**").hasRole("SECRETAIRE")
                                .requestMatchers("/notes/etudiant/**").hasRole("ETUDIANT")
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(authenticationProvider);
        return builder.build();
    }

    @Bean
    public UserDetailsService userDetailsCreate() {
        Role role = Role.ROLE_SECRETAIRE;

        if(repository.findByEmail("secretaire@myafi.com").isEmpty()){
            User admin = User.builder()
                    .email("secretaire@myafi.com")
                    .password(passwordEncoder.encode("admin"))
                    .nom("Afi")
                    .role(role)
                    .prenom("Secretaire")
                    .firstConnection(false)
                    .build();
            repository.save(admin);
            return new InMemoryUserDetailsManager(admin);
        }
        return null; // Pas besoin de faire quelque chose si l'utilisateur existe déjà
    }
}
