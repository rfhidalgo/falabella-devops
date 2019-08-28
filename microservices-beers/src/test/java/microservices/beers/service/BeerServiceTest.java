package microservices.beers.service;

import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.client.HttpClientApiLayerEntity;
import microservices.beers.client.HttpClientImpl;
import microservices.beers.entity.Beer;
import microservices.beers.exeption.BeerAlreadyExistsException;
import microservices.beers.exeption.BeerApiLayerNotAvailableException;
import microservices.beers.exeption.BeerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


/*
 *  Casos de Test:
 *   shouldAddBeers - Esperar un Objeto Beer la Cerveza creada
 *   shouldAddBeersAlreadyExists - Esperar una exepcion BeerAlreadyExistsException(409) con el mensaje: "El ID de la cerveza ya existe:{N°ID}"
 *   shouldSearchBeersPopulate - Esperar una lista<Beer> de las cervezas agregadas anteriormente
 *   shouldSearchBeerByIdNotExist - Esperar una exepción BeerNotFoundException(404) con el mensaje: "El Id de la cerveza no existe:{N°ID}"
 *   shouldSearchBeerByIExist - Esperar un objeto Beer con la cerveza buscada
 *   shouldBoxBeerPriceByIddNotExist - Esperar una exepción BeerNotFoundException(404) con el mensaje: "El Id de la cerveza no existe:{N°ID}"
 *   shouldBoxBeerPriceByExist - Esperar un objeto BeerBox con el precio de la caja de la cerveza consultada
 *   shouldBoxBeerPriceByExistApiNoAvailable - Esperar una BeerApiLayerNotAvailableException(503)  con el mensaje: Servicio apilayer no disponible https://currencylayer.com:{error:{code:info}}
 */


@MicronautTest
class BeerServiceTest {

    @Inject
    BeerService beerService;
    @Inject
    HttpClientImpl httpClientImpl;


    @Test
    void shouldAddBeers() {
        Beer newBeer = new Beer();
        newBeer.setId(130);
        newBeer.setName("Golden");
        newBeer.setBrewery("Kross");
        newBeer.setCountry("Chile");
        newBeer.setCurrency("EUR");
        newBeer.setPrice(15.5);
        assertEquals(newBeer, beerService.addBeers(newBeer));

    }

    @Test
    void shouldAddBeersAlreadyExists() {
        Beer newBeer = new Beer();
        newBeer.setId(128);
        newBeer.setName("Golden");
        newBeer.setBrewery("Kross");
        newBeer.setCountry("Chile");
        newBeer.setCurrency("EUR");
        newBeer.setPrice(15.5);
        beerService.addBeers(newBeer);
        BeerAlreadyExistsException beerAlreadyExistsException = assertThrows(BeerAlreadyExistsException.class, () -> beerService.addBeers(newBeer));
        Assertions.assertEquals("El ID de la cerveza ya existe:" + newBeer.getId(), beerAlreadyExistsException.getMessage());
    }

    @Test
    void shouldSearchBeersPopulate() {

        Beer newBeer = new Beer();
        newBeer.setId(129);
        newBeer.setName("Golden");
        newBeer.setBrewery("Kross");
        newBeer.setCountry("Chile");
        newBeer.setCurrency("EUR");
        newBeer.setPrice(15.5);
        beerService.addBeers(newBeer);
        Assertions.assertNotEquals(new ArrayList<>(),
                beerService.searchBeers()
        );
    }

    @Test
    void shouldSearchBeerByIdNotExist() {

        BeerNotFoundException beerNotFoundException = assertThrows(BeerNotFoundException.class, () -> beerService.searchBeerById(2000));
        assertEquals("El Id de la cerveza no existe:" + 2000, beerNotFoundException.getMessage());

    }

    @Test
    void shouldSearchBeerByIExist() {


        Beer newBeer = new Beer();
        newBeer.setId(130);
        newBeer.setName("Golden");
        newBeer.setBrewery("Kross");
        newBeer.setCountry("Chile");
        newBeer.setCurrency("EUR");
        newBeer.setPrice(15.5);
        beerService.addBeers(newBeer);
        assertEquals(newBeer, beerService.searchBeerById(newBeer.getId()));
    }

    @Test
    void shouldBoxBeerPriceByIddNotExist()  {

       BeerNotFoundException beerNotFoundException = assertThrows(BeerNotFoundException.class, () -> beerService.boxBeerPriceById(2000));
       assertEquals("El Id de la cerveza no existe:" + 2000, beerNotFoundException.getMessage());
    }


    @Test
    void shouldBoxBeerPriceByExist()  {


        Beer beer = new Beer();
        beer.setId(4);
        beer.setName("IRA");
        beer.setBrewery("Granizo");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);
        beerService.addBeers(beer); //Se agrega cerveza previamente

        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesByCurrencies("EUR");
        if (apiLayerEntity.isSuccess()) {
            Assertions.assertNotNull(beerService.boxBeerPriceById(beer.getId()));
            Assertions.assertNotEquals(null,
                    beerService.boxBeerPriceById(beer.getId()).getPriceTotal());
        }

    }


    @Test
    void shouldBoxBeerPriceByExistApiNoAvailable() {

        //Nota:
        // Para habilitar esta Prueba se debe modificar el valor del atributo ACCESS_KEY en la clase HttpClientConfiguration con el valor: 804f092ad90a19a6c76968d6ab8b4bb3

        Beer beer = new Beer();
        beer.setId(4);
        beer.setName("IRA");
        beer.setBrewery("Granizo");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);
        beerService.addBeers(beer); //Se agrega cerveza previamente

        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesByCurrencies("EUR");
        if (apiLayerEntity.isSuccess()==false) {

            BeerApiLayerNotAvailableException beerApiLayerNotAvailableException = assertThrows(BeerApiLayerNotAvailableException.class, () -> beerService.boxBeerPriceById(4));
            assertEquals("Servicio apilayer no disponible https://currencylayer.com: {code=104, info=Your monthly usage limit has been reached. Please upgrade your Subscription Plan.}", beerApiLayerNotAvailableException.getMessage());
        }
    }

}