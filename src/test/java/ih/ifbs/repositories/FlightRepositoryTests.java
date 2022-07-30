package ih.ifbs.repositories;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("testing")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class FlightRepositoryTests {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private PassengerRepository passengerRepository;


    @BeforeEach
    public void setup() {
        var airline = new Airline("Test Airlines", 100, 75);

        User account = new User("Rudo Villano", "rudovillano@gamail.com", "test", 35, Gender.M, Role.PASSENGER);
        var passenger1 = new Passenger(account, false);

        var flight = new Flight(airline, "FL123", FlightType.COMM,
                "Honolulu", "Sydney", LocalDate.now(), true);

        passenger1.addFlight(flight);
        flight.addPassenger(passenger1);
        airline.addFlight(flight);
        airlineRepository.save(airline);
        flightRepository.save(flight);
        passengerRepository.save(passenger1);
    }

    @Test
    @Transactional
    public void deleteFlightShouldDeleteThePassengersToo() {
        // Arrange
        var flight = flightRepository.findByFlightNumber("FL123");
        var passenger = flight.getPassengerList().get(0);

        // Act
        flightRepository.delete(flight);

        // Assert
        assertTrue(flightRepository.findById(flight.getId()).isEmpty());
        assertFalse(passengerRepository.findById(passenger.getId()).isPresent());
    }
}
