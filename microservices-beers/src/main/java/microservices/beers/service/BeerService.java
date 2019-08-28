package microservices.beers.service;

import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;

import java.util.List;

public interface BeerService {


    Beer addBeers(Beer beer);

    List<Beer> searchBeers();

    Beer searchBeerById(Integer beerID);

    BeerBox boxBeerPriceById(Integer beerID);


}
