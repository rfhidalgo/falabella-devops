package microservices.beers.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import microservices.beers.ApplicationConfiguration;
import microservices.beers.entity.Beer;
import microservices.beers.exeption.BeerException;
import microservices.beers.exeption.BeerStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Singleton
public class BeerRepositoryImpl implements BeerRepository {
    private static final Logger LOG = LoggerFactory.getLogger(BeerRepositoryImpl.class);
    private static final List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "name");

    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;


    public BeerRepositoryImpl(@CurrentSession EntityManager entityManager, ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    @Transactional
    public Beer addBeers(@NotNull Beer newBeer) {
        Beer beer = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:addBeers: {}", newBeer);
        }
        beer = entityManager.find(Beer.class, newBeer.getId());
        if (beer != null) {
            throw new BeerException(BeerStatusException.CONFLICT, "El ID de la cerveza ya existe:" + newBeer.getId(),"/beers");
        }
        entityManager.persist(newBeer);
        return newBeer;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Beer> searchBeers(SortingAndOrderArguments args) {
        String qlString = "SELECT g FROM Beer g";

        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:searchBeers(args): {}", args);
        }

        if ((args.getOrder().isPresent() && args.getSort().isPresent()) && VALID_PROPERTY_NAMES.contains(args.getSort().toString())) {
            qlString += " ORDER BY g." + args.getSort() + " " + args.getOrder().toString().toLowerCase();
        }

        TypedQuery<Beer> query = entityManager.createQuery(qlString, Beer.class);
        if (args.getMax().isPresent() && args.getOffset().isPresent()) {
            query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
            args.getOffset().ifPresent(query::setFirstResult);
        }
        return query.getResultList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Beer> searchBeers() {
        String qlString = "SELECT g FROM Beer g";
        TypedQuery<Beer> query = entityManager.createQuery(qlString, Beer.class);
        return query.getResultList();
    }


    @Override
    @Transactional(readOnly = true)
    public Beer searchBeerById(Integer beerID) {
        Beer beer = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:searchBeerById: {}", beerID);
        }
        beer = entityManager.find(Beer.class, beerID);
        if (beer == null) {
            throw new BeerException(BeerStatusException.NOT_FOUND, "El Id de la cerveza no existe:" + beerID, "/beers/" + beerID);
        }
        return beer;
    }
}
