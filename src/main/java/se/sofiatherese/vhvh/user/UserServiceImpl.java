package se.sofiatherese.vhvh.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.config.AppConfig;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppConfig appConfig;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            userModel.setPassword(appConfig.bcryptPasswordEncoder().encode(userModel.getPassword()));
            if (userRepository.existsByUsername(userModel.getUsername())) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            userRepository.save(userModel);
            return new ResponseEntity<>(userModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserModel>> viewAllUsersAllInfo() {
        try {
            return ResponseEntity.ok(this.userRepository.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Optional<UserModel>> getOneUser(String username) {
        try {
            return ResponseEntity.ok(this.userRepository.findByUsername(username));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserModel>> sortAllUsersByFirstname() {
        try {
            return ResponseEntity.ok(this.userRepository.orderByFirstname());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserModel>> sortAllUsersByLastname() {
        try {
            return ResponseEntity.ok(this.userRepository.orderByLastname());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<UserModel>> sortAllUsersByRole() {
        try {
            return ResponseEntity.ok(this.userRepository.orderByRole());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> createUserAnna() {
        try {
            UserModel anna = UserModel.builder()
                    .username("anna.alm@mail.com")
                    .password(appConfig.bcryptPasswordEncoder().encode("12345678"))
                    .firstname("Anna")
                    .lastname("Alm")
                    .role(UserRole.USER)
                    .build();

            userRepository.save(anna);
            return new ResponseEntity<>(anna, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> createUserBritta() {
        try {
            UserModel britta = UserModel.builder()
                    .username("britta.bok@mail.com")
                    .password(appConfig.bcryptPasswordEncoder().encode("heja1234"))
                    .firstname("Britta")
                    .lastname("Bok")
                    .role(UserRole.USER)
                    .build();

            userRepository.save(britta);
            return new ResponseEntity<>(britta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> removeUser(String username) {
        try {
            userRepository.deleteByUsername(username);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> updateUser(String username, final UserModel userModel) {
        Optional<UserModel> usedUser = userRepository.findByUsername(username);
        if (usedUser.isPresent()) {
            UserModel updatedUser = usedUser.get();
            updatedUser.setUsername(userModel.getUsername());
            updatedUser.setPassword(appConfig.bcryptPasswordEncoder().encode(userModel.getPassword()));
            updatedUser.setFirstname(userModel.getFirstname());
            updatedUser.setLastname(userModel.getLastname());
            updatedUser.setRole(userModel.getRole());
            userRepository.save(updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}