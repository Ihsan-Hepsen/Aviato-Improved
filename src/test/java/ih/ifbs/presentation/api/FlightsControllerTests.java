package ih.ifbs.presentation.api;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("testing")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FlightsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @BeforeAll
    void setup() {
        var goldenWings = new Airline("Golden Wings", 31, 45);
        var qantas = new Airline("Qantas Airlines", 134, 77);

        var flight1 = new Flight(goldenWings, "GW10", FlightType.PRIV,
                "Orlando", "Charleston", LocalDate.now(), true);
        var flight2 = new Flight(qantas, "QF12", FlightType.COMM,
                "Los Angeles", "Sydney", LocalDate.now(), false);
        var flight3 = new Flight(goldenWings, "GW13", FlightType.PRIV,
                "Miami", "Nassau", LocalDate.now(), true);

        User johnnysAccount = new User("Johnny Thunder", "johnnythunder@gmail.com", "test", 22, Gender.M, Role.PASSENGER);
        var johnny = new Passenger(johnnysAccount, false);

        goldenWings.addFlight(flight1);
        qantas.addFlight(flight2);
        goldenWings.addFlight(flight3);

        flight2.addPassenger(johnny);
        johnny.addFlight(flight2);

        passengerRepository.save(johnny);
        airlineRepository.save(qantas);
        airlineRepository.save(goldenWings);
        flightRepository.save(flight1);
        flightRepository.save(flight2);
        flightRepository.save(flight3);
    }


    @Test
    void getFlightsByFlightNumberShouldReturnTheCorrectFlight() throws Exception {
        // Arrange
        var expectedFlight = flightRepository.findByFlightNumber("GW13");

        // Act + Assert
        mockMvc.perform(
                        get("/api/flights/{flightNumber}", "GW13")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.flightNumber").value(expectedFlight.getFlightNumber()))
                .andExpect(jsonPath("$.departure").value("Miami"));
    }

    @Test
    void getFlightShouldReturnTheFlightWithCorrectInformation() throws Exception {
        // Arrange
        var flightId = flightRepository.findByFlightNumber("QF12").getId(); // id = 1


        // Act & Assert
        mockMvc.perform(
                        get("/api/flight")
                                .queryParam("fId", String.valueOf(flightId))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON.toString()))
                .andExpect(jsonPath("$.departure").value("Los Angeles"));
    }
}