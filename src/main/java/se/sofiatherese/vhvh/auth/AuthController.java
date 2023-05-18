package se.sofiatherese.vhvh.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) throws Exception {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) throws Exception {
        try {
            return ResponseEntity.ok(authService.authenticate(request));
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @GetMapping("/getAuthenticatedUser")
    public ResponseEntity<UserDetails> getAuthenticatedUser (Authentication authentication) {
        return authService.getAuthenticatedUser(authentication);
    }

    @CrossOrigin
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout () {
        return authService.notAuthenticated();
    }

}
