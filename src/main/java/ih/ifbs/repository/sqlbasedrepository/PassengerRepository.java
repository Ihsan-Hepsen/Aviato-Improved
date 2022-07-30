package ih.ifbs.repository.sqlbasedrepository;

import ih.ifbs.domain.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {
    @Query("SELECT p FROM Passenger p WHERE p.transitPassenger = ?1")
    List<Passenger> findAllByTransitPassenger(boolean transit);
    List<Passenger> findAllByAccountUsernameContaining(String accountUsername);
    Passenger findPassengerByAccountUsername(String accountUsername);
    Passenger findByAccountUsername(String name);
//    List<Passenger> findAllByName(String name);
//    Passenger findByName(String name);
}
