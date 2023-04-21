package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@Controller
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    /*TODO:
        7. Göra så inte all info kommer med så fort man hämtar en användare? DAO/DTO?
            t.ex. bara username, role, firstname och lastname.
        8. Se över put och matchmetoderna i service! "'Optional.get()' without 'isPresent()' check"
           och "Method invocation 'setAccessible' may produce 'NullPointerException'"
     */

    @PostMapping("/registeruser")
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
    public ResponseEntity<UserModel> deleteUser (@PathVariable Long userid) {
        return userService.removeUser(userid);
    }

    @PutMapping("/updateuser/{userid}")
    public ResponseEntity<UserModel> updateUser (@PathVariable Long userid, @RequestBody final UserModel userModel) {
        return userService.updateUser(userid, userModel);
    }

    @PatchMapping("/updateuserfield/{userid}")
    public ResponseEntity<UserModel> updateField (@PathVariable("userid") Long userId, @RequestBody Map<Object, Object> updates){
        return userService.updateUserField(userId, updates);
    }
}
