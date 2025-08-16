package com.example.booking.auth;

import com.example.booking.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
	private final Key key; private final long exp;
	public JwtService(@Value("${app.jwt.secret}") String secret,
					  @Value("${app.jwt.expirationSeconds}") long exp) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.exp = exp;
	}
	public String generate(User u){
		Instant now = Instant.now();
		return Jwts.builder()
				.setSubject(u.getId().toString())
				.addClaims(Map.of("role", u.getRole().name(), "email", u.getEmail(), "name", u.getName()))
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plusSeconds(exp)))
				.signWith(key, SignatureAlgorithm.HS256).compact();
	}
	public Jws<Claims> parse(String token){
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}
}