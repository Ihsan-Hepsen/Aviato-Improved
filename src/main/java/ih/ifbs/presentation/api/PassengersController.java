//package ih.ifbs.presentation.api;
//
//import ih.ifbs.domain.Flight;
//import ih.ifbs.domain.Passenger;
//import ih.ifbs.presentation.api.dto.FlightBookingDTO;
//import ih.ifbs.presentation.api.dto.FlightDTO;
//import ih.ifbs.presentation.api.dto.PassengerDTO;
//import ih.ifbs.security.AirlineOnly;
//import ih.ifbs.security.PassengerOnly;
//import ih.ifbs.services.FlightService;
//import ih.ifbs.services.PassengerService;
//import ih.ifbs.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api")
//public class PassengersController {
//
//    private final PassengerService passengerService;
//    private final FlightService flightService;
//    private final UserService userService;
//
//    @Autowired
//    public PassengersController(PassengerService passengerService, FlightService flightService, UserService userService) {
//        this.passengerService = passengerService;
//        this.flightService = flightService;
//        this.userService = userService;
//    }
//
//    @GetMapping("/passenger")
//    public ResponseEntity<Passenger> getPassengerById(@RequestParam("pId") int id) {
//        ResponseEntity<Passenger> result;
//        var passenger = passengerService.findById(id);
//        if (passenger == null) {
//            result = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            result = new ResponseEntity(passenger, HttpStatus.OK);
//        }
//        return result;
//    }
//
//    @GetMapping("/passengers")
//    public ResponseEntity<List<PassengerDTO>> getAllPassengers() {
//        var passengers = passengerService.getAllPassengers();
//        if (passengers.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        var passengerDTOs = passengers
//                .stream()
//                .map(passenger -> {
//                    var passengerDto = new PassengerDTO();
//                    passengerDto.setId(passenger.getId());
//                    passengerDto.setTransitPassenger(passenger.isTransitPassenger());
//                    passengerDto.setFlights(passenger.getFlights().stream()
//                            .map(flight -> {
//                                var flightDto = new FlightDTO();
//                                flightDto.setId(flight.getId());
//                                flightDto.setAirline(flight.getAirline().getAirlineName());
//                                flightDto.setFlightNumber(flight.getFlightNumber());
//                                flightDto.setDeparture(flightDto.getDeparture());
//                                flightDto.setArrival(flightDto.getArrival());
//                                flightDto.setFlightType(flight.getFlightType());
//                                flightDto.setScheduledOn(flight.getFlightSchedule());
//                                flightDto.setOnTime(flight.isOnTime());
//                                return flightDto;
//                            })
//                            .collect(Collectors.toList()));
//                    return passengerDto;
//                })
//                .toList();
//        return new ResponseEntity<>(passengerDTOs, HttpStatus.OK);
//    }
//
//    @GetMapping("/passengers/{name}")
//    public ResponseEntity<List<PassengerDTO>> getPassengerByName(@PathVariable("name") String name) {
//        var passenger = passengerService.findAllByName(name);
//        if (passenger.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        var passengerDTO = passenger
//                .stream()
//                .map(p -> {
//                    var passengerDto = new PassengerDTO();
//                    passengerDto.setId(p.getId());
//                    passengerDto.setTransitPassenger(p.isTransitPassenger());
//                    passengerDto.setFlights(p.getFlights().stream()
//                            .map(flight -> {
//                                var flightDto = new FlightDTO();
//                                flightDto.setId(flight.getId());
//                                flightDto.setAirline(flight.getAirline().getAirlineName());
//                                flightDto.setFlightNumber(flight.getFlightNumber());
//                                flightDto.setDeparture(flightDto.getDeparture());
//                                flightDto.setArrival(flightDto.getArrival());
//                                flightDto.setFlightType(flight.getFlightType());
//                                flightDto.setScheduledOn(flight.getFlightSchedule());
//                                flightDto.setOnTime(flight.isOnTime());
//                                return flightDto;
//                            })
//                            .collect(Collectors.toList()));
//                    return passengerDto;
//                }).toList();
//        return ResponseEntity.ok(passengerDTO);
//    }
//
//    @AirlineOnly
//    @DeleteMapping("/passengers/{pId}")
//    public ResponseEntity<Void> removePassengerById(@PathVariable("pId") int id) {
//        var passenger = passengerService.findById(id);
//        if (passenger == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        passengerService.deletePassengerById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PassengerOnly // Airline (ROLE_ADMIN) cannot edit passenger properties
//    @PutMapping("/passengers/{pId}")
//    public ResponseEntity<Void> updatePassenger(@PathVariable("pId") int id, @RequestBody PassengerDTO passengerDto) {
//        if (id != passengerDto.getId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        var passenger = passengerService.findById(id);
//        if (passenger == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        passenger.getAccount().setAge(passengerDto.getAge());
//        passengerService.updatePassenger(passenger);
//        userService.updateUser(passenger.getAccount());
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PassengerOnly
//    @PutMapping("/passengers/{pId}/book-flight/{fId}")
//    public ResponseEntity<Void> bookFlight(@PathVariable("pId") int passengerId, @PathVariable("fId") int flightId,
//                                           @RequestBody FlightBookingDTO flightBookingDTO) {
//        Passenger passenger = passengerService.findById(passengerId);
//        Flight flight = flightService.findById(flightId);
//        if (passenger == null || flight == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        if (passengerId != flightBookingDTO.getPassengerId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        if (passenger.getFlights().contains(flight)) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        passenger.addFlight(flight);
//        passenger.setPassengerStatus(passenger.getFlights().size() > 1);
//        flight.addPassenger(passenger);
//        passengerService.updatePassenger(passenger);
//        flightService.updateFlight(flight);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping("/passengers")
//    public ResponseEntity<Void> addPassenger(@Valid @RequestBody PassengerDTO passengerDto, BindingResult errors) {
//        if (errors.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        var passenger = passengerService.getByName(passengerDto.getName());
//        if (passenger != null || passengerDto.getName().equals("")) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        // TODO: fix this
////        var newPassenger = new Passenger(passengerDto.getName(), passengerDto.getAge(),
////                passengerDto.getGender(), passengerDto.isTransitPassenger());
////        passengerService.addPassenger(newPassenger);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//}
