package microservices.beers.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.validation.Validated;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import microservices.beers.service.BeerService;

import javax.inject.Singleton;
import java.net.URI;
import java.util.List;

@Validated
@Controller("/beers")
@Singleton
public class BeerEndpoint {

    private final BeerService beerService;
    public BeerEndpoint(BeerService beerService) {
        this.beerService = beerService;
    }


    /*Ingresa una nueva cerveza*/
    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Beer> addBeers(Beer beer) {
        Beer beerResponse = null;
        beerResponse = beerService.addBeers(beer);
         return HttpResponse
                    .created(beerResponse)
                    .headers(headers -> headers.location(location(beer.getId())));
    }


    /*Lista todas las cervezas*/
    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Beer> searchBeers()  {
        return beerService.searchBeers();
    }


    /*Lista el detalle de la marca de cervezas*/
    @Get("/{beerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<Beer> searchBeerById(Integer beerID) {
        Beer beerResponse = null;
        beerResponse = beerService.searchBeerById(beerID);
        return HttpResponse
                .ok(beerResponse)
                .headers(headers -> headers.location(location(beerID)));
    }



    /*Lista el precio de una caja de cervezas de una marca*/
    @Get("/{beerID}/boxprice")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<BeerBox> boxBeerPriceById(Integer beerID) {
        BeerBox beerBoxResponse = null;
        beerBoxResponse = beerService.boxBeerPriceById(beerID);
        return HttpResponse
                    .ok(beerBoxResponse)
                    .headers(headers -> headers.location(location(beerID)));

    }

    protected URI location(Integer id) {
        return URI.create("/beers/" + id);
    }

}
