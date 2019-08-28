package microservices.beers.service;

import io.micronaut.validation.validator.Validator;
import microservices.beers.client.HttpClientApiLayerEntity;
import microservices.beers.client.HttpClientImpl;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import microservices.beers.exeption.BeerApiLayerNotAvailableException;
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
    private final HttpClientImpl httpClientImpl;
    private Validator validator;
    private static final Logger LOG = LoggerFactory.getLogger(BeerServiceImpl.class);

    public BeerServiceImpl(BeerRepository bearRepository, HttpClientImpl httpClientImpl) {
        this.bearRepository = bearRepository;
        this.httpClientImpl = httpClientImpl;

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
        } else {
            throw new BeerApiLayerNotAvailableException("Servicio apilayer no disponible https://currencylayer.com: "+clientApiLayerEntity.getError().toString());
        }

        return beerBox;
    }


}
