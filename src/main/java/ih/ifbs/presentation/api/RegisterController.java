//package ih.ifbs.presentation.api;
//
//import ih.ifbs.domain.Role;
//import ih.ifbs.domain.User;
//import ih.ifbs.presentation.api.dto.RegistrationDTO;
//import ih.ifbs.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class RegisterController {
//
//    private final UserService userService;
//
//    @Autowired
//    public RegisterController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<Void> registerPassenger(@RequestBody RegistrationDTO registrationDTO) {
//        var user = userService.findUser(registrationDTO.getUsername());
//        if (user != null) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        String encodedPassword = new BCryptPasswordEncoder().encode(registrationDTO.getPassword());
//        var newUser = new User(registrationDTO.getEmail(),
//                registrationDTO.getUsername(),
//                encodedPassword,
//                Role.PASSENGER);
//        userService.addUser(newUser);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @PostMapping("/register-airline")
//    public ResponseEntity<Void> registerAirline(@RequestBody RegistrationDTO registrationDTO) {
//        var user = userService.findUser(registrationDTO.getUsername());
//        if (user != null) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        String encodedPassword = new BCryptPasswordEncoder().encode(registrationDTO.getPassword());
//        var newUser = new User(registrationDTO.getEmail(),
//                registrationDTO.getUsername(),
//                encodedPassword,
//                Role.AIRLINE);
//        userService.addUser(newUser);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//}
