package se.sofiatherese.vhvh.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.config.AppPasswordConfig;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;

    public UserServiceImpl(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    @Override
    public ResponseEntity<UserModel> createUser(UserModel userModel, BindingResult result) {
        try{
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            userModel.setPassword(appPasswordConfig.bcryptPasswordEncoder().encode(userModel.getPassword()));
            userModel.setAccountNonExpired(true);
            userModel.setAccountNonLocked(true);
            userModel.setCredentialsNonExpired(true);
            userModel.setEnabled(true);
            userRepository.save(userModel);

            return new ResponseEntity<>(userModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }    }

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

            UserModel anna = new UserModel(
                    "anna.al@mail.com",
                    appPasswordConfig.bcryptPasswordEncoder().encode("12345678"),
                    "Anna", "Al",UserRole.USER, true, true, true, true);

            userRepository.save(anna);
            return new ResponseEntity<>(anna, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> createUserBritta() {
        try {

            UserModel britta = new UserModel(
                    "britta.bok@mail.com",
                    appPasswordConfig.bcryptPasswordEncoder().encode("heja1234"),
                    "Britta", "Bok", UserRole.USER, true, true, true, true);

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
    public ResponseEntity<UserModel> updateUser(Long userId, final UserModel userModel){
        try{
            Optional<UserModel> usedUser = userRepository.findById(userId);
            UserModel updatedUser = usedUser.get();

            updatedUser.setUsername(userModel.getUsername());
            updatedUser.setPassword(userModel.getPassword());
            updatedUser.setFirstname(userModel.getFirstname());
            updatedUser.setLastname(userModel.getLastname());
            updatedUser.setRole(userModel.getRole());
            updatedUser.setAccountNonExpired(userModel.isAccountNonExpired());
            updatedUser.setAccountNonLocked(userModel.isAccountNonLocked());
            updatedUser.setCredentialsNonExpired(userModel.isCredentialsNonExpired());
            updatedUser.setEnabled(userModel.isEnabled());
            userRepository.save(updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<UserModel> updateUserField(Long userId, Map<Object, Object> updates) {
        try {
            UserModel userModel = userRepository.findById(userId).get();
            updates.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(UserModel.class, (String) key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, userModel, value);
            });
            userRepository.save(userModel);

            return new ResponseEntity<>(userModel, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}