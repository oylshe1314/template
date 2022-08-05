package com.sk.op.application.user.component;

import com.sk.op.application.common.dto.ResponseStatus;
import com.sk.op.application.common.exception.StandardRespondException;
import com.sk.op.application.user.config.TokenProperties;
import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
public class TokenUtils {

    private final Key key;
    private final long expiration;

    public TokenUtils(TokenProperties tokenProperties) {
        this.key = new SecretKeySpec(tokenProperties.getKey().getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        this.expiration = tokenProperties.getExpiration();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            return token;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("token")) {
                    token = cookie.getValue();
                    if (StringUtils.hasText(token)) {
                        return token;
                    }
                }
            }
        }

        token = request.getParameter("token");
        if (StringUtils.hasText(token)) {
            return token;
        }

        return null;
    }

    public String generateToken(Long uid, String username) {
        JwtBuilder jwtBuilder = Jwts.builder();

        Map<String, Object> body = new HashMap<>();

        body.put("userId", uid);
        body.put("username", username);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + this.expiration * 1000L);

        jwtBuilder.setSubject("token");
        jwtBuilder.setAudience("user");
        jwtBuilder.setIssuedAt(now);
        jwtBuilder.setExpiration(expirationDate);
        jwtBuilder.addClaims(body);

        return jwtBuilder.signWith(this.key).compact();
    }

    public Claims validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {
        return Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJwt(token).getBody();
    }

    private Claims getClaims(String token, boolean ignoreExpired) {
        Claims claims;
        try {
            claims = validateToken(token);
        } catch (ExpiredJwtException e) {
            if (ignoreExpired) {
                claims = e.getClaims();
            } else {
                log.error("Expired token!", e);
                throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_EXPIRED));
            }
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            log.error("invalid token!", e);
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_INVALID));
        }

        if (!ignoreExpired && claims.getExpiration().before(new Date())) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_EXPIRED));
        }
        return claims;
    }

    public String getUsername(String token) throws AuthenticationException {
        return getUsername(token, false);
    }

    public String getUsername(String token, boolean ignoreExpired) throws AuthenticationException {
        Claims claims = getClaims(token, ignoreExpired);

        Object username = claims.get("username");
        if (!(username instanceof String)) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_INVALID));
        }

        return ((String) username);
    }

    public Long getUserId(String token) throws AuthenticationException {
        return getUserId(token, false);
    }

    public Long getUserId(String token, boolean ignoreExpired) throws AuthenticationException {
        Claims claims = getClaims(token, ignoreExpired);

        Object userId = claims.get("userId");
        if (!(userId instanceof Number)) {
            throw new BadCredentialsException("", new StandardRespondException(ResponseStatus.TOKEN_INVALID));
        }

        return ((Number) userId).longValue();
    }
}
