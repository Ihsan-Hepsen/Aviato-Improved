package ih.ifbs.repository.sqlbasedrepository;

import ih.ifbs.domain.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
    Airline findAirlineByAirlineName(String airline);

    @Query("SELECT a.airlineName FROM Airline a")
    Set<String> getAllAirlineNames();
}
