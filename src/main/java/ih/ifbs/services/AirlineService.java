package ih.ifbs.services;

import ih.ifbs.domain.Airline;
import ih.ifbs.domain.Flight;

import java.util.List;
import java.util.Set;

public interface AirlineService {
    Airline findById(int id);
    Airline addAirline(Airline airline);
    List<Airline> getAllAirlines();
    Set<String> getAllAirlineNames();
    Airline findAirlineByAirlineName(String airline);
    void updateAirline(Airline airline);
    void deleteAirlineById(int id);
}
