package com.example.Multi.Vender.Food.Order.Web.Security;

import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.config.ProviderType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
public class AuthUtils {

    @Value("${jwt.secretKey}")
    private String secretKey;

    public SecretKey getSecretKey (){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private SecretKey getRefreshSecretKey() {
        return Keys.hmacShaKeyFor(
                refreshSecretKey.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String genrateAccessToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith((getSecretKey()))
                .compact();
    }

    @Value("${jwt.refreshSecretKey}")
    private String refreshSecretKey;

    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getRefreshSecretKey())
                .compact();
    }

    public Claims parseAccessToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims parseRefreshToken(String token) {
        return Jwts.parser()
                .verifyWith(getRefreshSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public ProviderType getProviderTypeFromRegistrationId(String registrationId){
        return switch (registrationId.toLowerCase()){
            case "google" -> ProviderType.GOOGLE;
            default -> throw new IllegalArgumentException("Unsuported OAuth2 clinet " + registrationId);
        };
    }

    public String determinProviderIdFromOauth2User(OAuth2User oAuth2User, String registrationId){
        String providerId = switch (registrationId.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            default -> throw new IllegalStateException("Unexpected value" + registrationId.toLowerCase());
        };

        if(providerId == null || providerId.isBlank()){
            log.error("Unable to determin providerId for oauth2 login");
            throw new IllegalArgumentException("Unable to determin providerId for oauth2 login");
        }
        return providerId;
    }

    public String determineUsernameFromOauth2user(OAuth2User oAuth2User, String registrationid ,String prividerId){
        String email = oAuth2User.getAttribute("email");

        if(email != null && !email.isBlank()){
            return email;
        }

        return switch (registrationid.toLowerCase()){
            case "google" -> oAuth2User.getAttribute("sub");
            default -> prividerId;
        };
    }


}
