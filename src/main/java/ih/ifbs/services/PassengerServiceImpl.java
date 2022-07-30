package ih.ifbs.services;

import ih.ifbs.domain.Passenger;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository repository;

    @Autowired
    public PassengerServiceImpl(PassengerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Passenger findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Passenger addPassenger(Passenger passenger) {
        return repository.save(passenger);
    }

    @Override
    public List<Passenger> getAllPassengers() {
        return repository.findAll();
    }

    @Override
    public List<Passenger> getAllPassengers(boolean isTransit) {
        return repository.findAll().stream()
                .filter(n -> isTransit == n.isTransitPassenger())
                .collect(Collectors.toList());
    }

    @Override
    public List<Passenger> findAllByTransitPassenger(boolean isTransit) {
        return repository.findAllByTransitPassenger(isTransit);
    }

    @Override
    public List<Passenger> findAllByName(String name) {
        return repository.findAllByAccountUsernameContaining(name);
    }

    @Override
    public void updatePassenger(Passenger passenger) {
        repository.save(passenger);
    }

    @Override
    public void deletePassengerById(int id) {
        repository.deleteById(id);
    }

    @Override
    public Passenger getByName(String name) {
        return repository.findPassengerByAccountUsername(name);
    }

}
