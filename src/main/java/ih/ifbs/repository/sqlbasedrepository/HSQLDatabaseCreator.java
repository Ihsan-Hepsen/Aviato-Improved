//package ih.ifbs.repository.hsqlrepository;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//
//@Component
//@Profile("dev")
//public class HSQLDatabaseCreator {
//    private static final Logger log = LoggerFactory.getLogger(HSQLDatabaseCreator.class);
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public HSQLDatabaseCreator(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @PostConstruct
//    public void loadData() {
//        log.debug("Loading data from the HSQL database...");
//        // FLIGHTS table
//        jdbcTemplate.update("DROP TABLE IF EXISTS FLIGHTS CASCADE");
//        jdbcTemplate.update(
//                "CREATE TABLE FLIGHTS(" +
//                        "    ID INTEGER NOT NULL IDENTITY," +
//                        "    AIRLINE VARCHAR(50) NOT NULL," +
//                        "    FLIGHT_NUMBER VARCHAR(25) UNIQUE NOT NULL," +
//                        "    FLIGHT_TYPE VARCHAR(25) NOT NULL," +
//                        "    DEPARTURE VARCHAR(50) NOT NULL," +
//                        "    ARRIVAL VARCHAR(50) NOT NULL," +
//                        "    DATE DATE NOT NULL," +
//                        "    ON_TIME BOOLEAN NOT NULL" +
//                        ");");
//        jdbcTemplate.update("INSERT INTO FLIGHTS(ID, AIRLINE, FLIGHT_NUMBER, FLIGHT_TYPE, DEPARTURE, ARRIVAL, DATE, ON_TIME)" +
//                "VALUES (0, 'Qantas Airlines', 'QF12', 'COMM', 'Los Angeles', 'Sydney', DATE '2021-10-21', true)," +
//                "       (1, 'Turkish Airlines', 'TK77', 'COMM', 'Istanbul', 'Miami', DATE '2021-10-22', false)," +
//                "       (2, 'Private Voyages', 'HI4506', 'PRIV', 'Honolulu', 'Sydney', DATE '2021-10-21', true)," +
//                "       (3, 'Air New Zealand', 'NZ6141', 'COMM', 'Auckland', 'Sydney', DATE '2021-10-22', false)," +
//                "       (4, 'Bahamas Air', 'BHS224', 'COMM', 'Miami', 'Nassau', DATE '2021-10-23', false)," +
//                "       (5, 'Virgin Australia', 'VA526', 'COMM', 'Sydney', 'Gold Coast', DATE '2021-10-22', true)," +
//                "       (6, 'Virgin Australia', 'VA734', 'COMM', 'Gold Coast', 'Melbourne', DATE '2021-10-23', false)," +
//                "       (7, 'Private Voyages', 'HI1204', 'PRIV', 'New York', 'Miami', DATE '2021-10-23', false)," +
//                "       (8, 'Iberia Airlines', 'IB3013', 'COMM', 'Barcelona', 'Madrid', DATE '2021-11-19', true)," +
//                "       (9, 'All Nippon Airways', 'NH802', 'COMM', 'Singapore', 'Tokyo', DATE '2021-12-13', true);");
//
//        // PASSENGERS table
//        jdbcTemplate.update("DROP TABLE IF EXISTS PASSENGERS CASCADE");
//        jdbcTemplate.update("CREATE TABLE PASSENGERS(" +
//                "    ID INTEGER NOT NULL IDENTITY," +
//                "    NAME VARCHAR(100) NOT NULL," +
//                "    AGE INTEGER NOT NULL," +
//                "    GENDER VARCHAR(20) NOT NULL," +
//                "    TRANSIT BOOLEAN NOT NULL," +
//                "    FLIGHTS INTEGER" +
//                ");");
//        jdbcTemplate.update("INSERT INTO PASSENGERS(id, name, age, gender, transit, flights)" +
//                "VALUES(0, 'Johnny Thunder', 21, 'M', TRUE, 3)," +
//                "      (1, 'Anna Cooper', 23, 'F', TRUE, 2)," +
//                "      (2, 'Padme Amidala', 35, 'F', FALSE, 1)," +
//                "      (3, 'Kilroy Barron', 52, 'M', FALSE, 1)," +
//                "      (4, 'Senor Palomar', 32, 'M', FALSE, 1)," +
//                "      (5, 'Kül Tigin', 37, 'M', FALSE, 1)," +
//                "      (6, 'Valaria Ferreria', 26, 'F', FALSE, 1)," +
//                "      (7, 'Akane Tanaka', 34, 'F', FALSE, 1);");
//
//        // BOOKED_FLIGHTS table (intermediate table for FLIGHTS & PASSENGERS tables)
//        jdbcTemplate.update("DROP TABLE IF EXISTS BOOKED_FLIGHTS CASCADE;");
//        jdbcTemplate.update("CREATE TABLE BOOKED_FLIGHTS(" +
//                "    FLIGHT_ID INTEGER FOREIGN KEY REFERENCES FLIGHTS(id)," +
//                "    PASSENGER_ID INTEGER FOREIGN KEY REFERENCES PASSENGERS(id));");
//        jdbcTemplate.update("INSERT INTO BOOKED_FLIGHTS(FLIGHT_ID, PASSENGER_ID)" +
//                "VALUES (0, 0)," +
//                "       (5, 0)," +
//                "       (6, 0)," +
//                "       (1, 1)," +
//                "       (4, 1)," +
//                "       (2, 2)," +
//                "       (3, 3)," +
//                "       (3, 4)," +
//                "       (7, 5)," +
//                "       (8, 6)," +
//                "       (9, 7);");
//    }
//}
