package zhedron.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zhedron.shop.dto.Token;
import zhedron.shop.exceptions.UserNotExistException;
import zhedron.shop.models.RefreshToken;
import zhedron.shop.models.User;
import zhedron.shop.services.JwtService;
import zhedron.shop.services.RefreshTokenService;
import zhedron.shop.services.UserService;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody User user) throws UserNotExistException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            User foundUser = userService.findByEmail(user.getEmail());

            String accessToken = jwtService.generateToken(foundUser.getEmail());
            RefreshToken refreshToken = refreshTokenService.generateRefreshToken(foundUser.getEmail());

            Token token = new Token (accessToken, refreshToken.getToken());

            return ResponseEntity.ok().body(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<Token> refreshToken (@RequestBody RefreshToken refreshToken) {
        return refreshTokenService.findByToken(refreshToken.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String access = jwtService.generateToken(user.getEmail());

                    Token token = new Token(access, refreshToken.getToken());

                    return ResponseEntity.ok().body(token);
                }).orElseThrow(() -> new RuntimeException(refreshToken.getToken() + " refresh token not found."));
    }
}
