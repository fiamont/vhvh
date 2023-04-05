package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.config.AppPasswordConfig;
import se.sofiatherese.vhvh.user.authorities.UserRoles;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;

    @Autowired
    public UserService(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<UserModel> createUser (@Valid @RequestBody final UserModel userModel, BindingResult result){
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String role = String.valueOf(userModel.getAuthorities().iterator().next());

        switch (role) {
            case "ADMIN" -> userModel.setAuthorities(UserRoles.ADMIN.getGrantedAuthorities());
            case "USER" -> userModel.setAuthorities(UserRoles.USER.getGrantedAuthorities());
        }

        userModel.setPassword(appPasswordConfig.bcryptPasswordEncoder().encode(userModel.getPassword()));
        userModel.setAccountNonExpired(true);
        userModel.setAccountNonLocked(true);
        userModel.setCredentialsNonExpired(true);
        userModel.setEnabled(true);
        userRepository.save(userModel);

        return new ResponseEntity<>(userModel, HttpStatus.CREATED);
    }

    public ResponseEntity<List<UserModel>> viewAllUsersAllInfo() {
        try {
            return ResponseEntity.ok(this.userRepository.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserModel> createUserAnna() {
        try {

            UserModel anna = new UserModel(
                    "anna.al@mail.com",
                    appPasswordConfig.bcryptPasswordEncoder().encode("12345678"),
                    "Anna", "Al", UserRoles.USER.getGrantedAuthorities(), true, true, true, true);

            userRepository.save(anna);
            return new ResponseEntity<>(anna, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<UserModel> createUserBritta() {
        try {

            UserModel britta = new UserModel(
                    "britta.bok@mail.com",
                    appPasswordConfig.bcryptPasswordEncoder().encode("heja1234"),
                    "Britta", "Bok", UserRoles.USER.getGrantedAuthorities(), true, true, true, true);

            userRepository.save(britta);
            return new ResponseEntity<>(britta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
