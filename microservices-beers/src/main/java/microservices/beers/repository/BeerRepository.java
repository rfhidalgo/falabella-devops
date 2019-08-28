package microservices.beers.repository;

import microservices.beers.entity.Beer;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface BeerRepository {

    Beer addBeers(@NotNull Beer beer);

    List<Beer> searchBeers(SortingAndOrderArguments args);

    List<Beer> searchBeers();

    Beer searchBeerById(Integer beerID);

}
