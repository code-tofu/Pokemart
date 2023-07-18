package tfip.b3.mp.pokemart.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.ms}")
    private int expireTimeMs;

    //vaildate that there is a subject (username)
    //validte that username and token is correct

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    //TOKEN PARSERS
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(jwtToken).getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token) {
        return (Date) extractClaim(token, Claims::getExpiration);
      }


    //TOKEN VALIDATORS
    public boolean checkJwtExpiry(String jwtToken){
        return extractExpiration(jwtToken).before(new Date());
    }

    public boolean checkJwtToken(String jwtStr) {
        try {
          Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parse(jwtStr);
          return true;
        } catch (MalformedJwtException malformedErr) {
            System.out.println(">> [ERROR] JWT Error:" + malformedErr);
        } catch (ExpiredJwtException expiredErr) {
            System.out.println(">> [ERROR]JWT Error:" + expiredErr);
        } catch (UnsupportedJwtException unsupportedErr) {
            System.out.println(">> [ERROR]JWT Error:" + unsupportedErr);
        } catch (IllegalArgumentException illegalArgumentErr) {
            System.out.println(">> [ERROR] JWT Error:" + illegalArgumentErr);
        } catch (SignatureException signatureErr){
            System.out.println(">> [ERROR] JWT Error:" + signatureErr);
        }
        return false;
      }
    public Boolean validateToken(String jwtStr, UserDetails userDetails) {
    final String username = extractUsername(jwtStr);
    return (username.equals(userDetails.getUsername()) && !checkJwtExpiry(jwtStr));
    }


    //TOKEN GENERATORS
    public String generateToken(String subject) {
        System.out.println(">> [INFO] TOKENGEN: Generate Token  Subject:" + subject);
        return generateToken(subject,new HashMap<>());
    }
    public String generateToken(String subject, Map<String, Object> customClaims){
        System.out.println(">> [INFO] TOKENGEN: Generate Custom Token Subject:" + subject + "|Custom Claims:" + customClaims);
        return buildToken(subject, customClaims);
    }
    public String buildToken(String subject,Map<String, Object> customClaims) {
        System.out.println(">> [INFO] TOKENGEN: Building Token");
        return Jwts
                .builder()
                .setClaims(customClaims) //NOTE: This will override existing claims. is this necessary?
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
