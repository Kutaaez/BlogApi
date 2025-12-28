package kz.seppaku.postmanBlog.config;

import kz.seppaku.postmanBlog.services.UserService;
import kz.seppaku.postmanBlog.services.impl.UserServiceImpl;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.repositories.RoleRepository;
import kz.seppaku.postmanBlog.mapper.UserMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new org.springframework.security.web.authentication.HttpStatusEntryPoint(org.springframework.http.HttpStatus.UNAUTHORIZED)));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/error", "/css/**", "/js/**").permitAll()

                // --- ИСПРАВЛЕНО: hasAnyRole -> hasAnyAuthority ---
                // Используем полные названия ролей, как они лежат в БД (с префиксом ROLE_)
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/threads/**", "/reviews/**").permitAll()

                .requestMatchers(HttpMethod.DELETE, "/threads/**", "/reviews/**")
                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MODERATOR")

                .requestMatchers(HttpMethod.POST, "/threads/**", "/reviews/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN")

                .requestMatchers(HttpMethod.PUT, "/threads/**", "/reviews/**")
                .hasAnyAuthority("ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN")
                // --------------------------------------------------

                .requestMatchers("/users/**").authenticated()
                .anyRequest().authenticated()
        );

        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}