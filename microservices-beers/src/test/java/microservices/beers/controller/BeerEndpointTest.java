package microservices.beers.controller;


import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.client.HttpClient;
import microservices.beers.client.HttpClientApiLayerEntity;
import microservices.beers.client.HttpClientImpl;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/*
 *  Casos de Test:
 *   shouldAddBeers - Esperar un Create(201) con la Cerveza creada
 *   shouldAddBeersBadRequest - Esperar una excepción HttpClientResponseException con la respuesta "BAD REQUEST" 400
 *   shouldAddBeersAlreadyExists - Esperar una excepción HttpClientResponseException con la respuesta "CONFLICT" 409
 *   shouldSearchBeersPopulate - Esperar un OK(200) con la lista de las cervezas agregadas anteriormente
 *   shouldSearchBeerByIdNotExist - Esperar una excepción HttpClientResponseException con la respuesta "NOT FOUND" 404
 *   shouldSearchBeerByIExist - Esperar un OK(200) con la cerveza buscada
 *   shouldBoxBeerPriceByIddNotExist -  Esperar una excepción HttpClientResponseException con la respuesta "NOT FOUND" 404
 *   shouldBoxBeerPriceByExist - Esperar un OK(200) con el precio de la caja de la cerveza consultada
 *   shouldBoxBeerPriceByExistApiNoAvailable - Esperar una HttpClientResponseException con la respuesta "SERVICE UNAVAILABLE" 503
 */

@MicronautTest
class BeerEndpointTest {

    private static final double precioTotal = 19.015919999999998;//Actualizar segun valor de conversión del dia (3.5 * 6 * USDEUR)

    @Inject
    ApplicationContext contex;

    @Inject
    EmbeddedServer server;
    BeerEndpoint beerEndpoint;

    @Inject
    HttpClient httpClient;


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
    void shouldAddBeers() {

        Beer beer = new Beer();
        beer.setId(221);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class);

        assertNotNull(bodyResponseAddBeers);
        assertEquals(beer.getId(), bodyResponseAddBeers.getId());
        assertEquals(beer.getName(), bodyResponseAddBeers.getName());
        assertEquals(beer.getBrewery(), bodyResponseAddBeers.getBrewery());
        assertEquals(beer.getCurrency(), bodyResponseAddBeers.getCurrency());
        assertEquals(beer.getPrice(), bodyResponseAddBeers.getPrice());
        assertEquals(beer.getCountry(), bodyResponseAddBeers.getCountry());


    }


    @Test
    void shouldAddBeersBadRequest() {

        Beer beer = new Beer();
        beer.setId(229);
        beer.setName("");
        beer.setCountry("");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        HttpClientResponseException httpClientResponseException = Assertions.assertThrows(HttpClientResponseException.class, ()->client.toBlocking().retrieve(request, HttpClientResponseException.class));
        Assertions.assertEquals("BAD REQUEST", httpClientResponseException.getMessage().toUpperCase());



    }


    @Test
    void shouldAddBeersAlreadyExists() {

        Beer beer = new Beer();
        beer.setId(201);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);
        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class);
        request = HttpRequest.POST("/", beer);
        HttpRequest finalRequest = request;
        HttpClientResponseException httpClientResponseException = Assertions.assertThrows(HttpClientResponseException.class, ()->client.toBlocking().retrieve(finalRequest, HttpClientResponseException.class));
        Assertions.assertEquals("CONFLICT", httpClientResponseException.getMessage().toUpperCase());
    }


    @Test
    void shouldSearchBeersPopulate() {


        Beer beer = new Beer();
        beer.setId(100);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);
        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza
        request = HttpRequest.GET("/");
        List<Beer> bodyResponseSearchBeers = (List<Beer>) client.toBlocking().retrieve(request, ArrayList.class); //Se invoca por segunda vez
        Assertions.assertNotEquals(new ArrayList<>(), bodyResponseSearchBeers); //Se valida que no sea una lista vacia*/
    }

    @Test
    void shouldSearchBeerByIdNotExist()  {

        HttpRequest request = HttpRequest.GET("/" + 101);
        HttpClientResponseException httpClientResponseException = Assertions.assertThrows(HttpClientResponseException.class, ()->client.toBlocking().retrieve(request, HttpClientResponseException.class));
        Assertions.assertEquals("NOT FOUND", httpClientResponseException.getMessage().toUpperCase());
    }


    @Test
    void shouldSearchBeerByIExist()  {


        Beer beer = new Beer();
        beer.setId(21);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza
        request = HttpRequest.GET("/" + beer.getId());
        Beer searchBeerById = (Beer) client.toBlocking().retrieve(request, Beer.class);
        assertNotNull(searchBeerById);
        assertEquals(beer.getId(), searchBeerById.getId());
        assertEquals(beer.getName(), searchBeerById.getName());
        assertEquals(beer.getBrewery(), searchBeerById.getBrewery());
        assertEquals(beer.getCurrency(), searchBeerById.getCurrency());
        assertEquals(beer.getPrice(), searchBeerById.getPrice());
        assertEquals(beer.getCountry(), searchBeerById.getCountry());

    }


    @Test
    void shouldBoxBeerPriceByIddNotExist() {

        HttpRequest request = HttpRequest.GET("/" + 101 + "/boxprice");
        HttpClientResponseException httpClientResponseException = Assertions.assertThrows(HttpClientResponseException.class, ()->client.toBlocking().retrieve(request, HttpClientResponseException.class));
        Assertions.assertEquals("NOT FOUND", httpClientResponseException.getMessage().toUpperCase());

    }


    @Test
    void shouldBoxBeerPriceByExist() {


       Beer beer = new Beer();
        beer.setId(501);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);

        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza


        HttpClientApiLayerEntity apiLayerEntity = httpClient.getCuotesByCurrencies("EUR");
        if (apiLayerEntity.isSuccess()) {
            request = HttpRequest.GET("/" + beer.getId() + "/boxprice");
            BeerBox boxBeerPriceById = (BeerBox) client.toBlocking().retrieve(request, BeerBox.class);
            assertNotNull(boxBeerPriceById);
            Assertions.assertEquals(precioTotal,
                    boxBeerPriceById.getPriceTotal());

        }
    }


    @Test
    void shouldBoxBeerPriceByExistApiNoAvailable() {

        //Nota:
        // Para habilitar esta Prueba se debe modificar el valor del atributo ACCESS_KEY en la clase HttpClientConfiguration con el valor: 804f092ad90a19a6c76968d6ab8b4bb3

        Beer beer = new Beer();
        beer.setId(541);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);
        HttpRequest request = HttpRequest.POST("/", beer);
        Beer bodyResponseAddBeers = (Beer) client.toBlocking().retrieve(request, Beer.class); //Se agrega cerveza
        HttpClientApiLayerEntity apiLayerEntity = httpClient.getCuotesByCurrencies("EUR");

        if (!apiLayerEntity.isSuccess()) {
            request = HttpRequest.GET("/" + beer.getId() + "/boxprice");
            HttpRequest finalRequest = request;
            HttpClientResponseException httpClientResponseException = Assertions.assertThrows(HttpClientResponseException.class, ()->client.toBlocking().retrieve(finalRequest, HttpClientResponseException.class));
            Assertions.assertEquals("SERVICE UNAVAILABLE", httpClientResponseException.getMessage().toUpperCase());

        }
    }

}