//package ih.ifbs.repository.hsqlrepository;
//
//import ih.ifbs.domain.Gender;
//import ih.ifbs.domain.Passenger;
//import ih.ifbs.repository.EntityRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Repository
//@Profile("dev")
//public class HSQLPassengerRepository implements EntityRepository<Passenger> {
//
//    private static final Logger log = LoggerFactory.getLogger(HSQLFlightRepository.class);
//    private final JdbcTemplate jdbcTemplate;
//    private final SimpleJdbcInsert passengerInserter;
//
//    @Autowired
//    public HSQLPassengerRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//        this.passengerInserter = new SimpleJdbcInsert(jdbcTemplate)
//                .withTableName("PASSENGERS")
//                .usingGeneratedKeyColumns("ID");
//    }
//
//    public Passenger mapRow(ResultSet rs, int mapRow) throws SQLException {
//        log.debug("Returning passengers with ResultSet");
//        Passenger passenger = new Passenger(rs.getString("name"), rs.getInt("age"),
//                Gender.lookup(rs.getString("gender")),
//                rs.getBoolean("transit"));
//        passenger.setId(rs.getInt("id"));
//        return passenger;
//    }
//
//    @Override
//    public List<Passenger> read() {
//        return jdbcTemplate.query("SELECT * FROM PASSENGERS", this::mapRow);
//    }
//
//    @Override
//    public Passenger create(Passenger passenger) {
//        log.debug("Saving passenger: " + passenger);
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("NAME", passenger.getName());
//        parameters.put("AGE", passenger.getAge());
//        parameters.put("GENDER", passenger.getGender().toString());
//        parameters.put("TRANSIT", passenger.isTransitPassenger());
//        parameters.put("FLIGHTS", passenger.getNumberOfFlights());
//        passenger.setId(passengerInserter.executeAndReturnKey(parameters).intValue());
//        return passenger;
//    }
//
//    public void delete(Passenger passenger) {
//        log.debug("Deleting passenger: " + passenger);
//        jdbcTemplate.queryForObject("DELETE FROM PASSENGERS WHERE ID = ?", this::mapRow, passenger.getId());
//    }
//
//    public void update(Passenger passenger) {
//
//    }
//
//    @Override
//    public Passenger findById(int id) {
//        log.debug("Passenger found with id: " + id);
//        return jdbcTemplate.queryForObject("SELECT * FROM PASSENGERS WHERE id = ?", this::mapRow, id);
//    }
//
//    public List<Passenger> findByName(String name) {
//        log.debug("Trying to find passenger '" + name + "'...");
//        return jdbcTemplate.query("SELECT * FROM PASSENGERS WHERE FLIGHT_NUMBER = ?", this::mapRow, name);
//    }
//}
