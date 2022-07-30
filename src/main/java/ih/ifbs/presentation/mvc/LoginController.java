package ih.ifbs.presentation.mvc;

import ih.ifbs.presentation.mvc.viewmodels.LoginViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public ModelAndView getLoginPage(@Valid @ModelAttribute("loginInfo") ModelAndView mav) {
        mav.setViewName("login");
        mav.addObject("loginInfo", new LoginViewModel());
        return mav;
    }
}
