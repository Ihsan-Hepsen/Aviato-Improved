package ih.ifbs.resolvers;

import ih.ifbs.domain.Airline;
import ih.ifbs.domain.User;
import ih.ifbs.exceptions.UserNotFoundException;
import ih.ifbs.services.AirlineService;
import ih.ifbs.services.UserService;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@GraphQLApi
public class AirlineResolver {

    private final AirlineService airlineService;
    private final UserService userService;

    @Autowired
    public AirlineResolver(AirlineService airlineService, UserService userService) {
        this.airlineService = airlineService;
        this.userService = userService;
    }

    
    @GraphQLQuery(name = "getAirline", description = "Retrieve an airline with given ID.")
    public Airline findById(int id) {
        return airlineService.findById(id);
    }


    @GraphQLMutation(name = "addAirline", description = "Adds a new airline")
    public Airline addAirline(@GraphQLArgument(name = "username") String username,
                              @GraphQLArgument(name = "name") String name,
                              @GraphQLArgument(name = "fleetSize") int fleetSize,
                              @GraphQLArgument(name = "totalDestinations") int totalDestinations) {
        User account = userService.findUser(username);
        if (account == null) throw new UserNotFoundException("User account is null", username);
        Airline airline = new Airline(name, fleetSize, totalDestinations);
        airline.setAccount(account);
        return airlineService.addAirline(airline);
    }

    
    @GraphQLQuery(name = "getAirlines", description = "Retrieve all airlines found in the system.")
    public List<Airline> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    
    @GraphQLQuery(name = "getAirline", description = "Retrieve an airline with given airline name.")
    public Airline findAirlineByAirlineName(String airline) {
        return airlineService.findAirlineByAirlineName(airline);
    }


    @GraphQLMutation(name = "updateAirline", description = "Updates the airline.")
    public void updateAirline(@GraphQLArgument(name = "id") int airlineId, @GraphQLArgument(name = "fleetSize") int fleetSize) {
        Airline airline = airlineService.findById(airlineId);
        airline.setFleetSize(fleetSize);
        airlineService.updateAirline(airline);
    }

    
    @GraphQLMutation(name = "deleteAirline", description = "Deletes the airline.")
    public void deleteAirlineById(@GraphQLArgument(name = "id") int id) {
        airlineService.deleteAirlineById(id);
    }
}
