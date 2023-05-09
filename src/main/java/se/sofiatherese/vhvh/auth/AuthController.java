package se.sofiatherese.vhvh.auth;

import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import se.sofiatherese.vhvh.config.jwt.JwtService;
import se.sofiatherese.vhvh.user.UserModel;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) throws Exception {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, @AuthenticationPrincipal JwtService jwt) throws Exception {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
