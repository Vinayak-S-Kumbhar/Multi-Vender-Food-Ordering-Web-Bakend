package com.example.Multi.Vender.Food.Order.Web.Security;

import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2Successhandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final AuthUtils authUtils;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2AuthenticationToken token =
                (OAuth2AuthenticationToken) authentication;

        OAuth2User oAuth2User =
                (OAuth2User) authentication.getPrincipal();

        String registrationId =
                token.getAuthorizedClientRegistrationId();

        User user =
                authService.handleOauth2LoginRequest(
                        oAuth2User,
                        registrationId
                );

        String accessToken =
                authUtils.genrateAccessToken(user);

        String refreshToken =
                authUtils.generateRefreshToken(user);

        Cookie cookie =
                new Cookie("refreshToken", refreshToken);

        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);
        response.sendRedirect(
                "https://multi-vender-food-ordering-web-fron.vercel.app/oauth-success" +
                        "?token=" + accessToken +
                        "&userId=" + user.getId() +
                        "&role=" + user.getUserRole()
        );
    }
}