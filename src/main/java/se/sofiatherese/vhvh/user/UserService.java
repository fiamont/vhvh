package se.sofiatherese.vhvh.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    ResponseEntity<UserModel> createUser(final UserModel userModel, BindingResult result);

    ResponseEntity<List<UserModel>> viewAllUsersAllInfo();

    ResponseEntity<Optional<UserModel>> getOneUser(String username);

    ResponseEntity<List<UserModel>> sortAllUsersByFirstname();

    ResponseEntity<List<UserModel>> sortAllUsersByLastname();

    ResponseEntity<List<UserModel>> sortAllUsersByRole();

    ResponseEntity<UserModel> createUserAnna();

    ResponseEntity<UserModel> createUserBritta();

    ResponseEntity<UserModel> removeUser(String username);

    ResponseEntity<UserModel> updateUser(String username, final UserModel userModel);
}