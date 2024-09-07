package zhedron.shop.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateToken (String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] secretKey = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(secretKey);
    }

    private Claims getAllClaims (String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaims (String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername (String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public boolean isValidToken (String token, UserDetails user) {
        String username = extractUsername (token);

        return username.equals(user.getUsername()) && !isExpiredToken(token);
    }

    private boolean isExpiredToken(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration (String token) {
        return extractClaims(token, Claims::getExpiration);
    }
}
