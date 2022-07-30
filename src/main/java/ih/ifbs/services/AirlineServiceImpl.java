package ih.ifbs.services;

import ih.ifbs.domain.Airline;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository repository;

    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineRepository) {
        this.repository = airlineRepository;
    }

    @Override
    public Airline findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Airline addAirline(Airline airline) {
        return repository.save(airline);
    }

    @Override
    public List<Airline> getAllAirlines() {
        return repository.findAll();
    }

    @Override
    public Set<String> getAllAirlineNames() {
        return repository.getAllAirlineNames().stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Airline findAirlineByAirlineName(String airline) {
        return repository.findAirlineByAirlineName(airline);
    }

    @Override
    public void updateAirline(Airline airline) {
        repository.save(airline);
    }

    @Override
    public void deleteAirlineById(int id) {
        repository.deleteById(id);
    }
}
