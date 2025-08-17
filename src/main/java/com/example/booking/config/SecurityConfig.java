package com.example.booking.config;

import com.example.booking.auth.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	private final JwtAuthFilter jwtFilter;
	public SecurityConfig(JwtAuthFilter jwtFilter) { this.jwtFilter = jwtFilter; }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(reg -> reg
						// Swagger/OpenAPI (docs + UI)
						.requestMatchers(
								"/v3/api-docs/**",
								"/swagger-ui.html",
								"/swagger-ui/**"
						).permitAll()

						// Actuator + Auth
						.requestMatchers("/actuator/**", "/api/auth/**").permitAll()

						// Everything else requires auth
						.anyRequest().authenticated()
				)
				.exceptionHandling(ex -> ex
						.authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
						.accessDeniedHandler((req, res, e) -> res.sendError(HttpServletResponse.SC_FORBIDDEN))
				)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}