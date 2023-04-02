package se.sofiatherese.vhvh.user;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.config.AppPasswordConfig;
import se.sofiatherese.vhvh.user.authorities.UserRoles;

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
        1. 1 metod för att lägga till en användare
        2. 1 metod för att läsa in alla användare
        3. 1 metod för att läsa in 1 specifik användare?
        4. 1 metod för läsa in användare i specifik ordning (bokstavsordning t.ex.?)
        5. 1 metod för ändra en specifik användare
        6. 1 metod för ta bort en specifik användare

     */

    @GetMapping("/saveuseranna")
    public UserModel createUserAnna {

        UserModel anna = new UserModel(
                "anna.ek@mail.com",
                appPasswordConfig.bcryptPasswordEncoder().encode("12345678"),
                "Anna", "Ek", UserRoles.USER.getGrantedAuthorities(), true, true, true, true);

        return userRepository.save(anna);
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@Valid @RequestBody UserModel userModel, BindingResult result) {
        return userService.createUser(userModel, result);
    }
}
