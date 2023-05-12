package se.sofiatherese.vhvh.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.sofiatherese.vhvh.user.UserRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty
    private String token;
    private Long userId;
    private String username;
    private UserRole userRole;

}

