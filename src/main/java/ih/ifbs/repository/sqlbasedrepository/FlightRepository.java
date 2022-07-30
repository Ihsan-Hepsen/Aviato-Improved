package ih.ifbs.repository.sqlbasedrepository;

import ih.ifbs.domain.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    Flight findByFlightNumber(String flightNumber);
    List<Flight> findAllByAirline_AirlineName(String name);
}
