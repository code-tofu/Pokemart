package tfip.b3.mp.pokemart.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tfip.b3.mp.pokemart.security.AuthEntryPointImpl;
import tfip.b3.mp.pokemart.security.OncePerRequestFilterImpl;
import tfip.b3.mp.pokemart.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userDetailsSvc;
    @Autowired
    private AuthEntryPointImpl authExceptionHandler;
    @Autowired
    private OncePerRequestFilterImpl jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthProvider() {
        DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
        daoAuthProvider.setUserDetailsService(userDetailsSvc);
        daoAuthProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            //configure filter settings
            .authorizeHttpRequests(auth -> 
            auth.requestMatchers("/api/auth/**").permitAll()
            .requestMatchers("/api/inventory/**").permitAll()
            .requestMatchers("/api/product/**").permitAll()
            .requestMatchers("/api/img/**").permitAll()
            .requestMatchers("/api/user/**").permitAll()
            .requestMatchers("/api/sales/**").permitAll()
            .requestMatchers("/api/cart/**").permitAll()
            // .hasAuthority("ROLE_DEVELOPER")
            .anyRequest().authenticated()
            )
            //configure sessionmanagement
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            //configure the auth providers and filter
            .authenticationProvider(daoAuthProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            //configure authentrypoint to handle auth provider exceptions
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authExceptionHandler));

        return http.build();
    }

}

