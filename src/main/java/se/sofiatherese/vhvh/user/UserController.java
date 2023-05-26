package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @PostMapping("/registeruser")
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
        return userService.createUser(userModel, result);
    }

    @CrossOrigin
    @GetMapping("/showusers")
    public ResponseEntity<List<UserModel>> showUsers() {
        return userService.viewAllUsersAllInfo();
    }

    @CrossOrigin
    @GetMapping("/showuser/{username}")
    public ResponseEntity<Optional<UserModel>> showUser(@PathVariable String username) {
        return userService.getOneUser(username);
    }

    @CrossOrigin
    @GetMapping("/showusersbyfirstname")
    public ResponseEntity<List<UserModel>> showUsersByFirstname() {
        return userService.sortAllUsersByFirstname();
    }

    @CrossOrigin
    @GetMapping("/showusersbylastname")
    public ResponseEntity<List<UserModel>> showUsersByLastname() {
        return userService.sortAllUsersByLastname();
    }

    @CrossOrigin
    @GetMapping("/showusersbyrole")
    public ResponseEntity<List<UserModel>> showUsersByRole() {
        return userService.sortAllUsersByRole();
    }

    @CrossOrigin
    @GetMapping("/saveuseranna")
    public ResponseEntity<UserModel> registerUserAnna() {
        return userService.createUserAnna();
    }

    @CrossOrigin
    @GetMapping("/saveuserbritta")
    public ResponseEntity<UserModel> registerUserBritta() {
        return userService.createUserBritta();
    }

    @CrossOrigin
    @DeleteMapping("/deleteuser/{username}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable String username) {
        return userService.removeUser(username);
    }

    @CrossOrigin
    @PutMapping("/updateuser/{username}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String username, @RequestBody final UserModel userModel) {
        return userService.updateUser(username, userModel);
    }

}
