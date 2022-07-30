package ih.ifbs.resolvers;

import ih.ifbs.domain.Airline;
import ih.ifbs.domain.Flight;
import ih.ifbs.domain.FlightType;
import ih.ifbs.domain.Passenger;
import ih.ifbs.exceptions.AirlineNotFoundException;
import ih.ifbs.exceptions.FlightNotFoundException;
import ih.ifbs.services.AirlineService;
import ih.ifbs.services.FlightService;
import ih.ifbs.services.PassengerService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Component
@GraphQLApi
public class FlightResolver {

    private final FlightService flightService;
    private final AirlineService airlineService;

    private final PassengerService passengerService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FlightResolver(FlightService flightService, AirlineService airlineService, PassengerService passengerService) {
        this.flightService = flightService;
        this.airlineService = airlineService;
        this.passengerService = passengerService;
    }


    @GraphQLMutation(name = "addFlight", description = "Adds a new flight.")
    public Flight addFlight(
            @GraphQLArgument(name = "airline") String airlineName,
            @GraphQLArgument(name = "departure") String departure,
            @GraphQLArgument(name = "arrival") String arrival,
            @GraphQLArgument(name = "flightNumber") String flightNumber,
            @GraphQLArgument(name = "flightType") FlightType flightType,
            @GraphQLArgument(name = "onTime") boolean onTime,
            @GraphQLArgument(name = "scheduledOn") LocalDate scheduledOn) {
        Airline airline = airlineService.findAirlineByAirlineName(airlineName);
        if (airline == null) throw new AirlineNotFoundException("Airline not found", airlineName);

        Flight flight = new Flight(airline, flightNumber, flightType, departure, arrival, scheduledOn, onTime);
        flightService.addFlight(flight);
        airline.addFlight(flight);
        airlineService.addAirline(airline);
        return flight;
    }


    @GraphQLQuery(name = "getFlights", description = "Retrieve all flights found in the system.")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }


    @GraphQLQuery(name = "getFlightById", description = "Retrieve a flight with the given ID.")
    public Flight findById(@GraphQLArgument(name = "id") int id) {
        return flightService.findById(id);
    }


    @GraphQLQuery(name = "getFlightByFlightNumber", description = "Retrieve a flight with provided flight number.")
    public Flight findByFlightNumber(@GraphQLArgument(name = "flightNumber") String flightNumber) {
        flightNumber = flightNumber.toUpperCase(Locale.ROOT);
        Flight flight = flightService.findByFlightNumber(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber, "Flight '" + flightNumber + "' not found!");
        }
        return flight;
    }


    @GraphQLMutation(name = "deleteFlight", description = "Deletes a flight with given ID.")
    public void deleteFlightById(@GraphQLArgument(name = "id") int id) {
        flightService.deleteFlightById(id);
    }


    @GraphQLMutation(name = "updateFlight", description = "Updates a flight.")
    public void updateFlight(@GraphQLArgument(name = "id") int flightId,
                             @GraphQLArgument(name = "scheduledOn") LocalDate date,
                             @GraphQLArgument(name = "onTime") boolean onTime) {
        Flight flight = flightService.findById(flightId);
        flight.setScheduledOn(date);
        flight.setOnTime(onTime);
        flightService.updateFlight(flight);
    }

    @GraphQLMutation(name = "removePassengerFromFlight", description = "Removes given passenger from the given flight.")
    public void removePassengerFromFlight(@GraphQLArgument(name = "fId") int flightId, @GraphQLArgument(name = "pId") int passengerId) {
        log.debug("Deleting passenger: " + passengerId + " from flight: " + flightId);
        Flight flight = flightService.findById(flightId);
        Passenger passenger = passengerService.findById(passengerId);
        flight.getPassengerList().remove(passenger);
        passenger.getFlights().remove(flight);
        passengerService.updatePassenger(passenger);
        flightService.updateFlight(flight);
    }

}
