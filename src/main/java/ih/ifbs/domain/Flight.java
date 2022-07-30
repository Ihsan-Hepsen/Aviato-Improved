package ih.ifbs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.leangen.graphql.annotations.GraphQLQuery;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "FLIGHTS")
public class Flight extends EntityClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GraphQLQuery(name = "id", description = "A flight's ID.")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH},
            fetch=FetchType.LAZY)
    @JoinColumn(name="airline_flight")
    @GraphQLQuery(name = "airline", description = "Airline (owner) of the flight.")
    private final Airline airline;

    @Column(name = "flight_number", nullable = false, length = 25, unique = true)
    @GraphQLQuery(name = "flightNumber", description = "A flight's semi-unique identifier.")
    private final String flightNumber;

    @Column(name = "flight_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @GraphQLQuery(name = "flightType", description = "A flight's acknowledged flight type.")
    private final FlightType flightType;

    @Column(name = "departure", nullable = false, length = 50)
    @GraphQLQuery(name = "departure", description = "A flight's city of departure.")
    private final String departure;

    @Column(name = "arrival", nullable = false, length = 50)
    @GraphQLQuery(name = "arrival", description = "A flight's city of arrival.")
    private final String arrival;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date", nullable = false)
    @GraphQLQuery(name = "scheduledOn", description = "A flight's departure date (yyyy-mm-dd).")
    private LocalDate scheduledOn;

    @Column(name = "status", nullable = false)
    @GraphQLQuery(name = "onTime", description = "A flight's flight status: on time or delayed.")
    private boolean onTime;

    @ManyToMany(targetEntity = Passenger.class, mappedBy = "flights", cascade = {CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JsonIgnore
    @GraphQLQuery(name = "passengerList", description = "List of passengers that flight has.")
    private final List<Passenger> passengerList;


    public Flight(Airline airline, String flightNumber, FlightType flightType, String departure,
                  String arrival, LocalDate scheduledOn, boolean isOnTime) {
        this.airline = airline;
        this.flightNumber = flightNumber;
        this.flightType = flightType;
        this.departure = departure;
        this.arrival = arrival;
        this.scheduledOn = scheduledOn;
        this.onTime = isOnTime;
        this.passengerList = new ArrayList<>();
    }

    protected Flight() {
        this.airline = null;
        this.flightNumber = null;
        this.flightType = null;
        this.departure = null;
        this.arrival = null;
        this.scheduledOn = null;
        this.onTime = false;
        this.passengerList = new ArrayList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public void addPassenger(Passenger passenger) {
        passengerList.add(passenger);
    }

    public Airline getAirline() {
        return airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public String getDeparture() {
        return departure;
    }

    public String getArrival() {
        return arrival;
    }

    public LocalDate getFlightSchedule() {
        return scheduledOn;
    }

    public void setScheduledOn(LocalDate scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return id == flight.id && flightNumber.equals(flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, flightNumber);
    }

    @Override
    public String toString() {
        return String.format("-%-18s, Flight: %-6s - %s to %s - Time: %d-%s-%d (%s)\n",
                airline, flightNumber, departure.toUpperCase(), arrival.toUpperCase(),
                scheduledOn.getYear(), scheduledOn.getMonth().toString().subSequence(0, 3), scheduledOn.getDayOfMonth(),
                onTime ? "OnTime" : "Delayed");
    }
}
