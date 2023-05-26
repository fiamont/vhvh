package se.sofiatherese.vhvh.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface AuthService {

    AuthResponse authenticate(AuthRequest request) throws Exception;

    ResponseEntity<UserDetails> getAuthenticatedUser(Authentication authentication);

    ResponseEntity<Map<String, Object>> notAuthenticated();

}
