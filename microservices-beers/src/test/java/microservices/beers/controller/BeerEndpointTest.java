package microservices.beers.controller;


import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.BeerServiceException;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/*
* TODO: Los tests del controlador deben ser mas "amigables" con lenguaje de negocio
*  Ejemplos:
*   shouldFindBeerByValidBeerId - encontrar una cerveza por id valid
*   shouldReturnNotFoundForInvalidBeerIdSearch - not found para cerveza no encontrada
*   shouldReturn500InUnexpectedError - Esperar un error 500 en caso de error no controlado
*   etc...
 */

@MicronautTest
class BeerEndpointTest {

    @Inject
    ApplicationContext contex;

    @Inject
    EmbeddedServer server;
    BeerEndpoint beerEndpoint;


    @Inject
    @Client("/beers/")
    RxHttpClient client;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addBeers() {

        Beer beer = new Beer();
        beer.setId(201);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class);

        System.out.println("Body addBeers: " + bodyResponseAddBeers);

        /*assertNotNull(bodyResponseAddBeers);
        assertEquals(beer.getId(), bodyResponseAddBeers.getId());
        assertEquals(beer.getName(), bodyResponseAddBeers.getName());
        assertEquals(beer.getBrewery(), bodyResponseAddBeers.getBrewery());
        assertEquals(beer.getCurrency(), bodyResponseAddBeers.getCurrency());
        assertEquals(beer.getPrice(), bodyResponseAddBeers.getPrice());
        assertEquals(beer.getCountry(), bodyResponseAddBeers.getCountry());*/


    }

    @Test
    void searchBeers() {


        Beer beer = new Beer();
        beer.setId(100);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza

        System.out.println("Body searchBeers Add: " + bodyResponseAddBeers);

        request = HttpRequest.GET("/");
        List<Beer> bodyResponseSearchBeers = (List<Beer>) client.toBlocking().retrieve(request, ArrayList.class); //Se invoca por segunda vez

        System.out.println("Body searchBeers Get: " + bodyResponseSearchBeers);

        /*Assertions.assertNotEquals(new ArrayList<>(), bodyResponseSearchBeers); //Se valida que no sea una lista vacia*/
    }

    @Test
    void searchBeerById() throws BeerServiceException {


        Beer beer = new Beer();
        beer.setId(21);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza

        System.out.println("Body searchBeerById Add: " + bodyResponseAddBeers);


        request = HttpRequest.GET("/" + beer.getId());
        Beer searchBeerById = (Beer) client.toBlocking().retrieve(request, Beer.class);

        System.out.println("Body searchBeerById: " + searchBeerById);

       /* assertNotNull(searchBeerById);
        assertEquals(beer.getId(), searchBeerById.getId());
        assertEquals(beer.getName(), searchBeerById.getName());
        assertEquals(beer.getBrewery(), searchBeerById.getBrewery());
        assertEquals(beer.getCurrency(), searchBeerById.getCurrency());
        assertEquals(beer.getPrice(), searchBeerById.getPrice());
        assertEquals(beer.getCountry(), searchBeerById.getCountry());*/


    }

    @Test
    void boxBeerPriceById() {


        Beer beer = new Beer();
        beer.setId(501);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza

        System.out.println("Body boxBeerPriceById Add: " + bodyResponseAddBeers);


        request = HttpRequest.GET("/" + beer.getId() + "/boxprice");
        BeerBox boxBeerPriceById = (BeerBox) client.toBlocking().retrieve(request, BeerBox.class);


        System.out.println("Body boxBeerPriceById: " + boxBeerPriceById);

        /*Validar el precio de una caja de cervezas*/
        Number precioTotal = 18.94263;

       /* assertNotNull(boxBeerPriceById);*/
        /*Assertions.assertEquals(precioTotal,
                boxBeerPriceById.getPriceTotal());*/


    }


}