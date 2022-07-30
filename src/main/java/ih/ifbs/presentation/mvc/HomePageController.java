package ih.ifbs.presentation.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomePageController {
    private final Logger logger = LoggerFactory.getLogger(HomePageController.class);

    @GetMapping({"", "/", "/home"})
    public String displayIndexPage() {
        logger.info("Index page is on display.");
        return "index";
    }
}
