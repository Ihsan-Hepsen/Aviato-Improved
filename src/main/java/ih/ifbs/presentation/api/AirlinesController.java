//package ih.ifbs.presentation.api;
//
//import ih.ifbs.domain.Airline;
//import ih.ifbs.domain.Flight;
//import ih.ifbs.presentation.api.dto.AirlineDTO;
//import ih.ifbs.presentation.api.dto.FlightDTO;
//import ih.ifbs.services.AirlineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class AirlinesController {
//
//    private final AirlineService airlineService;
//
//    @Autowired
//    public AirlinesController(AirlineService airlineService) {
//        this.airlineService = airlineService;
//    }
//
//    @GetMapping("/airline")
//    public ResponseEntity<AirlineDTO> getAirlineById(@RequestParam("aId") int id) {
//        var airline = airlineService.findById(id);
//        if (airline == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        var airlineDto = new AirlineDTO();
//        airlineDto.setId(airline.getId());
//        airlineDto.setAirlineName(airline.getAirlineName());
//        airlineDto.setFleetSize(airline.getFleetSize());
//        airlineDto.setTotalDestinations(airline.getTotalDestinations());
//        airlineDto.setFlights(airline.getFlightList().stream()
//                .map(flight -> {
//                    var flightDto = new FlightDTO();
//                    flightDto.setId(flight.getId());
//                    flightDto.setAirline(flight.getAirline().getAirlineName());
//                    flightDto.setFlightNumber(flight.getFlightNumber());
//                    flightDto.setDeparture(flight.getDeparture());
//                    flightDto.setArrival(flight.getArrival());
//                    flightDto.setFlightType(flight.getFlightType());
//                    flightDto.setScheduledOn(flight.getFlightSchedule());
//                    flightDto.setOnTime(flight.isOnTime());
//                    return flightDto;
//                })
//                .toList());
//        return ResponseEntity.ok(airlineDto);
//    }
//
//    @GetMapping("/airlines")
//    public ResponseEntity<List<AirlineDTO>> getAirlines() {
//        var airlines = airlineService.getAllAirlines();
//        if (airlines.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        var airlineDTOs = airlines.stream()
//                .map(airline -> {
//                    var airlineDto = new AirlineDTO();
//                    airlineDto.setId(airline.getId());
//                    airlineDto.setAirlineName(airline.getAirlineName());
//                    airlineDto.setFleetSize(airline.getFleetSize());
//                    airlineDto.setTotalDestinations(airline.getTotalDestinations());
//                    airlineDto.setFlights(airline.getFlightList().stream()
//                            .map(flight -> {
//                                var flightDto = new FlightDTO();
//                                flightDto.setId(flight.getId());
//                                flightDto.setAirline(flight.getAirline().getAirlineName());
//                                flightDto.setFlightNumber(flight.getFlightNumber());
//                                flightDto.setDeparture(flight.getDeparture());
//                                flightDto.setArrival(flight.getArrival());
//                                flightDto.setFlightType(flight.getFlightType());
//                                flightDto.setScheduledOn(flight.getFlightSchedule());
//                                flightDto.setOnTime(flight.isOnTime());
//                                return flightDto;
//                            })
//                            .toList());
//                    return airlineDto;
//                })
//                .toList();
//        return ResponseEntity.ok(airlineDTOs);
//    }
//
//    @DeleteMapping("/airlines/{aId}")
//    public ResponseEntity<Airline> deleteAirlineById(@PathVariable("aId") int id) {
//        var airline = airlineService.findById(id);
//        if (airline == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        airlineService.deleteAirlineById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/airlines/{aId}")
//    public ResponseEntity<Void> updateAirline(@PathVariable("aId") int id, @RequestBody AirlineDTO airlineDto) {
//        if (id != airlineDto.getId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        var airline = airlineService.findById(id);
//        if (airline == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        airline.setFleetSize(airlineDto.getFleetSize());
//        airlineService.updateAirline(airline);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping("/airlines")
//    public ResponseEntity<Void> addAirline(@RequestBody @Valid AirlineDTO airlineDto, BindingResult errors) {
//        if (errors.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        var airline = airlineService.findAirlineByAirlineName(airlineDto.getAirlineName());
//
//        if (airline != null) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // no duplicate Airlines
//        }
//        var newAirline = new Airline(airlineDto.getAirlineName(),
//                airlineDto.getFleetSize(), airlineDto.getTotalDestinations());
//
//        // if the flightDto object have any associated flights it will be added in the below section
//        if (airlineDto.getFlights().size() > 0) {
//            List<Flight> flights = airlineDto.getFlights().stream()
//                    .map(flightDto -> {
//                        var airlineObj = airlineService.findAirlineByAirlineName(flightDto.getAirline());
//                        var flight = new Flight(airlineObj, flightDto.getFlightNumber(), flightDto.getFlightType(),
//                                flightDto.getDeparture(), flightDto.getArrival(),
//                                flightDto.getScheduledOn(), flightDto.isOnTime());
//                        flight.setId(flightDto.getId());
//                        return flight;
//                    }).collect(Collectors.toList());
//            flights.forEach(newAirline::addFlight);
//        }
//
//        newAirline.setId(airlineDto.getId());
//        airlineService.addAirline(newAirline);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//}
