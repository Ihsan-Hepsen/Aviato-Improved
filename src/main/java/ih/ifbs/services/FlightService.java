package ih.ifbs.services;

import ih.ifbs.domain.Flight;
import java.util.Collection;
import java.util.List;

public interface FlightService {
    Flight findById(int id);
    Flight findByFlightNumber(String flightNumber);
    Flight addFlight(Flight flight);
    List<Flight> getAllFlights();
    void deleteFlightById(int id);
    void updateFlight(Flight flight);
    List<Flight> getAllFlightsOfAnAirline(String airlineName);
}
