package ih.ifbs.presentation.mvc;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("testing")
@AutoConfigureMockMvc
class PassengerControllerTests {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void passengersDetailsShouldBeForbiddenForUnauthenticatedUsers() throws Exception {
        // Arrange
        var airline = new Airline("Angel Wings", 45, 27);

        User account = new User("Mabel King", "mabelking@icloud.com", "test", 24, Gender.F, Role.PASSENGER);
        var passenger = new Passenger(account, false);

        var flight = new Flight(airline, "AW123", FlightType.PRIV,
                "McKinney", "Charleston", LocalDate.now(), true);

        passenger.addFlight(flight);
        flight.addPassenger(passenger);
        airline.addFlight(flight);
        airlineRepository.save(airline);
        flightRepository.save(flight);
        passengerRepository.save(passenger);

        // Act & Assert
        mockMvc.perform(
                        get("/passengers/details")
                                .queryParam("pId", String.valueOf(account.getId()))
                )
                .andExpect(status().isForbidden());
    }
}
