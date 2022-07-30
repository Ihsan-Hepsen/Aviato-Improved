package ih.ifbs.resolvers;

import ih.ifbs.domain.Flight;
import ih.ifbs.domain.Passenger;
import ih.ifbs.domain.User;
import ih.ifbs.exceptions.UserNotFoundException;
import ih.ifbs.repository.sqlbasedrepository.UserRepository;
import ih.ifbs.services.FlightService;
import ih.ifbs.services.PassengerService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@GraphQLApi
public class PassengerResolver {

    private final PassengerService passengerService;
    private final UserRepository userRepository;

    private final FlightService flightService;

    @Autowired
    public PassengerResolver(PassengerService passengerService, UserRepository userRepository, FlightService flightService) {
        this.passengerService = passengerService;
        this.userRepository = userRepository;
        this.flightService = flightService;
    }


    @GraphQLQuery(name = "getPassenger", description = "Retrieve a passenger with the given ID.")
    public Passenger findById(int id) {
        return passengerService.findById(id);
    }


    @GraphQLMutation(name = "addPassenger", description = "Add a new Passenger")
    public Passenger addPassenger(@GraphQLArgument(name = "username") String username,
                                  @GraphQLArgument(name = "transit") boolean isTransit) {
        User account = userRepository.findByUsername(username).orElse(null);
        if (account == null) throw new UserNotFoundException("User is null", username);
        Passenger passenger = new Passenger(account, isTransit);
        passengerService.addPassenger(passenger);
        userRepository.save(account);
        return passenger;
    }


    @GraphQLQuery(name = "getPassengers", description = "Retrieve all the passengers found in the system.")
    public List<Passenger> getAllPassengers() {
        return passengerService.getAllPassengers();
    }


    @GraphQLQuery(name = "getPassenger", description = "Retrieve a passenger with given name.")
    public Passenger getByName(String name) {
        return passengerService.getByName(name);
    }


    @GraphQLMutation(name = "updatePassenger", description = "Updates a passenger.")
    public void updatePassenger(@GraphQLArgument(name = "id") int passengerId, @GraphQLArgument(name = "age") int age) {
        Passenger passenger = passengerService.findById(passengerId);
        passenger.getAccount().setAge(age);
        passengerService.updatePassenger(passenger);
        userRepository.save(passenger.getAccount());
    }


    @GraphQLMutation(name = "deletePassenger", description = "Deletes a passenger.")
    public void deletePassengerById(@GraphQLArgument(name = "id") int id) {
        passengerService.deletePassengerById(id);
    }


    @GraphQLMutation(name = "bookFlight", description = "Book flight with given ID for the passenger with given ID.")
    public void bookFlight(@GraphQLArgument(name = "fId") int flightId, @GraphQLArgument(name = "pId") int passengerId) {
        Passenger passenger = passengerService.findById(passengerId);
        Flight flight = flightService.findById(flightId);

        if (passenger.getFlights().contains(flight)) return;
        passenger.addFlight(flight);
        flight.addPassenger(passenger);
        flightService.updateFlight(flight);
        passengerService.updatePassenger(passenger);
    }
}
