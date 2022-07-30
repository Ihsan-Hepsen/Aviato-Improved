package ih.ifbs.presentation.mvc;

import ih.ifbs.domain.Passenger;
import ih.ifbs.presentation.mvc.viewmodels.PassengerViewModel;
import ih.ifbs.services.FlightService;
import ih.ifbs.services.PassengerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final Logger logger = LoggerFactory.getLogger(PassengerController.class);
    private final PassengerService passengerService;
    private final FlightService flightService;

    @Autowired
    public PassengerController(PassengerService passengerService, FlightService flightService) {
        this.passengerService = passengerService;
        this.flightService = flightService;
    }

//    @GetMapping
//    public String displayPassengers(Model model) {
//        logger.info("Displaying passenger list");
//        model.addAttribute("passengers", passengerService.getAllPassengers());
//        return "passengers";
//    }

    // Genders disappear after one invalid attempt, therefore I use Converter to fix the issue.
    @GetMapping("/add")
    public String showPassengerForm(Model model) {
        logger.info("Showing add passenger form");
        model.addAttribute("passenger", new PassengerViewModel());
        return "add-passengers";
    }

    @GetMapping("/details")
    public ModelAndView showPassengerDetails(@RequestParam(value = "pn") String name) {
        Passenger passenger = passengerService.getByName(name);
        logger.debug("Showing details of " + passenger);
        final ModelAndView mav = new ModelAndView();
        mav.setViewName("passenger-details");
        mav.getModel().put("passenger", passenger);
        mav.getModel().put("flights", flightService.getAllFlights());
        return mav;
    }

    @RequestMapping(value = "/filtered{t}", method = RequestMethod.GET)
    public String showFilteredPassengers(@PathVariable("t") @RequestParam(value = "t") String condition, Model model) {
        boolean isTransit = Boolean.parseBoolean(condition);
        logger.debug("Showing all " + isTransit + " passengers");
        model.addAttribute("passengers", passengerService.findAllByTransitPassenger(isTransit));
        return "passengers";
    }

    @PostMapping("/add")
    public String handlePassenger(@Valid @ModelAttribute("passenger") PassengerViewModel pDTO, BindingResult errors, Model model) {
        logger.info("collecting data from passenger form fields...");
        model.addAttribute("errors", errors);
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            return "add-passengers";
        } else {
            String fullName = pDTO.getName() + " " + pDTO.getLastName();
//            Passenger passenger = new Passenger(fullName, pDTO.getAge(),
//                    pDTO.getGender(), pDTO.isTransitPassenger());
//            passengerService.addPassenger(passenger);
//            logger.info("new passenger '" + passenger.getName() + "' added");
            return "redirect:/passengers";
        }
    }
}
