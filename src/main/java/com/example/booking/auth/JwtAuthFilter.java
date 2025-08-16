package com.example.booking.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	public JwtAuthFilter(JwtService jwtService){ this.jwtService = jwtService; }

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		String header = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (header != null && header.startsWith("Bearer ")) {
			try {
				Claims c = jwtService.parse(header.substring(7)).getBody();
				var auth = new UsernamePasswordAuthenticationToken(
						c.getSubject(), null, List.of(new SimpleGrantedAuthority("ROLE_" + c.get("role"))));
				auth.setDetails(c);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (Exception ignored) {}
		}
		chain.doFilter(req, res);
	}
}