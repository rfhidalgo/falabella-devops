package microservices.beers.service;

import microservices.beers.BeerServiceException;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;

import java.util.List;

public interface BeerService {

    //TODO: Definir excepciones especificas en lugar de un exception generico
    Beer addBeers(Beer beer) throws Exception;
    List<Beer> searchBeers() throws Exception;
    Beer searchBeerById(Integer beerID) throws BeerServiceException;
    BeerBox boxBeerPriceById(Integer beerID) throws Exception;


}
