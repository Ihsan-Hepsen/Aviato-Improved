//package ih.ifbs.repository.hsqlrepository;
//
//import ih.ifbs.domain.Airline;
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
//public class HSQLAirlineRepositoryHibernate implements EntityRepository<Airline> {
//
//    private final Logger log = LoggerFactory.getLogger(HSQLFlightRepositoryHibernate.class);
//
//    @PersistenceUnit
//    private EntityManagerFactory entityManagerFactory;
//    private EntityManager em;
//
//    @Override
//    public List<Airline> read() {
//        log.debug("Reading all passengers(hibernate)");
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        List airlines = em.createQuery("SELECT a FROM Airline a").getResultList();
//        em.getTransaction().commit();
//        em.close();
//        return airlines;
//    }
//
//    @Override
//    public Airline create(Airline airline) {
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(airline);
//        log.info("Created airline: " + airline.toString());
//        em.getTransaction().commit();
//        em.close();
//        return airline;
//    }
//
//    @Override
//    public Airline findById(int id) {
//        log.debug("Finding flight: " + id);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        Airline airline = em.find(Airline.class, id);
//        em.getTransaction().commit();
//        em.close();
//        return airline;
//    }
//
//    @Override
//    public void delete(Airline airline) {
//        log.debug("Deleting: " + airline);
//        em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        em.remove(airline);
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    @Override
//    public void update(Airline airline) {
//
//    }
//}
//
