package ih.ifbs.presentation.mvc;

import ih.ifbs.domain.Airline;
import ih.ifbs.services.AirlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/airlines")
public class AirlineController {

    private final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping("/airline-details")
    public String showAirlineDetail(@RequestParam(name = "an") String airlineName, Model model) {
        Airline airline = airlineService.findAirlineByAirlineName(airlineName);
        logger.debug("Showing '" + airline.getAirlineName() + "' details");
        model.addAttribute("airline", airline);
        return "airline-details";
    }
}
