package ih.ifbs.presentation.mvc;

import ih.ifbs.domain.Airline;
import ih.ifbs.domain.Flight;
import ih.ifbs.domain.FlightType;
import ih.ifbs.exceptions.FlightNotFoundException;
import ih.ifbs.presentation.mvc.viewmodels.FlightViewModel;
import ih.ifbs.services.AirlineService;
import ih.ifbs.services.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private final Logger logger = LoggerFactory.getLogger(FlightController.class);
    private final FlightService flightService;
    private final AirlineService airlineService;

    @Autowired
    public FlightController(FlightService flightService, AirlineService airlineService) {
        this.flightService = flightService;
        this.airlineService = airlineService;
    }

    @GetMapping
    public String displayFlights(Model model) {
        logger.info("displaying flights...");
        model.addAttribute("flights", flightService.getAllFlights());
        return "flights";
    }

    @GetMapping("/airline")
    public String displayFlights(@Param("an") @RequestParam(value = "an") String airlineName, Model model) {
        logger.info("displaying flights...");
        model.addAttribute("flights", flightService.getAllFlightsOfAnAirline(airlineName));
        return "flights";
    }

    @GetMapping("/add")
    public String showFlightForm(Model model) {
        logger.info("Showing add flight form");
        model.addAttribute("airlines", airlineService.getAllAirlineNames());
        model.addAttribute("types", FlightType.values());
        model.addAttribute("flight", new FlightViewModel());
        model.addAttribute("today", LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return "add-flight";
    }

    @PostMapping("/add")
    public String handleFlight(@Valid @ModelAttribute("flight") FlightViewModel flightViewModel, BindingResult errors, Model model) {
        logger.info("collecting and checking flight fields...");
        model.addAttribute("errors", errors);
        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(error -> logger.error(error.toString()));
            model.addAttribute("today", LocalDateTime.now().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            model.addAttribute("airlines", airlineService.getAllAirlineNames());
            model.addAttribute("types", FlightType.values());
            return "add-flight";
        } else {
            Airline airline = airlineService.findAirlineByAirlineName(flightViewModel.getAirline());
            Flight flight = new Flight(airline, flightViewModel.getFlightNumber(),
                    flightViewModel.getFlightType(), flightViewModel.getDeparture(), flightViewModel.getArrival(),
                    flightViewModel.getScheduledOn(), flightViewModel.isOnTime());
            flightService.addFlight(flight);
            airlineService.findAirlineByAirlineName(airline.getAirlineName()).addFlight(flight);  // adding flight to Airline
            logger.info("new flight '" + flight.getFlightNumber() + "' added to flight list");
            return "redirect:/flights";
        }
    }

    @GetMapping("/details")
    public String showFlightDetails(@Param("fn") @RequestParam(value = "fn") String flightNumber, Model model) {
        Flight f = flightService.findByFlightNumber(flightNumber);
        logger.debug("Showing details of the flight id:" + f.getId());
        model.addAttribute("flight", f);
        return "flight-details";
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ModelAndView flightNotFoundHandler(HttpServletRequest request, Exception e) {
        logger.error("Error: " + e.getMessage() + " - Request URI: " + request.getRequestURI());
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.setViewName("flights-error");
        return mav;
    }
}
