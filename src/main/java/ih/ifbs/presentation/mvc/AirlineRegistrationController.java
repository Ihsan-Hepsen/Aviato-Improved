package ih.ifbs.presentation.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register-airline")
public class AirlineRegistrationController {

    @GetMapping
    public String displayRegistrationPage() {
        return "airline-register";
    }
}
