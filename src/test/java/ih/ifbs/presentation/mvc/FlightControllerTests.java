package ih.ifbs.presentation.mvc;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightControllerTests {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private MockMvc mockMvc;


    @BeforeAll
    void setUp() {
        var airline = new Airline("Test Airlines", 100, 75);

        User account = new User("Rudo Villano", "rudovillano@gamail.com", "test", 35, Gender.M, Role.PASSENGER);
        var passenger = new Passenger(account, false);

        var flight = new Flight(airline, "FL123", FlightType.COMM,
                "Honolulu", "Sydney", LocalDate.now(), true);

        passenger.addFlight(flight);
        flight.addPassenger(passenger);
        airline.addFlight(flight);
        airlineRepository.save(airline);
        flightRepository.save(flight);
        passengerRepository.save(passenger);
    }

    @Test
    void displayFlightShouldReturnAllPresentFlights() throws Exception {
        // Arrange
        var flights = flightRepository.findAll();

        // Act
        var mvcResult = mockMvc.perform(
                        get("/flights")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("flights"))
                .andReturn();
        var actualFlights = (List<Flight>) mvcResult.getModelAndView().getModel().get("flights");

        // Assert
        assertEquals(flights.size(), actualFlights.size());
    }

    @Test
    void showFlightDetailsShouldHaveCorrectInformation() throws Exception {
        // Arrange
        var flight = flightRepository.findByFlightNumber("FL123");

        // Act & Assert
        mockMvc.perform(
                        get("/flights/details")
                                .queryParam("fn", flight.getFlightNumber())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("flight-details"))
                .andExpect(model().attribute("flight",
                        hasProperty("arrival", equalTo("Sydney"))));
    }
}