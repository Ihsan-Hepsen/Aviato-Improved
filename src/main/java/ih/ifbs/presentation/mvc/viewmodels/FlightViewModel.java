package ih.ifbs.presentation.mvc.viewmodels;

import ih.ifbs.domain.FlightType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class FlightViewModel {

    @NotBlank(message="Airline name field cannot be left empty.")
    @Size(min=5, max=50, message="Airline name should consist of a minimum of 5 characters and a maximum of 50 charters.")
    private String airline;

    @NotBlank(message="You must enter a flight number!")
    @Size(min=2, max=12,
            message="Invalid flight number. Valid flight number should be minimum of 2 and maximum of 12 characters.")
    private String flightNumber;

    private FlightType flightType;

    @NotBlank(message="You must enter departure city!")
    private String departure;

    @NotBlank(message="You must enter arrival city!")
    private String arrival;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message="You must select a date")
    private LocalDate scheduledOn;

    private boolean onTime;

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public LocalDate getScheduledOn() {
        return scheduledOn;
    }

    public void setScheduledOn(LocalDate scheduledOn) {
        this.scheduledOn = scheduledOn;
    }

    public boolean isOnTime() {
        return onTime;
    }

    public void setOnTime(boolean onTime) {
        this.onTime = onTime;
    }
}
