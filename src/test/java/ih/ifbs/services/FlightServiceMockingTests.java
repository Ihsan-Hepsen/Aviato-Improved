package ih.ifbs.services;

import ih.ifbs.domain.Airline;
import ih.ifbs.domain.Flight;
import ih.ifbs.domain.FlightType;
import ih.ifbs.exceptions.FlightNotFoundException;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("testing")
public class FlightServiceMockingTests {

    @Autowired
    private FlightService flightService;

    @MockBean
    private FlightRepository flightRepository;


    @Test
    public void findByFlightNumberMustThrowFlightNotFoundExceptionIfFlightDoesNotExist() {
        // Arrange
        given(flightRepository.findByFlightNumber("FL123"))
                .willThrow(FlightNotFoundException.class);

        // Assert
        assertThrows(FlightNotFoundException.class, () -> {
            // Act
            flightService.findByFlightNumber("FL123");
        });
    }

    @Test
    public void findByFlightNumberShouldReturnThatFlight() {
        // Arrange
        Airline airline = new Airline("Air Tropic", 45, 19);
        Flight flight = new Flight(
                airline, "FL123",
                FlightType.PRIV, "Havana", "Charleston",
                LocalDate.of(2022, 4, 25), true);
        given(flightRepository.findByFlightNumber("FL123")).willReturn(flight);

        // Act
        Flight returnedFlight = flightService.findByFlightNumber("FL123");

        // Assert
        assertEquals(flight.getId(), returnedFlight.getId());
        assertEquals(flight.getAirline().getAirlineName(), returnedFlight.getAirline().getAirlineName());
        assertEquals(flight.getFlightNumber(), returnedFlight.getFlightNumber());
        assertEquals(flight.getFlightType(), returnedFlight.getFlightType());
        assertEquals(flight.getFlightSchedule(), returnedFlight.getFlightSchedule());
        assertEquals(flight.getDeparture(), returnedFlight.getDeparture());
        assertEquals(flight.getArrival(), returnedFlight.getArrival());
    }
}
