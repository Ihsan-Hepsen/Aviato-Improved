//package ih.ifbs.repository.hsqlrepository;
//
//import ih.ifbs.domain.Flight;
//import ih.ifbs.repository.EntityRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Repository;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceUnit;
//import java.util.List;
//
//@Repository
//@Profile("dev-hiber")
//public class HSQLFlightRepositoryHibernate implements EntityRepository<Flight> {
//
//    private final Logger log = LoggerFactory.getLogger(HSQLFlightRepositoryHibernate.class);
//
//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
//    private EntityManager em;
//
//    @Override
//    public List<Flight> read() {
//        log.debug("Reading all flights(hibernate)");
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        List flights = em.createQuery("SELECT f FROM Flight f").getResultList();
//        em.getTransaction().commit();
//        em.close();
//        return flights;
//    }
//
//    @Override
//    public Flight create(Flight flight) {
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(flight);
//        log.info("Created flight: " + flight.toString());
//        em.getTransaction().commit();
//        em.close();
//        return flight;
//    }
//
//    @Override
//    public Flight findById(int id) {
//        log.debug("Finding flight: " + id);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        Flight flight = em.find(Flight.class, id);
//        em.getTransaction().commit();
//        em.close();
//        return flight;
//    }
//
//    @Override
//    public void delete(Flight flight) {
//        log.debug("Deleting: " + flight);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.remove(flight);
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    @Override
//    public void update(Flight flight) {
//
//    }
//}
