package microservices.beers.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Produces;
import io.micronaut.validation.Validated;
import microservices.beers.BeerNotFoundException;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import microservices.beers.service.BeerService;

import javax.inject.Singleton;
import java.net.URI;
import java.util.List;
@Validated
@Controller("/beers")
@Singleton


public class BeerEndpoint{

    private final BeerService beerService;


    public BeerEndpoint(BeerService beerService) {
        this.beerService = beerService;
    }


    /*Ingresa una nueva cerveza*/
    @Post(produces = MediaType.APPLICATION_JSON)
    public HttpResponse<Beer> addBeers(Beer beer) throws Exception
    {
        Beer beerResponse = null;

        beerResponse = beerService.addBeers(beer);


        /*
        *  TODO: beerService debiese lanzar alguna exception en caso de error
        *  No es una buena practica retornar valores nulos en caso de error.
        *  Otra cosa a mejorar seria especificar excepciones para cada caso, por
        *  ejemplo si beerService encuentra que el beer de entrada es invalido
        *  lanzar una excepcion que pueda ser manejada por el controlador y retornar
        *  un Bad request, por otro lado en caso de un error no manejado podemos lanzar
        *  un error 500, en caso de que la cerveza ya exista lanzar un exists (302)
         */



        if(beerResponse!=null) {
            return HttpResponse
                    .created(beerResponse)
                    .headers(headers -> headers.location(location(beer.getId())));
        }
        else if(beerResponse==null)
        {
            beerResponse = new Beer();
            beerResponse.setName("El ID de la cerveza ya existe");
            return HttpResponse
                    .ok(beerResponse)
                    .headers(headers -> headers.location(location(beer.getId())))
                    .status(HttpStatus.CONFLICT)
                    ;
        }
        else {
            return HttpResponse
                    .badRequest(beer)
                    .headers(headers -> headers.location(location(beer.getId())));

        }

    }


    /*Lista todas las cervezas*/
    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Beer> searchBeers() throws Exception{
            return beerService.searchBeers();
    }

    /*Lista el detalle de la marca de cervezas*/
    @Get("/{beerID}")
    @Produces(MediaType.APPLICATION_JSON)
     public HttpResponse<Beer> searchBeerById(Integer beerID) {
        Beer beerResponse = null;

        try
        {
            beerResponse = beerService.searchBeerById(beerID);
            return HttpResponse
                    .ok(beerResponse)
                    .headers(headers -> headers.location(location(beerID)));
        }
        catch (BeerNotFoundException e)
        {
            beerResponse = new Beer();
            beerResponse.setName("El Id de la cerveza no existe");
            return HttpResponse
                    .notFound(beerResponse)
                    .headers(headers -> headers.location(location(beerID)));
        }


    }

    /*Lista el precio de una caja de cervezas de una marca*/
    @Get("/{beerID}/boxprice")
    @Produces(MediaType.APPLICATION_JSON)
    public HttpResponse<BeerBox> boxBeerPriceById(Integer beerID) throws Exception {
        BeerBox beerBoxResponse = null;
        beerBoxResponse = beerService.boxBeerPriceById(beerID);

        if(beerBoxResponse!=null) {
            return HttpResponse
                    .ok(beerBoxResponse)
                    .headers(headers -> headers.location(location(beerID)));
        }
        else if(beerBoxResponse==null)
        {
            beerBoxResponse = new BeerBox();
            beerBoxResponse.setName("El Id de la cerveza no existe");
            beerBoxResponse.setNumberPackage(null);
            return HttpResponse
                    .notFound(beerBoxResponse)
                    .headers(headers -> headers.location(location(beerID)));
        }
        else
        {
            return HttpResponse
                    .serverError(beerBoxResponse)
                    .headers(headers -> headers.location(location(beerID)));
        }
    }


    protected URI location(Integer id) {
        return URI.create("/beers/" + id);
    }



}
