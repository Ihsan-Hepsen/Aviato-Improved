//package ih.ifbs.repository.hsqlrepository;
//
//import ih.ifbs.domain.Passenger;
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
//public class HSQLPassengerRepositoryHibernate implements EntityRepository<Passenger> {
//
//    private final Logger log = LoggerFactory.getLogger(HSQLFlightRepositoryHibernate.class);
//
//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
//    private EntityManager em;
//
//    @Override
//    public List<Passenger> read() {
//        log.debug("Reading all passengers(hibernate)");
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        List<Passenger> passengers = em.createQuery("SELECT p FROM Passenger p", Passenger.class).getResultList();
//        em.getTransaction().commit();
//        em.close();
//        return passengers;
//    }
//
//    @Override
//    public Passenger create(Passenger passenger) {
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(passenger);
//        log.info("Created passenger: " + passenger.toString());
//        em.getTransaction().commit();
//        em.close();
//        return passenger;
//    }
//
//    @Override
//    public Passenger findById(int id) {
//        log.debug("Finding flight: " + id);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        Passenger passenger = em.find(Passenger.class, id);
//        em.getTransaction().commit();
//        em.close();
//        return passenger;
//    }
//
//    @Override
//    public void delete(Passenger passenger) {
//        log.debug("Deleting: " + passenger);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.remove(passenger);
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    @Override
//    public void update(Passenger passenger) {
//
//    }
//}
