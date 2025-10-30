package br.com.ferraz.blog.config;

import br.com.ferraz.blog.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> auth
            // rotas públicas
            .requestMatchers("/", "/login", "/error", "/css/**", "/js/**").permitAll()
            // rotas que exigem autenticação (escrita em posts e todas categorias)
            .requestMatchers("/posts/**", "/categories/**").authenticated()
            // qualquer outra rota pública por padrão
            .anyRequest().permitAll()
    )
        .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())
        .logout(LogoutConfigurer::permitAll);

    return http.build();
  }

  @Bean
  public AuthenticationManager authManager(HttpSecurity http) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder =
        http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

    return authenticationManagerBuilder.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
