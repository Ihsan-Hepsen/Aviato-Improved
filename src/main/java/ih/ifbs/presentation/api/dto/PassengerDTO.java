package ih.ifbs.presentation.api.dto;

import ih.ifbs.domain.Gender;

import javax.validation.constraints.*;
import java.util.List;

public class PassengerDTO {

    private int id;

    @NotBlank(message="Name field cannot be left empty!")
    @Size(min=2, max=50, message="Name should consist of minimum of 2 characters and maximum of 50 charters")
    private String name;

    @Min(value=0, message="Invalid age. Please make sure age is greater than 0.")
    @Max(value=120, message="Invalid age. Age cannot be bigger than 120.")
    private int age;

    @NotNull
    private Gender gender;

    private boolean transitPassenger;
    private List<FlightDTO> flights;

    public PassengerDTO() {
    }

    public PassengerDTO(String name, int age, Gender gender, boolean transitPassenger, List<FlightDTO> flights) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.transitPassenger = transitPassenger;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isTransitPassenger() {
        return transitPassenger;
    }

    public void setTransitPassenger(boolean transitPassenger) {
        this.transitPassenger = transitPassenger;
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDTO> flights) {
        this.flights = flights;
    }
}
