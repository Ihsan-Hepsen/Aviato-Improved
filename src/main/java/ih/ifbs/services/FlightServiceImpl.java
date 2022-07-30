package ih.ifbs.services;

import ih.ifbs.domain.Flight;
import ih.ifbs.exceptions.FlightNotFoundException;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;


@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }


    @Override
    public void deleteFlightById(int id) {
        flightRepository.deleteById(id);
    }

    @Override
    public void updateFlight(Flight flight) {
        flightRepository.save(flight);
    }

    @Override
    public List<Flight> getAllFlightsOfAnAirline(String airlineName) {
        return flightRepository.findAllByAirline_AirlineName(airlineName);
    }


    @Override
    public Flight findById(int id) {
        return flightRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Flight findByFlightNumber(String flightNumber) {
        flightNumber = flightNumber.toUpperCase(Locale.ROOT);
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null) {
            throw new FlightNotFoundException(flightNumber, "Flight '" + flightNumber + "' not found!");
        }
        return flight;
    }
}

