package microservices.beers.repository;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import microservices.beers.ApplicationConfiguration;
import microservices.beers.BeerNotFoundException;
import microservices.beers.BeerServiceException;
import microservices.beers.entity.Beer;
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
    public Beer addBeers(@NotNull Beer newBeer) throws Exception {
        Beer beer = newBeer;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:addBeers:" + newBeer);
        }
        try {

            entityManager.persist(beer);

        }
        catch (Exception e)
        {
            LOG.error("Ha ocurrido un error interno BeerRepositoryImpl:addBeers:" + e.getMessage());
            throw new Exception(e);
        }

        return beer;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Beer> searchBeers(SortingAndOrderArguments args)  throws Exception {
        String qlString = "SELECT g FROM Beer g";

        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:searchBeers(args):" + args);
        }

        try {

            if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
                qlString += " ORDER BY g." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
            }

            TypedQuery<Beer> query = entityManager.createQuery(qlString, Beer.class);

            if (args.getMax().isPresent() && args.getOffset().isPresent()) {
                query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
                args.getOffset().ifPresent(query::setFirstResult);
            }

            return query.getResultList();
        }
        catch (Exception e)
        {
            LOG.error("Ha ocurrido un error interno BeerRepositoryImpl:searchBeers(args):" + e.getMessage());
            throw new Exception(e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<Beer> searchBeers() throws Exception {
        String qlString = "SELECT g FROM Beer g";

        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:searchBeers");
        }

        try {

            TypedQuery<Beer> query = entityManager.createQuery(qlString, Beer.class);
            return query.getResultList();
        }
        catch (Exception e)
        {
            LOG.error("Ha ocurrido un error interno BeerRepositoryImpl:searchBeers:" + e.getMessage());
            throw new Exception(e);
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Beer searchBeerById(Integer beerID) throws BeerServiceException {
        Beer beer = null;
        String qlString = "SELECT g FROM Beer g" + " where g.id=" + beerID;

        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing BeerRepositoryImpl:searchBeerById:" + beerID );
        }

        try {

            TypedQuery<Beer> query = entityManager.createQuery(qlString, Beer.class);


            if (query.getResultList().isEmpty()) {

                throw new BeerNotFoundException();
            }
            else
            {
                beer = query.getResultList().get(0);
            }


        }

        catch (BeerServiceException e)
        {
            LOG.error("Ha ocurrido un error interno BeerRepositoryImpl:searchBeerById:" + e.getMessage());
            throw new BeerServiceException();
        }

        return beer;

    }
}
