package com.example.Multi.Vender.Food.Order.Web.Controller;

import com.example.Multi.Vender.Food.Order.Web.Dtos.*;
import com.example.Multi.Vender.Food.Order.Web.Repository.UsersRepository;
import com.example.Multi.Vender.Food.Order.Web.Security.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/Auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UsersRepository usersRepository;

    @PostMapping("/Login")
    public ResponseEntity<LoginResponceDto> Login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ){
        return authService.Login(loginRequestDto, response);
    }


    @PostMapping("/signUp")
    public ResponseEntity<SigneUpResponceDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        return ResponseEntity.ok(authService.SignUp(signUpRequestDto));
    }
    @GetMapping("/oauth/token")
    public ResponseEntity<LoginResponceDto> getToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        return authService.refreshToken(refreshToken);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponceDto> refresh(
            @CookieValue(name = "refreshToken", required = false) String refreshToken
    ) {
        if(refreshToken == null){
            throw new RuntimeException("Refresh token missing");
        }
        return authService.refreshToken(refreshToken);
    }

}
