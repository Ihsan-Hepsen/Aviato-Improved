package ih.ifbs.bootstrap;

import ih.ifbs.domain.*;
import ih.ifbs.repository.sqlbasedrepository.AirlineRepository;
import ih.ifbs.repository.sqlbasedrepository.FlightRepository;
import ih.ifbs.repository.sqlbasedrepository.PassengerRepository;
import ih.ifbs.repository.sqlbasedrepository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import java.time.LocalDate;

@Configuration
@Profile("!testing")
@Transactional
public class DataSeedingHibernate implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeedingHibernate.class);
    private final AirlineRepository airlines;
    private final PassengerRepository passengers;
    private final FlightRepository flights;
    private final UserRepository users;

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public DataSeedingHibernate(AirlineRepository airlines,
                                PassengerRepository passengers,
                                FlightRepository flights,
                                UserRepository userRepository) {
        this.airlines = airlines;
        this.passengers = passengers;
        this.flights = flights;
        this.users = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.debug("Seeding the repositories(hibernate)");

        Airline a1 = new Airline("Qantas Airlines", 134, 77);
        Airline a2 = new Airline("Turkish Airlines", 350, 304);
        Airline a3 = new Airline("Air New Zealand", 114, 80);
        Airline a4 = new Airline("Virgin Australia", 68, 40);
        Airline a5 = new Airline("Bahamas Air", 3, 21);
        Airline a6 = new Airline("Private Voyages", 11, 13);
        Airline a7 = new Airline("Iberia Airlines", 68, 120);
        Airline a8 = new Airline("All Nippon Airways", 220, 75);
        Airline a9 = new Airline("Golden Wings", 6, 17);

        Flight f1 = new Flight(a1, "QF12", FlightType.COMM,
                "Los Angeles", "Sydney",
                LocalDate.of(2022, 3, 2), true);
        Flight f2 = new Flight(a2, "TK77", FlightType.COMM,
                "Istanbul", "Miami",
                LocalDate.of(2022, 3, 3), false);
        Flight f3 = new Flight(a6, "HI4506", FlightType.PRIV,
                "Honolulu", "Sydney",
                LocalDate.of(2022, 3, 4), true);
        Flight f4 = new Flight(a3, "NZ6141", FlightType.COMM,
                "Auckland", "Sydney",
                LocalDate.of(2022, 3, 1), false);
        Flight f5 = new Flight(a5, "BHS224", FlightType.COMM,
                "Miami", "Nassau",
                LocalDate.of(2022, 3, 1), false);
        Flight f6 = new Flight(a4, "VA526", FlightType.COMM,
                "Sydney", "Gold Coast",
                LocalDate.of(2022, 3, 2), true);
        Flight f7 = new Flight(a4, "VA734", FlightType.COMM,
                "Gold Coast", "Melbourne",
                LocalDate.of(2022, 3, 1), false);
        Flight f8 = new Flight(a6, "HI1204", FlightType.PRIV,
                "New York", "Miami",
                LocalDate.of(2022, 3, 1), false);
        Flight f9 = new Flight(a7, "IB3013", FlightType.COMM,
                "Barcelona", "Madrid",
                LocalDate.of(2022, 2, 28), true);
        Flight f10 = new Flight(a8, "NH802", FlightType.COMM,
                "Singapore", "Tokyo",
                LocalDate.of(2022, 2, 28), true);
        Flight f11 = new Flight(a9, "GW31", FlightType.PRIV,
                "Savannah", "Charleston",
                LocalDate.of(2022, 3, 1), true);

        User user1 = new User("Johnny Thunder", "johnnythunder@gmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                22, Gender.M, Role.PASSENGER);
        User user2 = new User("Anna Cooper", "annacooper@icloud.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                23, Gender.F, Role.PASSENGER);
        User user3 = new User("Padme Amidala", "amidale_padme@gmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                35, Gender.F, Role.PASSENGER);
        User user4 = new User("Kilroy Barron", "kilroybarron@hotmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                52, Gender.M, Role.PASSENGER);
        User user5 = new User("Senor Palomar", "senor.palomar@gmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                32, Gender.M, Role.PASSENGER);
        User user6 = new User("Kül Tigin", "kultigin@yahoo.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                37, Gender.M, Role.PASSENGER);
        User user7 = new User("Valeria Ferreira", "valeria.ferreira@gmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                26, Gender.F, Role.PASSENGER);
        User user8 = new User("Akane Tanaka", "akanetanaka@outlook.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                34, Gender.F, Role.PASSENGER);
        User user9 = new User("Sam Sinister", "sinister.sam@gmail.com",
                "$2a$10$VF7OwENe8KHC7tAAw5S77.PKVPjAOpDlmCoJk6zLr0Qv1xN3gu68C",
                37, Gender.M, Role.PASSENGER);


        Passenger p1 = new Passenger(user1, true);
        Passenger p2 = new Passenger(user2, true);
        Passenger p3 = new Passenger(user3, false);
        Passenger p4 = new Passenger(user4, false);
        Passenger p5 = new Passenger(user5, false);
        Passenger p6 = new Passenger(user6, false);
        Passenger p7 = new Passenger(user7, false);
        Passenger p8 = new Passenger(user8, false);
        Passenger p9 = new Passenger(user9, false);


        // Airlines are like the "ADMINs" of a website.
        // They can delete passengers or flights that are associated with them.
        User airlines1 = new User("qantas@airline.com",
                "Qantas Airlines",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines2 = new User("turkish@airline.com",
                "Turkish Airlines",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines3 = new User("private.voyages@airline.com",
                "Private Voyages",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines4 = new User("anz@airline.com",
                "Air New Zealand",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines5 = new User("bahamas@airline.com",
                "Bahamas Air",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines6 = new User("virgin.australia@airline.com",
                "Virgin Australia",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines7 = new User("iberia@airline.com",
                "Iberia Airlines",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines8 = new User("ana@airline.com",
                "All Nippon Airlines",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);
        User airlines9 = new User("golden.wings@airline.com",
                "Golden Wings",
                "$2a$10$q0fO1phkKOZrWmMB/p.jI.fA2kWdvr.ZV/9hk2BZG/4qrFjhFv8BS",
                Role.AIRLINE);

        users.save(user1);
        users.save(user2);
        users.save(user3);
        users.save(user4);
        users.save(user5);
        users.save(user6);
        users.save(user7);
        users.save(user8);
        users.save(user9);

        users.save(airlines1);
        users.save(airlines2);
        users.save(airlines3);
        users.save(airlines4);
        users.save(airlines5);
        users.save(airlines6);
        users.save(airlines7);
        users.save(airlines8);
        users.save(airlines9);

        airlines.save(a1);
        airlines.save(a2);
        airlines.save(a3);
        airlines.save(a4);
        airlines.save(a5);
        airlines.save(a6);
        airlines.save(a7);
        airlines.save(a8);
        airlines.save(a9);

        flights.save(f1);
        flights.save(f2);
        flights.save(f3);
        flights.save(f4);
        flights.save(f5);
        flights.save(f6);
        flights.save(f7);
        flights.save(f8);
        flights.save(f9);
        flights.save(f10);
        flights.save(f11);

        passengers.save(p1);
        passengers.save(p2);
        passengers.save(p3);
        passengers.save(p4);
        passengers.save(p5);
        passengers.save(p6);
        passengers.save(p7);
        passengers.save(p8);
        passengers.save(p8);
        passengers.save(p9);

        a1.addFlight(f1);
        a2.addFlight(f2);
        a6.addFlight(f3);
        a3.addFlight(f4);
        a5.addFlight(f5);
        a4.addFlight(f6);
        a4.addFlight(f7);
        a6.addFlight(f8);
        a7.addFlight(f9);
        a8.addFlight(f10);
        a9.addFlight(f11);

        p1.addFlight(f1);
        p1.addFlight(f6);
        p1.addFlight(f7);
        p2.addFlight(f2);
        p2.addFlight(f5);
        p3.addFlight(f3);
        p4.addFlight(f4);
        p5.addFlight(f5);
        p6.addFlight(f8);
        p7.addFlight(f9);
        p8.addFlight(f10);
        p9.addFlight(f11);

        f1.addPassenger(p1);
        f2.addPassenger(p2);
        f3.addPassenger(p3);
        f4.addPassenger(p4);
        f5.addPassenger(p5);
        f8.addPassenger(p6);
        f9.addPassenger(p7);
        f10.addPassenger(p8);
        f11.addPassenger(p9);
    }
}
