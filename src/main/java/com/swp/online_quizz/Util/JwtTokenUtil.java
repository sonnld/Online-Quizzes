package com.swp.online_quizz.Util;

import com.swp.online_quizz.Model.TokenPayLoad;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    private final String secret = "VIET_DZ";
    public String generateToken(TokenPayLoad tokenPayLoad, long expiredDate){
        Map<String, Object> claims = new HashMap<>();
        claims.put("payload",tokenPayLoad);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredDate*1000))
                .signWith(SignatureAlgorithm.HS256,secret).compact();
    }

    public TokenPayLoad getTokenPayLoad(String token) {
        return getClaimsFromToken(token,(Claims claim)->{
            Map<String, Object> result = (Map<String, Object>) claim.get("payload");
            return TokenPayLoad.builder()
                    .userId((int) result.get("accountId"))
                    .email((String) result.get("email"))
                    .userName((String) result.get("username"))
                    .role((String) result.get("role"))
                    .build();
        });
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public boolean isValid(String token, TokenPayLoad tokenPayLoadFromAccount){
        if(!isExpired(token)){
            return false;
        }
        TokenPayLoad tokenPayLoad = getTokenPayLoad(token);
        return tokenPayLoad.getUserName().equals(tokenPayLoadFromAccount.getUserName())
                && tokenPayLoad.getUserId() == tokenPayLoadFromAccount.getUserId()
                && tokenPayLoad.getEmail().equals(tokenPayLoadFromAccount.getEmail());
    }

    public boolean isExpired(String token){
        Date expiredTime = getClaimsFromToken(token, Claims::getExpiration);
        return expiredTime.before(new Date());
    }
}

