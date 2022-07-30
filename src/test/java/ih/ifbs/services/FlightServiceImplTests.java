package ih.ifbs.services;

import ih.ifbs.domain.*;
import ih.ifbs.exceptions.FlightNotFoundException;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testing")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightServiceImplTests {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private PassengerRepository passengerRepository;


    @BeforeAll
    public void setup() {
        // Arrange
        var airline = new Airline("Pegasus", 30, 24);
        User account = new User("Rudo Villano", "corteveronica@gamail.com", "test", 28, Gender.F, Role.PASSENGER);
        var passenger = new Passenger(account, false);
        var flight = new Flight(airline, "P456", FlightType.COMM,
                "İstanbul", "Aydın", LocalDate.now(), true);
        airline.addFlight(flight);
        airlineRepository.save(airline);
        flightRepository.save(flight);
        passengerRepository.save(passenger);
    }

    @Test
    @Transactional
    void addFlightShouldWork() {
        // Act
        var airline = airlineRepository.findAirlineByAirlineName("Pegasus");
        var flight =  new Flight(airline, "P123", FlightType.PRIV,
                "Honolulu", "Sydney", LocalDate.now(), true);
        airline.addFlight(flight);
        flightRepository.save(flight);

        // Assert
        assertEquals(flight, flightService.findById(flight.getId()));
    }

    @Test
    void throwFlightNotFoundExceptionIfFlightNumberIsInvalid() {
        String invalidFlightNumber = "XX00";

        assertNull(flightRepository.findByFlightNumber(invalidFlightNumber));
        // Assert
        assertThrows(FlightNotFoundException.class, () -> {
            // Act
            flightService.findByFlightNumber(invalidFlightNumber);
        });
    }

    @Test
    void updateFlightShouldWorkWithValidFlightNumber() {
        // Act
        var flight = flightRepository.findByFlightNumber("P456");
        flight.setOnTime(false);
        flightService.updateFlight(flight);

        // Assert
        assertFalse(flightRepository.save(flight).isOnTime());
    }
}