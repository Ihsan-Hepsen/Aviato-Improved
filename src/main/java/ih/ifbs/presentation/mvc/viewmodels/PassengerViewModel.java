package ih.ifbs.presentation.mvc.viewmodels;

import ih.ifbs.domain.Gender;
import javax.validation.constraints.*;

public class PassengerViewModel {

    @NotBlank(message="Name field cannot be left empty!")
    @Size(min=2, max=50, message="Name should consist of minimum of 2 characters and maximum of 50 charters")
    private String name;

    @NotBlank(message="Name field cannot be left empty!")
    @Size(min=2, max=50, message="Name should consist of minimum of 2 characters and maximum of 50 charters")
    private String lastName;

    @Min(value=0, message="Invalid age. Please make sure age is greater than 0.")
    @Max(value=120, message="Invalid age. Age cannot be bigger than 120.")
    private int age;
    private Gender gender;
    private boolean transitPassenger;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
}
