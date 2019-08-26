package microservices.beers.service;

import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.entity.Beer;
import microservices.beers.entity.BeerBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/*
* TODO: mismos comentarios que en beer repo test:
*  - Escribir tests para escenarios negativos
*  - Escribir tests para escenarios de negocio como cuando hay un codigo de moneda invalido
 */

@MicronautTest
class BeerServiceTest {

    @Inject
    BeerService beerService;


    @Test
    void addBeers() throws Exception {


        Beer beer = new Beer();
        //TODO: remover variables no utilizadas
        Beer newBeer = new Beer();
        beer.setId(120);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

        /*Ingresa una nueva cerveza*/
        Assertions.assertEquals(beer, beerService.addBeers(beer));

        /*Ingresa cerveza existente!!!*/
        Assertions.assertEquals(null, beerService.addBeers(beer));

    }


    @Test
    void searchBeers() throws Exception {

        /*Listar todas las cervezas ()*/
        List<Beer> searchBeers = null;
        Beer beer = new Beer();
        beer.setId(2);
        beer.setName("Doppelbock");
        beer.setBrewery("Kunstmann");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(7.5);
        beerService.addBeers(beer); //Se agrega cerveza

        searchBeers = new ArrayList<>();

        /*Se valida que no venga la lista vacia*/
        Assertions.assertNotEquals(searchBeers,
                beerService.searchBeers()

        );

    }

    @Test
    void searchBeerById() throws Exception {


        /*Buscar una cerveza que no existe*/

        Beer searchBeerById = null;
        //TODO: remover variables no utilizadas
        searchBeerById = new Beer();
        searchBeerById = beerService.searchBeerById(888);

        Assertions.assertEquals(null,
                searchBeerById
        );

        /*Buscar una cerveza que existe*/
        Beer beer = new Beer();
        beer.setId(989);
        beer.setName("Foreign Extra Stout");
        beer.setBrewery("Cuello Negro");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(6.5);
        beerService.addBeers(beer); //Se agrega cerveza previamente

        searchBeerById = beerService.searchBeerById(beer.getId());
        Assertions.assertEquals(
                beer,
                searchBeerById
        );

    }

    @Test
    void boxBeerPriceById() throws Exception {

        /*Buscar una cerveza que no existe*/
        BeerBox beerBox = null;
        beerBox = beerService.boxBeerPriceById(600);
        Assertions.assertEquals(
                null, beerBox
        );


        /*Buscar una cerveza que existe*/
        Beer beer = new Beer();
        beer.setId(4);
        beer.setName("IRA");
        beer.setBrewery("Granizo");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(3.5);
        beerService.addBeers(beer); //Se agrega cerveza previamente

        Assertions.assertNotEquals(null,
                beerService.boxBeerPriceById(beer.getId()));


        /*Validar el precio de una caja de cervezas*/
        Number precioTotal = 18.94263;
        Assertions.assertEquals(precioTotal,
                beerService.boxBeerPriceById(beer.getId()).getPriceTotal());

    }

}