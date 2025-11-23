package kr.kro.photoliner.global.auth;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import kr.kro.photoliner.domain.user.model.User;
import kr.kro.photoliner.global.code.ApiResponseCode;
import kr.kro.photoliner.global.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

@Component
public class JwtProvider {

    private static final String BEARER_TYPE = "Bearer ";
    private static final int BEARER_TYPE_LEN = 7;

    private final String secretKey;
    private final Long accessTokenExpirationTime;

    public JwtProvider(
            @Value("${jwt.secret-key}") String secretKey,
            @Value("${jwt.access-token.expiration-time}") Long accessTokenExpirationTime
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpirationTime = accessTokenExpirationTime;
    }

    public String extractAccessToken(WebRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(BEARER_TYPE_LEN);
        }
        return null;
    }

    public Long getUserId(String token) {
        try {
            String userId = Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .get("id")
                    .toString();
            return Long.parseLong(userId);
        } catch (JwtException e) {
            throw CustomException.of(ApiResponseCode.TOKEN_PARSE_ERROR);
        }
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException | WeakKeyException e) {
            throw CustomException.of(ApiResponseCode.INVALID_JWT_TOKEN, e.getMessage(), e);
        } catch (ExpiredJwtException e) {
            throw CustomException.of(ApiResponseCode.EXPIRED_JWT_TOKEN, e.getMessage(), e);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            throw CustomException.of(ApiResponseCode.TOKEN_PARSE_ERROR, e.getMessage(), e);
        } catch (Exception e) {
            throw e;
        }
    }

    public String createAccessToken(User user) {
        if (user == null) {
            throw CustomException.of(ApiResponseCode.NOT_FOUND_USER, "user: " + null);
        }
        SecretKey key = getSecretKey();
        return Jwts.builder()
                .signWith(key)
                .header()
                .add("typ", "JWT")
                .add("alg", key.getAlgorithm())
                .and()
                .claim("id", user.getId())
                .expiration(Date.from(Instant.now().plusMillis(accessTokenExpirationTime)))
                .compact();
    }

    private SecretKey getSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }
}
