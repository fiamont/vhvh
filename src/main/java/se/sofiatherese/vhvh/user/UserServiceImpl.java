package se.sofiatherese.vhvh.user;

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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppConfig appConfig;

    public UserServiceImpl(UserRepository userRepository, AppConfig appConfig) {
        this.userRepository = userRepository;
        this.appConfig = appConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            userModel.setPassword(appConfig.bcryptPasswordEncoder().encode(userModel.getPassword()));
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
    public ResponseEntity<Optional<UserModel>> getOneUser(Long userid) {
        try {
            return ResponseEntity.ok(this.userRepository.findById(userid));
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
    public ResponseEntity<UserModel> removeUser(Long userid) {
        try {
            userRepository.deleteById(userid);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> updateUser(Long userId, final UserModel userModel) {
        Optional<UserModel> usedUser = userRepository.findById(userId);
        if (usedUser.isPresent()) {
            UserModel updatedUser = usedUser.get();
            updatedUser.setUsername(userModel.getUsername());
            updatedUser.setPassword(userModel.getPassword());
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