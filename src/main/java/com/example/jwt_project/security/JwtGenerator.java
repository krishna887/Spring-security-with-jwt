package com.example.jwt_project.security;

import com.example.jwt_project.dto.LoginDto;
import com.example.jwt_project.entity.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtGenerator {
    private final  String SECRET_KEY="4bb6d1dfbafb64a681139d1586b6f1160d18159afd57c8c79136d7490630407c";

    public long JWT_EXPIRATION = 70000;
    private Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

//    public String generateToken(Authentication authentication) {
//        String username = authentication.getName();
//        Date currentDate = new Date();
//        Date expireDate = new Date(currentDate.getTime() + JWT_EXPIRATION);
//
//        // Extract roles from the authentication object
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//
//        // Build the token with roles included in claims
//        String token = Jwts
//                .builder()
//                .setSubject(username)
//                .claim("roles", roles) // Inclusion of roles in claims
//                .setIssuedAt(new Date())
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512,secretKey)
//                .compact();
//        return token;
//    }
//
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
public String getUsernameFromJWT(String token) {

    return extractClaim(token, Claims::getSubject);
}
    public boolean validateToken(String token, UserDetails user){
        String username= getUsernameFromJWT(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
private Claims extractAllClaims(String token) {
    return Jwts
            .parser()
            .verifyWith(getSigninKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

    public String generateToken(LoginDto loginDto){

        String token = Jwts.builder()
                .subject(loginDto.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*2))//for two minute
                .signWith(getSigninKey())
                .compact();
        return  token;
    }

    public SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
//            return true;
//        } catch (Exception ex) {
//            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",
//                    ex.fillInStackTrace());
//        }
//    }

//    public List<String> getRolesFromJWT(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
//        return claims.get("roles", List.class);
//    }
}