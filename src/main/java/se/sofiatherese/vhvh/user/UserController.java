package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import se.sofiatherese.vhvh.config.AppPasswordConfig;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, AppPasswordConfig appPasswordConfig, UserService userService) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;
        this.userService = userService;
    }


    /*TODO:
        3. 1 metod för att läsa in 1 specifik användare?
        5. 1 metod för ändra en specifik användare
        6. 1 metod för ta bort en specifik användare
        7. Göra så inte all info kommer med så fort man hämtar en användare? DAO/DTO?
            t.ex. bara username, role, firstname och lastname.
        8.

     */

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
        return userService.createUser(userModel, result);
    }

    @GetMapping("/showusers")
    public ResponseEntity<List<UserModel>> showUsers () {
        return userService.viewAllUsersAllInfo();
    }

    @GetMapping("/showuser/{userid}")
    public ResponseEntity<Optional<UserModel>> showUser (@PathVariable Long userid) {
        return userService.getOneUser(userid);
    }

    @GetMapping("/showusersbyfirstname")
    public ResponseEntity<List<UserModel>> showUsersByFirstname() {
        return userService.sortAllUsersByFirstname();
    }

    @GetMapping("/showusersbylastname")
    public ResponseEntity<List<UserModel>> showUsersByLastname(){
        return userService.sortAllUsersByLastname();
    }

    @GetMapping("/saveuseranna")
    public ResponseEntity<UserModel> registerUserAnna (){

        return userService.createUserAnna();
    }

    @GetMapping("/saveuserbritta")
    public ResponseEntity<UserModel> registerUserBritta (){

        return userService.createUserBritta();
    }

    @DeleteMapping("/deleteuser/{userid}")
    public void deleteUser (@PathVariable Long userid) {
        userService.removeUser(userid);
    }
}
