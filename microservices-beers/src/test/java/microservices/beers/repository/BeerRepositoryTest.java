package microservices.beers.repository;

import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.entity.Beer;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/*
* TODO: Agregar tests que validen la logica del componente, estos agregan mas valor.
* EJ: Agregar un test que verifique que se lanzo una excepcion si no se encontraron cervezas
* para un criterio de busqueda especifico
* Validar tambien casos negativos, en caso de errores como inserts o updates fallidos.
 */

@MicronautTest
class BeerRepositoryTest {

    @Inject
    BeerRepository beerRepository;

    @Test
    void addBeers() throws Exception {

        /*Ingresa una nueva cerveza*/

        Beer beer = new Beer();
        //TODO: remover variables no utilizadas
        Beer newBeer = new Beer();
        beer.setId(300);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);

       /* Assertions.assertEquals(beer, beerRepository.addBeers(beer));*/


    }

    @Test
    void searchBeers() throws Exception {

        /*Listar todas las cervezas ()*/
        List<Beer> searchBeers = null;
        Beer beer = new Beer();
        beer.setId(900);
        beer.setName("Doppelbock");
        beer.setBrewery("Kunstmann");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(7.5);
        beerRepository.addBeers(beer); //Se agrega cerveza

        searchBeers = new ArrayList<>();

        /*Se valida que no venga la lista vacia*/
      /* Assertions.assertNotEquals(searchBeers,
                beerRepository.searchBeers(null)

        );*/


    }

    @Test
    void searchBeerById() throws Exception {


        /*Buscar una cerveza que no existe*/

        Beer searchBeerById = null;
        //TODO: remover asignacion no utilizada
        searchBeerById = new Beer();
       // searchBeerById = beerRepository.searchBeerById(212);

        /*Assertions.assertEquals(null,
                searchBeerById
        );*/

        /*Buscar una cerveza que existe*/
        Beer beer = new Beer();
        beer.setId(333);
        beer.setName("Foreign Extra Stout");
        beer.setBrewery("Cuello Negro");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(6.5);
        //beerRepository.addBeers(beer); //Se agrega cerveza previamente

       // searchBeerById = beerRepository.searchBeerById(beer.getId());
        /*Assertions.assertEquals(
                beer,
                searchBeerById
        );*/

    }
}