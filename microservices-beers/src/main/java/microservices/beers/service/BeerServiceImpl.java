package microservices.beers.service;

import microservices.beers.BeerServiceException;
import microservices.beers.client.HttpClientApiLayerEntity;
import microservices.beers.client.HttpClientImpl;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import microservices.beers.repository.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Singleton
public class BeerServiceImpl implements BeerService {


    protected final BeerRepository bearRepository;
    private final HttpClientImpl httpClientImpl;
    private static final Logger LOG = LoggerFactory.getLogger(BeerServiceImpl.class);

    public BeerServiceImpl(BeerRepository bearRepository, HttpClientImpl httpClientImpl) {
        this.bearRepository = bearRepository;
        this.httpClientImpl = httpClientImpl;

    }

    @Override
    public Beer addBeers(Beer beer) throws Exception {
        Beer newBeer = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing addBeers:" + beer);
        }
        try {
           // if (bearRepository.searchBeerById(beer.getId()) == null)

                newBeer = bearRepository.addBeers(beer);
            //}

        } catch (Exception e) {
            LOG.error("Ha ocurrido un error interno BeerServiceImpl:addBeers:" + e.getMessage());
            throw e;
        }
        return newBeer;
    }


    @Override
    public List<Beer> searchBeers() throws Exception {
        List<Beer> listBeer = new ArrayList<>();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing searchBeers");
        }

        try {
            //TODO: puede ser mejor sobre cargar metodo y tener un searchBeer sin argumentos
            listBeer = bearRepository.searchBeers();
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error interno BeerServiceImpl:searchBeers:" + e.getMessage());
            throw new Exception(e);
        }
        return listBeer;
    }

    @Override
    public Beer searchBeerById(Integer beerID) throws BeerServiceException {
        /*
        * TODO: nuevamente manejo de exceptions, si manejamos excepciones correctamente
        *  podriamos tener un codigo como el siguiente:
        *  public Beer searchBeerById(Integer beerId) throws BeerNotFoundException {
        *   return beerRepository.searchBeerById(beerId);
        *  }
         */

        Beer beer = null;


        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing searchBeerById beerID:"+ beerID );
        }

        return bearRepository.searchBeerById(beerID);


      /*  if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing searchBeerById beerID:"+ beerID );
        }

        try {
            beer = bearRepository.searchBeerById(beerID);


        } catch (Exception e) {
            LOG.error("Ha ocurrido un error interno BeerServiceImpl:searchBeerById:" + e.getMessage());
            throw new Exception(e);
        }
        return beer;*/
    }

    @Override
    public BeerBox boxBeerPriceById(Integer beerID) throws Exception {
        Beer beer = null;
        BeerBox beerBox = null;
        HttpClientApiLayerEntity clientApiLayerEntity;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing boxBeerPriceById beerID:"+ beerID );
        }

        try {
            beer = bearRepository.searchBeerById(beerID);
            if (beer != null) {
                clientApiLayerEntity = httpClientImpl.getCuotesByCurrencies(beer.getCurrency());
                if (clientApiLayerEntity.isSuccess()) {
                    beerBox = new BeerBox();
                    Map<String, Object> quotes = clientApiLayerEntity.getQuotes();
                    double changeMoney = (Double) quotes.get(clientApiLayerEntity.getSource() + beer.getCurrency());
                    double priceTotal = changeMoney * beerBox.getNumberPackage().doubleValue() * beer.getPrice().doubleValue();
                    beerBox = new BeerBox();
                    beerBox.setPriceTotal(priceTotal);
                    beerBox.setBeer(Collections.singleton(beer));
                    beerBox.setName(beer.getName());
                }
            }
        } catch (Exception e) {
            LOG.error("Ha ocurrido un error interno BeerServiceImpl:boxBeerPriceById:" + e.getMessage());
            throw new Exception(e);
        }
        return beerBox;
    }


}
