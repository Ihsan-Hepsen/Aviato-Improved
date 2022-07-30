package ih.ifbs.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ih.ifbs.domain.Airline;
import ih.ifbs.domain.Flight;
import ih.ifbs.domain.FlightType;
import ih.ifbs.presentation.api.dto.FlightDTO;
import ih.ifbs.services.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("testing")
@AutoConfigureMockMvc
public class FlightsControllerMockingTests {

    @MockBean
    private FlightService flightService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(value = "Air Kangaroo", roles = {"ADMIN"})
    public void updateFlightMustBeRejectedIfIdsDontMatch() throws Exception {
        // Arrange
        Airline airline = new Airline("Air Kangaroo", 12, 45);

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(999);
        flightDTO.setAirline("Air Kangaroo");
        flightDTO.setFlightNumber("AK123");
        flightDTO.setFlightType(FlightType.COMM);
        flightDTO.setDeparture("Sydney");
        flightDTO.setArrival("Gold Coast");
        flightDTO.setScheduledOn(LocalDate.of(2022, 6, 25));
        flightDTO.setOnTime(true);

        Flight flight = new Flight(
                airline, "AK123",
                FlightType.COMM, "Sydney", "Gold Coast",
                LocalDate.of(2022, 6, 25), true);
        flight.setId(123);
        given(flightService.findByFlightNumber("FL123")).willReturn(flight);

        // Act & Assert
        mockMvc.perform(
                        put("/api/flights/{fId}", 123)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(flightDTO))
                )
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(value = "Air Kangaroo", roles = {"ADMIN"})
    public void updateFlightMustReturnNotFoundIfFlightDoesNotExist() throws Exception {
        // Arrange
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(999);
        flightDTO.setAirline("Air Kangaroo");
        flightDTO.setFlightNumber("AK123");
        flightDTO.setFlightType(FlightType.COMM);
        flightDTO.setDeparture("Sydney");
        flightDTO.setArrival("Gold Coast");
        flightDTO.setScheduledOn(LocalDate.of(2022, 6, 25));
        flightDTO.setOnTime(false);

        given(flightService.findByFlightNumber("AK123")).willReturn(null);

        // Act & Assert
        mockMvc.perform(
                        put("/api/flights/{fId}", 999)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(flightDTO))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "Air Kangaroo", roles = {"ADMIN"})
    public void updateFlightShouldGoThroughIfEverythingIsInOrder() throws Exception {
        // Arrange
        Airline airline = new Airline("Air Kangaroo", 12, 45);

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setId(123);
        flightDTO.setAirline("Air Kangaroo");
        flightDTO.setFlightNumber("AK123");
        flightDTO.setFlightType(FlightType.COMM);
        flightDTO.setDeparture("Sydney");
        flightDTO.setArrival("Gold Coast");
        flightDTO.setScheduledOn(LocalDate.of(2022, 6, 25));
        flightDTO.setOnTime(false);

        Flight flight = new Flight(
                airline, "AK123",
                FlightType.COMM, "Sydney", "Gold Coast",
                LocalDate.of(2022, 4, 25), true);
        flight.setId(123);
        given(flightService.findById(123)).willReturn(flight);

        // Act & Assert
        mockMvc.perform(
                        put("/api/flights/{fId}", 123)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(flightDTO))
                )
                .andExpect(status().isNoContent());

        ArgumentCaptor<Flight> captor = ArgumentCaptor.forClass(Flight.class);
        verify(flightService).updateFlight(captor.capture());

        Flight capturedFlight = captor.getValue();
        assertEquals(flightDTO.getId(), capturedFlight.getId());
        assertEquals(flightDTO.getFlightNumber(), capturedFlight.getFlightNumber());
    }

}
