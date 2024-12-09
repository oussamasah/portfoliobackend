package dev.portfolio.oussama.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
        private static final String SECRET_KEY = "0b677507c4c62222eec3bbed73fb70c02ec8fd969d3c715c464be5a115c8bba4708a9249739fde8e3feef6ab07c3b41cf07ec51abb046d6516f9699dc8e39ec05119bd8419cb2d0166744066ed0efa17cff4addca3c232246a97837913d044aa2fcf6ed6fd5b3c15f220b551b8ebff4cb7b6590416376a3c1f5f41de9a53daf960c61205f16c74d60f1a014aa5eef95d111ca08d38986fcf8bb5a06e5a8fda9dac7ac459aa73cb33982607cdf8fa34bd2ac3af5f2a5754ce9f4d26ab8ff3f6be51ea9b7c2e607141c00f23c105f1497b88407ccd7c13ea54844d6b266bc6dbfa11b1291ff00b40a4aceb6aa8ca8a9cbd338ab1d276fc6910fde8d72f384a7a96";

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 10 hours
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            System.out.println("--------------------------token is validated");

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("--------------------------token not validated");
            return false;
        }
    }
}