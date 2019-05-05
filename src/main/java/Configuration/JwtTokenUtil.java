package Configuration;

import Authetication.UserPrivilege;
import Models.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class JwtTokenUtil implements Serializable {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "mrtsven";

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user.getEmail(),user.getPrivileges());
    }

    private String doGenerateToken(String subject, List<UserPrivilege> privileges) {

        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("scopes", Arrays.asList(privileges));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("127.0.0.1")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String email) {
        final String check = getUsernameFromToken(token);
        return (
                check.equals(email)
                        && !isTokenExpired(token));
    }

    public boolean validateAdmin(String token) {
        final Claims claims = getAllClaimsFromToken(token);

        ArrayList privileges = claims.get("scopes", ArrayList.class);
        // We have fetch the first array as it is nested for some reason.
        List checkList = (List) privileges.get(0);


        if(checkList.contains("ADMIN") && !isTokenExpired(token)){
            return true;
        } else {
            return false;
        }

    }
}