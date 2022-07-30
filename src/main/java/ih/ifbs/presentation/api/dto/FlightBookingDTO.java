package ih.ifbs.presentation.api.dto;

import javax.validation.constraints.Min;

public class FlightBookingDTO {

    @Min(value = 0, message = "Invalid ID")
    private int passengerId;

    @Min(value = 0, message = "Invalid ID")
    private int flightId;

    public FlightBookingDTO() {
    }

    public FlightBookingDTO(int passengerId, int flightId) {
        this.passengerId = passengerId;
        this.flightId = flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }
}
