package com.example.Multi.Vender.Food.Order.Web.Security;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.config.ProviderType;
import com.example.Multi.Vender.Food.Order.Web.config.UserRole;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UsersRepository usersRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;
    private final PasswordEncoder passwordEncoder;


public ResponseEntity<LoginResponceDto> Login(
        LoginRequestDto loginRequestDto,
        HttpServletResponse response
) {

    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequestDto.getUsername(),
                    loginRequestDto.getPassword()
            )
    );

    User user = (User) authentication.getPrincipal();

    String accessToken = authUtils.genrateAccessToken(user);
    String refreshToken = authUtils.generateRefreshToken(user);

    Cookie cookie = new Cookie("refreshToken", refreshToken);

    cookie.setHttpOnly(true);
    cookie.setSecure(false); // true in production HTTPS
    cookie.setPath("/");
    cookie.setMaxAge(7 * 24 * 60 * 60);

    response.addCookie(cookie);

    return ResponseEntity.ok(
            new LoginResponceDto(user.getId(), accessToken,user.getUserRole())
    );
}

    public User SignUpInternal(SignUpRequestDto signUpRequestDto,String providerId, ProviderType providerType){
        User user = usersRepository.findByUsername(signUpRequestDto.getUsername()).orElse(null);

        if(providerType == ProviderType.EMAIL && signUpRequestDto.getName() == null || signUpRequestDto.getName().isBlank()){
            throw new RuntimeException("name is required");
        }
        if(providerType == ProviderType.EMAIL && signUpRequestDto.getUsername() == null || signUpRequestDto.getUsername().isBlank()){
            throw new RuntimeException("Email is required");
        }

        if(user != null) throw new RuntimeException("User alredy Exist");

        User user1 = User.builder()
                .name(signUpRequestDto.getName())
                .username(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .userRole(UserRole.CUSTOMER)
                .registerdAt(LocalDateTime.now())
                .providerId(providerId)
                .providerType(providerType)
                .build();
        User savedUser = usersRepository.save(user1);
        return savedUser;
    }

    public SigneUpResponceDto SignUp(SignUpRequestDto signUpRequestDto) {
        User savedUser = SignUpInternal(signUpRequestDto,null,ProviderType.EMAIL);

        return new SigneUpResponceDto(savedUser.getId(),savedUser.getUsername());
    }

    public User handleOauth2LoginRequest(
            OAuth2User oAuth2User,
            String registrationId
    ){
        ProviderType providerType = authUtils.getProviderTypeFromRegistrationId(registrationId);

        String providerId = authUtils.determinProviderIdFromOauth2User(oAuth2User,registrationId);

        User user = usersRepository.findByProviderTypeAndProviderId(providerType,providerId).orElse(null);

        String email = oAuth2User.getAttribute("email");

        User EmailUser = usersRepository.findByUsername(email).orElse(null);
        String name = oAuth2User.getAttribute("name");

        if(user == null && EmailUser == null){
            //signup flow
            String username = authUtils.determineUsernameFromOauth2user(oAuth2User,registrationId,providerId);
            user = SignUpInternal(new SignUpRequestDto(name,username,null),providerId,providerType);
        } else if (user != null) {
            if(email != null || !email.isBlank()){
                user.setUsername(email);
                usersRepository.save(user);
            }
        }else{
            throw new BadCredentialsException("this email is alredy register with the provider " + email);
        }
        LoginResponceDto loginResponceDto = new LoginResponceDto(user.getId(), authUtils.genrateAccessToken(user),user.getUserRole());

        return user;
    }


    public ResponseEntity<LoginResponceDto> refreshToken(String refreshToken) {

        if (refreshToken == null) {
            throw new BadCredentialsException("No refresh token");
        }

        Claims claims = authUtils.parseRefreshToken(refreshToken);
        String username = claims.getSubject();

        User user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = authUtils.genrateAccessToken(user);

        return ResponseEntity.ok(
                new LoginResponceDto(user.getId(), newAccessToken,user.getUserRole())
        );
    }

}
