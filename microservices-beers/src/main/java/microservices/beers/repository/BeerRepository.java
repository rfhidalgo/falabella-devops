package microservices.beers.repository;

import microservices.beers.BeerServiceException;
import microservices.beers.entity.Beer;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface BeerRepository {

    Beer addBeers(@NotNull Beer beer) throws Exception;

    List<Beer> searchBeers(SortingAndOrderArguments args) throws Exception;

    List<Beer> searchBeers() throws Exception;

    Beer searchBeerById(Integer beerID) throws BeerServiceException;

}
