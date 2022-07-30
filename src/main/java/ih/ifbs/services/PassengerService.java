package ih.ifbs.services;

import ih.ifbs.domain.Passenger;
import java.util.List;

public interface PassengerService {
    Passenger findById(int id);
    Passenger addPassenger(Passenger passenger);
    List<Passenger> getAllPassengers();
    @Deprecated
    List<Passenger> getAllPassengers(boolean isTransit);
    List<Passenger> findAllByTransitPassenger(boolean isTransit); // JPA
    List<Passenger> findAllByName(String name);
    void updatePassenger(Passenger passenger);
    void deletePassengerById(int id);
    Passenger getByName(String name);
}
