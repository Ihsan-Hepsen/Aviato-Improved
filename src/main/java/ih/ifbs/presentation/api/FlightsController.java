//package ih.ifbs.presentation.api;
//
//import ih.ifbs.domain.Flight;
//import ih.ifbs.domain.Passenger;
//import ih.ifbs.exceptions.FlightNotFoundException;
//import ih.ifbs.presentation.api.dto.FlightDTO;
//import ih.ifbs.presentation.api.dto.PassengerDTO;
//import ih.ifbs.security.AirlineOnly;
//import ih.ifbs.services.AirlineService;
//import ih.ifbs.services.FlightService;
//import ih.ifbs.services.PassengerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.query.Param;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import javax.validation.Valid;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class FlightsController {
//
//    private final FlightService flightService;
//    private final AirlineService airlineService;
//    private final PassengerService passengerService;
//
//    @Autowired
//    public FlightsController(FlightService flightService, AirlineService airlineService, PassengerService passengerService) {
//        this.flightService = flightService;
//        this.airlineService = airlineService;
//        this.passengerService = passengerService;
//    }
//
//    // TODO: FIX REQUIRED. Should be: /flights/{id}
//    @GetMapping("/flight")
//    public ResponseEntity<FlightDTO> getFlightById(@RequestParam("fId") int id) {
//        var flight = flightService.findById(id);
//        if (flight == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        var flightDto = new FlightDTO(flight.getAirline().getAirlineName(), flight.getFlightNumber(),
//                flight.getFlightType(), flight.getDeparture(), flight.getArrival(),
//                flight.getFlightSchedule(), flight.isOnTime());
//        flightDto.setId(flight.getId());
//        return ResponseEntity.ok(flightDto);
//    }
//
//    @GetMapping("/flights/{flightNumber}")
//    public ResponseEntity<FlightDTO> getFlightsByFlightNumber(@PathVariable("flightNumber") String flightNumber) {
//        var flight = flightService.findByFlightNumber(flightNumber);
//        if (flight == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        var flightDto = new FlightDTO(flight.getAirline().getAirlineName(), flight.getFlightNumber(),
//                flight.getFlightType(), flight.getDeparture(), flight.getArrival(),
//                flight.getFlightSchedule(), flight.isOnTime());
//        flightDto.setId(flight.getId());
//        return ResponseEntity.ok(flightDto);
//    }
//
//    @GetMapping("/flights")
//    public ResponseEntity<List<FlightDTO>> getFlights() {
//        var flights = flightService.getAllFlights();
//        if (flights.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        var flightDTOs = flights.stream()
//                .map(flight -> {
//                    var flightDto = new FlightDTO();
//                    flightDto.setId(flight.getId());
//                    flightDto.setAirline(flight.getAirline().getAirlineName());
//                    flightDto.setFlightNumber(flight.getFlightNumber());
//                    flightDto.setFlightType(flight.getFlightType());
//                    flightDto.setDeparture(flight.getDeparture());
//                    flightDto.setArrival(flight.getArrival());
//                    flightDto.setScheduledOn(flight.getFlightSchedule());
//                    flightDto.setOnTime(flight.isOnTime());
//                    return flightDto;
//                })
//                .toList();
//        return ResponseEntity.ok(flightDTOs);
//    }
//
//    @GetMapping(value = "/details")
//    public ResponseEntity<FlightDTO> flightSearch(@Param("fn") @RequestParam(value = "fn") String flightNumber) {
//        var flight = flightService.findByFlightNumber(flightNumber);
//        if (flight == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @DeleteMapping("/flights/{fId}")
//    public ResponseEntity<Void> deleteFlight(@PathVariable("fId") int id) {
//        var flight = flightService.findById(id);
//        if (flight == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        flight.getPassengerList().clear();
//        flightService.updateFlight(flight);
//        flightService.deleteFlightById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PutMapping("/flights/{fId}")
//    public ResponseEntity<Void> updateFlight(@PathVariable("fId") int id, @RequestBody FlightDTO flightDto) {
//        if (id != flightDto.getId()) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        var flight = flightService.findById(id);
//        if (flight == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        flight.setOnTime(flightDto.isOnTime());
//        flight.setScheduledOn(flightDto.getScheduledOn());
//        flightService.updateFlight(flight);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @AirlineOnly
//    @PutMapping("/flights/{fId}/{pId}")
//    public ResponseEntity<Void> removePassengerFromFlight(@PathVariable("pId") int passengerId, @PathVariable("fId") int flightId, @RequestBody FlightDTO flightDTO) {
//        if (flightDTO.getId() != flightId) {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//        Flight flight = flightService.findById(flightId);
//        Passenger passenger = passengerService.findById(passengerId);
//        if (flight == null || passenger == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        flight.getPassengerList().remove(passenger);
//        flightService.updateFlight(flight);
//        passenger.getFlights().remove(flight);
//        passenger.setPassengerStatus(passenger.getFlights().size() > 1);
//        passengerService.updatePassenger(passenger);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @AirlineOnly
//    @PostMapping("/flights")
//    public ResponseEntity<FlightDTO> addFlight(@RequestBody @Valid FlightDTO flightDto, BindingResult errors) {
//        if (errors.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        var airline = airlineService.findAirlineByAirlineName(flightDto.getAirline());
//
//        if (airline == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        var flight = new Flight(airline, flightDto.getFlightNumber(), flightDto.getFlightType(),
//                flightDto.getDeparture(), flightDto.getArrival(), flightDto.getScheduledOn(), flightDto.isOnTime());
//        flight.setId(flight.getId());
//        airline.addFlight(flight);
//        flightService.addFlight(flight);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//}
