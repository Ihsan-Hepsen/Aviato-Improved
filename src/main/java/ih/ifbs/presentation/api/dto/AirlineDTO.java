package ih.ifbs.presentation.api.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class AirlineDTO {

    private int id;

    @NotBlank(message="You must enter a airline name!")
    @Size(min=2, max=12,
            message="Invalid airline name. Valid airline name should be minimum of 2 and maximum of 12 characters.")
    private String airlineName;

    @Min(value=1, message="Invalid fleet size. Please make sure fleet size is greater (equal) than 1.")
    private int fleetSize;

    @Min(value=1, message="Invalid number of destinations. Please make sure number of destinations is greater (or equal than 1.")
    private int totalDestinations;

    private List<FlightDTO> flights;

    public AirlineDTO() {

    }

    public AirlineDTO(String airlineName, int fleetSize, int totalDestinations, List<FlightDTO> flights) {
        this.airlineName = airlineName;
        this.fleetSize = fleetSize;
        this.totalDestinations = totalDestinations;
        this.flights = flights;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getFleetSize() {
        return fleetSize;
    }

    public void setFleetSize(int fleetSize) {
        this.fleetSize = fleetSize;
    }

    public int getTotalDestinations() {
        return totalDestinations;
    }

    public void setTotalDestinations(int totalDestinations) {
        this.totalDestinations = totalDestinations;
    }

    public List<FlightDTO> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightDTO> flights) {
        this.flights = flights;
    }
}
