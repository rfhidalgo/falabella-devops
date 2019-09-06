package microservices.beers.service;

import io.micronaut.validation.validator.Validator;
import microservices.beers.client.HttpClient;
import microservices.beers.client.HttpClientApiLayerEntity;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import microservices.beers.exeption.BeerException;
import microservices.beers.exeption.BeerStatusException;
import microservices.beers.repository.BeerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Singleton
public class BeerServiceImpl implements BeerService {


    private final BeerRepository bearRepository;
    private final HttpClient httpClient;
    private Validator validator;
    private static final Logger LOG = LoggerFactory.getLogger(BeerServiceImpl.class);

    public BeerServiceImpl(BeerRepository bearRepository, HttpClient httpClient) {
        this.bearRepository = bearRepository;
        this.httpClient = httpClient;

    }

    @Override
    public Beer addBeers(Beer beer) {
       if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing addBeers: {}", beer);
        }
        return bearRepository.addBeers(beer);
    }


    @Override
    public List<Beer> searchBeers() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing searchBeers");
        }
        return bearRepository.searchBeers();
    }


    @Override
    public Beer searchBeerById(Integer beerID) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing searchBeerById beerID: {}", beerID);
        }
        return bearRepository.searchBeerById(beerID);
    }


    @Override
    public BeerBox boxBeerPriceById(Integer beerID) {
        Beer beer = null;
        BeerBox beerBox = null;
        HttpClientApiLayerEntity clientApiLayerEntity;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Tracing boxBeerPriceById beerID: {}", beerID);
        }
        beer = bearRepository.searchBeerById(beerID);
        clientApiLayerEntity = httpClient.getCuotesByCurrencies(beer.getCurrency());
        if (!clientApiLayerEntity.isSuccess()) {
            throw new BeerException(BeerStatusException.SERVICE_UNAVAILABLE, "Servicio apilayer no disponible: "+clientApiLayerEntity.getError().toString(), "/beers/"+beerID+"/boxprice");
        }
        beerBox = new BeerBox();
        Map<String, Object> quotes = clientApiLayerEntity.getQuotes();
        double changeMoney = (Double) quotes.get(clientApiLayerEntity.getSource() + beer.getCurrency());
        double priceTotal = changeMoney * beerBox.getNumberPackage().doubleValue() * beer.getPrice().doubleValue();
        beerBox = new BeerBox();
        beerBox.setPriceTotal(priceTotal);
        beerBox.setBeer(Collections.singleton(beer));
        beerBox.setName(beer.getName());
        return beerBox;
    }


}
