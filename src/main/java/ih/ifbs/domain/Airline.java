package ih.ifbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
public class Airline extends EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GraphQLQuery(name = "id", description = "An airline's ID.")
    private int id;

    @Column(name = "airline", nullable = false, length = 50)
    @GraphQLQuery(name = "airlineName", description = "An airline's name.")
    private final String airlineName;

    @Column(name = "fleet_size", nullable = false)
    @GraphQLQuery(name = "fleetSize", description = "An airline's total number of aircraft.")
    private int fleetSize;

    @Column(name = "destinations", nullable = false)
    @GraphQLQuery(name = "totalDestinations", description = "Total number of an airline flies to.")
    private final int totalDestinations;

    @OneToMany(mappedBy = "airline", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @GraphQLQuery(name = "flightList", description = "List of flights which an airline has.")
    private final List<Flight> flightList;

    @OneToOne
    @JoinColumn(name = "airline_user_id", referencedColumnName = "id")
    @GraphQLQuery(name = "account", description = "An airline's account.")
    private User account;

    public Airline(String airlineName, int fleetSize, int totalDestinations) {
        this.airlineName = airlineName;
        this.fleetSize = fleetSize;
        this.totalDestinations = totalDestinations;
        this.flightList = new ArrayList<>();
    }

    protected Airline() {
        this.airlineName = null;
        this.fleetSize = 0;
        this.totalDestinations = 0;
        this.flightList = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void addFlight(Flight flight) {
        flightList.add(flight);
    }

    public String getAirlineName() {
        return airlineName;
    }

    public int getFleetSize() {
        return fleetSize;
    }

    public void setFleetSize(int fleetSize) {
        this.fleetSize = fleetSize;
    }

    public int getTotalDestinations() {
        return totalDestinations;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    @Override
    public String toString() {
        String fixPrint = String.format("# %s - (Fleet Size: %d, Flights to: %d destinations, Current Flights: %d)",
                airlineName.toUpperCase(), fleetSize, totalDestinations, flightList.size());
//        StringBuilder airlinePrint = new StringBuilder(fixPrint);
//        flightList.forEach(airlinePrint::append);
        return fixPrint;
        /* little part of the toString() is commented out
            to prevent stack overflow issues (cannot be solved by using 'transient' keyword) */
    }
}
