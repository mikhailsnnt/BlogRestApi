package com.sainnt.blogrestapi.security;

import com.sainnt.blogrestapi.exception.BlogApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliseconds;

    public String generateToken(Authentication authentication){
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwt(String  token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (ExpiredJwtException exception){
            throw  new BlogApiException("Expired JWT token");
        }
        catch (UnsupportedJwtException exception){
            throw  new BlogApiException("Unsupported JWT token");
        }
        catch (MalformedJwtException exception){
            throw  new BlogApiException("Invalid JWT token");
        }
        catch (SignatureException exception){
            throw  new BlogApiException("Invalid JWT signature");
        }
        catch (IllegalArgumentException exception){
            throw  new BlogApiException("Invalid JWT token argument");
        }
    }
}
