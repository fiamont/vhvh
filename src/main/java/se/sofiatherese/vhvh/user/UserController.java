package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/registeruser")
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
        return userService.createUser(userModel, result);
    }

    @CrossOrigin
    @GetMapping("/showusers")
    public ResponseEntity<List<UserModel>> showUsers () {
        return userService.viewAllUsersAllInfo();
    }

    @CrossOrigin
    @GetMapping("/showuser/{userid}")
    public ResponseEntity<Optional<UserModel>> showUser (@PathVariable Long userid) {
        return userService.getOneUser(userid);
    }

    @CrossOrigin
    @GetMapping("/showusersbyfirstname")
    public ResponseEntity<List<UserModel>> showUsersByFirstname() {
        return userService.sortAllUsersByFirstname();
    }

    @CrossOrigin
    @GetMapping("/showusersbylastname")
    public ResponseEntity<List<UserModel>> showUsersByLastname(){
        return userService.sortAllUsersByLastname();
    }

    @CrossOrigin
    @GetMapping("/saveuseranna")
    public ResponseEntity<UserModel> registerUserAnna (){

        return userService.createUserAnna();
    }

    @CrossOrigin
    @GetMapping("/saveuserbritta")
    public ResponseEntity<UserModel> registerUserBritta (){

        return userService.createUserBritta();
    }

    @CrossOrigin
    @DeleteMapping("/deleteuser/{userid}")
    public ResponseEntity<UserModel> deleteUser (@PathVariable Long userid) {
        return userService.removeUser(userid);
    }

    @CrossOrigin
    @PutMapping("/updateuser/{userid}")
    public ResponseEntity<UserModel> updateUser (@PathVariable Long userid, @RequestBody final UserModel userModel) {
        return userService.updateUser(userid, userModel);
    }
}
