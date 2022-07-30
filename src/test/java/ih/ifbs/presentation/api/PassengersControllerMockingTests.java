package ih.ifbs.presentation.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import ih.ifbs.domain.Gender;
import ih.ifbs.domain.Passenger;
import ih.ifbs.domain.Role;
import ih.ifbs.domain.User;
import ih.ifbs.presentation.api.dto.PassengerDTO;
import ih.ifbs.services.PassengerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("testing")
@AutoConfigureMockMvc
public class PassengersControllerMockingTests {

    @MockBean
    private PassengerService passengerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test // Passenger editing - everything is in order
    @WithMockUser(username = "Han Solo", roles = {"USER"})
    public void onlyPassengerItSelfCanEditTheirPropertiesCaseOne() throws Exception {
        // Arrange
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId(123);
        passengerDTO.setName("Han Solo");
        passengerDTO.setAge(79);
        passengerDTO.setGender(Gender.M);
        passengerDTO.setTransitPassenger(false);

        User account = new User("Han Solo", "solohan@hotmail.com", "test", 35, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        passenger.setId(123);

        given(passengerService.findById(123)).willReturn(passenger);

        // Act + Assert
        // even though passengers have the same name they won't be able to modify the Passenger object
        mockMvc.perform(
                        put("/api/passengers/{pId}", 123)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(passengerDTO))
                )
                .andExpect(status().isNoContent());
    }


    @Test // Passenger editing - Same passengers with different id (Conflict)
    @WithMockUser(username = "Johnny Thunder", roles = {"USER"})
    public void onlyPassengerItSelfCanEditTheirPropertiesCaseTwo() throws Exception {
        // Arrange
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId(456);
        passengerDTO.setName("Han Solo");
        passengerDTO.setAge(79);
        passengerDTO.setGender(Gender.M);
        passengerDTO.setTransitPassenger(false);

        User account = new User("Han Solo", "solohan@hotmail.com", "test", 35, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        passenger.setId(123);

        given(passengerService.findById(123)).willReturn(passenger);

        // Act + Assert
        mockMvc.perform(
                        put("/api/passengers/{pId}", 123)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(passengerDTO))
                )
                .andExpect(status().isConflict());
    }


    @Test // Passenger Editing - Airline attempting to edit passenger's account - Forbidden
    @WithMockUser(username = "Qantas Airlines", roles = {"ADMIN"})
    public void airlinesCannotEditPassengers() throws Exception {
        // Arrange
        PassengerDTO passengerDTO = new PassengerDTO();
        passengerDTO.setId(123);
        passengerDTO.setName("Han Solo");
        passengerDTO.setAge(79);
        passengerDTO.setGender(Gender.M);
        passengerDTO.setTransitPassenger(false);

        User account = new User("Han Solo", "solohan@hotmail.com", "test", 35, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        passenger.setId(123);

        given(passengerService.findById(123)).willReturn(passenger);

        // Act + Assert
        // even though name of the passenger is the same, they won't be able to modify the Passenger object
        mockMvc.perform(
                        put("/api/passengers/{pId}", 123)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(passengerDTO))
                )
                .andExpect(status().isForbidden());
    }


    @Test // Passenger deletion
    @WithMockUser(username = "Qantas Airlines", roles = {"ADMIN"})
    public void onlyAirlinesCanDeletePassengers() throws Exception {
        // Arrange
        User account = new User("Han Solo", "solohan@hotmail.com", "test", 35, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        passenger.setId(999);

        given(passengerService.findById(999)).willReturn(passenger);

        // Act + Assert
        mockMvc.perform(
                        delete("/api/passengers/{pId}", "999")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test // Passenger deletion
    @WithMockUser(username = "Johnny Thunder", roles = {"USER"})
    public void passengersCannotDeleteOtherPassengers() throws Exception {
        // Arrange
        User account = new User("Han Solo", "solohan@hotmail.com", "test", 35, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        passenger.setId(999);

        given(passengerService.findById(999)).willReturn(passenger);

        // Act + Assert
        mockMvc.perform(
                        delete("/api/passengers/{pId}", "999")
                                .with(csrf())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }
}
