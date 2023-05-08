package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    ResponseEntity<UserModel> createUser(final UserModel userModel, BindingResult result);

    ResponseEntity<List<UserModel>> viewAllUsersAllInfo();

    ResponseEntity<Optional<UserModel>> getOneUser(Long userid);

    ResponseEntity<List<UserModel>> sortAllUsersByFirstname();

    ResponseEntity<List<UserModel>> sortAllUsersByLastname();

    ResponseEntity<UserModel> createUserAnna();

    ResponseEntity<UserModel> createUserBritta();

    ResponseEntity<UserModel> removeUser(Long userid);

    ResponseEntity<UserModel> updateUser(Long userId, final UserModel userModel);

    ResponseEntity<UserModel> updateUserField(Long userId, Map<Object, Object> updates);
}