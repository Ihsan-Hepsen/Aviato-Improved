package ih.ifbs.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
public class Passenger extends EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GraphQLQuery(name = "id", description = "A passenger's ID.")
    private int id;

    @SerializedName("transit")
    @Column(name = "passenger_type", nullable = false)
    @GraphQLQuery(name = "transitPassenger", description = "A passenger's transit status: transit or non-transit.")
    private boolean transitPassenger;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinTable(name = "passenger_flight",
            joinColumns = @JoinColumn(name = "flight_id"),
            inverseJoinColumns = @JoinColumn(name = "passenger_id"))
    @GraphQLQuery(name = "flights", description = "List of flights that passenger has.")
    private final List<Flight> flights;

    @OneToOne(cascade = {CascadeType.ALL, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "passenger_user_id", referencedColumnName = "id")
    @GraphQLQuery(name = "account", description = "Passenger's user account.")
    private User account;

    public Passenger(User account, boolean transitPassenger) {
        this.account = account;
        this.flights = new ArrayList<>();
        this.transitPassenger = transitPassenger;
    }

    protected Passenger() {
        this.transitPassenger = false;
        this.flights = new ArrayList<>();
        this.account = null;
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
        flights.add(flight);
    }

    public void setPassengerStatus(boolean transit) {
        this.transitPassenger = transit;
    }

    public boolean isTransitPassenger() {
        return transitPassenger;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public int getNumberOfFlights() {
        return flights.size();
    }

    public User getAccount() {
        return account;
    }

    public void setAccount(User account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nAge: %d\nGender: %s\nTransit: %s\nFlights: %d\n",
                account.getUsername(), account.getAge(), account.getGender().toString(), transitPassenger ? "Yes" : "No", flights.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Passenger passenger = (Passenger) o;
        return id == passenger.id && Objects.equals(account, passenger.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, account);
    }
}
