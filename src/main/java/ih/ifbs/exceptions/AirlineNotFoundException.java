package ih.ifbs.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such airline.")
public class AirlineNotFoundException extends RuntimeException {

    private final String airlineName;

    public AirlineNotFoundException(String message, String airlineName) {
        super(message);
        this.airlineName = airlineName;
    }

    public String getAirlineName() {
        return airlineName;
    }
}
