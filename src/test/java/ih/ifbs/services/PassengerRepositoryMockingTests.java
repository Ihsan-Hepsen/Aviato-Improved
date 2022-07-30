package ih.ifbs.services;

import ih.ifbs.domain.Gender;
import ih.ifbs.domain.Passenger;
import ih.ifbs.domain.Role;
import ih.ifbs.domain.User;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("testing")
public class PassengerServiceMockingTests {

    @Autowired
    private PassengerService passengerService;

    @MockBean
    private PassengerRepository passengerRepository;

    @Test // Testing .getByName(String name)
    public void findByNameMustBeAbleToReturnTheCorrectPassengerWhenGivenNameCorrespondsTheOneInTheRepository() {
        // Arrange
        User account = new User("Johnny Thunder", "johnnythunder@gmail.com", "test", 22, Gender.M, Role.PASSENGER);
        Passenger passenger = new Passenger(account, false);
        given(passengerRepository.findPassengerByAccountUsername("Johnny Thunder")).willReturn(passenger);

        // Act
        Passenger returnedPassenger = passengerService.getByName("Johnny Thunder");

        // Assert
        assertEquals(passenger.getId(), returnedPassenger.getId());
        assertEquals(passenger.getAccount().getUsername(), returnedPassenger.getAccount().getUsername());
        assertEquals(passenger.getAccount().getAge(), returnedPassenger.getAccount().getAge());
        assertEquals(passenger.getAccount().getGender(), returnedPassenger.getAccount().getGender());
        assertEquals(passenger.isTransitPassenger(), returnedPassenger.isTransitPassenger());
        assertEquals(passenger, returnedPassenger);
    }

    @Test // Testing .getByName(String name)
    public void findByNameMustReturnNullIfPassengerNameDoesNotCorrespondTheOneInTheRepository() {
        // Arrange
        given(passengerRepository.findPassengerByAccountUsername("Anakin Skywalker")).willReturn(null);

        // Act
        Passenger passenger = passengerService.getByName("Anakin Skywalker");

        // Assert
        assertNull(passenger);
    }
}
