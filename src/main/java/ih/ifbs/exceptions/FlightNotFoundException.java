package ih.ifbs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid flight number")
public class FlightNotFoundException extends RuntimeException {
    private final String flightNumber;

    public FlightNotFoundException(String flightNumber, String message) {
        super(message);
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
