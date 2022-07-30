package ih.ifbs.repositories;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.BeforeAll;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PassengerRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @BeforeAll
    void setup() {
        var airline = new Airline("Test Airlines", 100, 75);

        User accountRudo = new User("Rudo Villano", "rudovillano@gamail.com", "test", 35, Gender.M, Role.PASSENGER);
        User accountVeronica = new User("Rudo Villano", "corteveronica@gamail.com", "test", 28, Gender.F, Role.PASSENGER);
        User accountAnna = new User("Anna McAmy", "mcamyanna@gamail.com", "test", 26, Gender.F, Role.PASSENGER);
        var passenger1 = new Passenger(accountRudo, false);
        var passenger2 = new Passenger(accountVeronica, false);
        var passenger3 = new Passenger(accountAnna, false);

        var flight = new Flight(airline, "FL123", FlightType.COMM,
                "Honolulu", "Sydney", LocalDate.now(), true);

        passenger1.addFlight(flight);
        passenger2.addFlight(flight);
        passenger3.addFlight(flight);
        flight.addPassenger(passenger1);
        flight.addPassenger(passenger2);
        flight.addPassenger(passenger3);
        airline.addFlight(flight);
        airlineRepository.save(airline);
        flightRepository.save(flight);
        passengerRepository.save(passenger1);
        passengerRepository.save(passenger2);
        passengerRepository.save(passenger3);
    }


    @Test
    @Transactional
    void fetchingFlightPassengerListShouldSucceed() {
        // Arrange
        var flight = flightRepository.findByFlightNumber("FL123");

        // Assert(s)
        assertNotNull(flight);
        assertTrue(flight.isOnTime());
        assertEquals("Honolulu", flight.getDeparture());
        assertEquals("Sydney", flight.getArrival());
        assertEquals("Test Airlines", flight.getAirline().getAirlineName());
        assertEquals(3, flight.getPassengerList().size());

        for (Passenger p : flight.getPassengerList()) {
            assertNotNull(p);
        }

        assertNotNull(flight.getAirline());
    }
}
