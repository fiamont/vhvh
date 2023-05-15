package se.sofiatherese.vhvh.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import se.sofiatherese.vhvh.config.jwt.JwtService;
import se.sofiatherese.vhvh.user.UserModel;
import se.sofiatherese.vhvh.user.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthResponse authenticate(AuthRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new Exception("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder().token(jwtToken).userRole(user.getRole()).build();
    }

    public ResponseEntity<UserDetails> getAuthenticatedUser(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails);
    }

    public ResponseEntity<Map<String, Object>> notAuthenticated () {
        SecurityContextHolder.clearContext();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User är satt till 'anonymous'");
        return ResponseEntity.ok(response);
    }
}
