package microservices.beers.repository;

import io.micronaut.test.annotation.MicronautTest;
import microservices.beers.entity.Beer;
import microservices.beers.exeption.BeerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 *  Casos de Test:
 *   shouldAddBeers - Esperar un Objeto Beer la Cerveza creada
 *   shouldAddBeersAlreadyExists - Esperar una exepcion BeerException(409) con el mensaje: "El ID de la cerveza ya existe:{N°ID}"
 *   shouldSearchBeersPopulate - Esperar una lista<Beer> de las cervezas agregadas anteriormente
 *   shouldSearchBeerByIdNotExist - Esperar una exepción BeerException(404) con el mensaje: "El Id de la cerveza no existe:{N°ID}"
 *   shouldSearchBeerByIExist - Esperar un objeto Beer con la cerveza buscada
 *
 */


@MicronautTest
class BeerRepositoryTest {

    @Inject
    BeerRepository beerRepository;

    @Test
    void shouldAddBeers() {

        Beer beer = new Beer();
        beer.setId(300);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);
        assertEquals(beer, beerRepository.addBeers(beer));
    }


    @Test
    void shouldAddBeersAlreadyExists() {

        Beer beer = new Beer();
        beer.setId(300);
        beer.setName("Golden");
        beer.setBrewery("Kross");
        beer.setCountry("Chile");
        beer.setCurrency("EUR");
        beer.setPrice(15.5);
        assertEquals(beer, beerRepository.addBeers(beer));
        BeerException beerAlreadyExistsException = assertThrows(BeerException.class, () -> beerRepository.addBeers(beer));
    }


    @Test
    void shouldSearchBeersPopulate() {

        List<Beer> searchBeers = null;
        Beer beer = new Beer();
        beer.setId(900);
        beer.setName("Doppelbock");
        beer.setBrewery("Kunstmann");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(7.5);
        beerRepository.addBeers(beer); //Se agrega cerveza
        Assertions.assertNotEquals(new ArrayList<>(),
                beerRepository.searchBeers()
        );
    }

    @Test
    void shouldSearchBeerByIdNotExist() {
        BeerException beerNotFoundException = assertThrows(BeerException.class, () -> beerRepository.searchBeerById(2000));
        assertEquals("El Id de la cerveza no existe:" + 2000, beerNotFoundException.getMessage());
    }


    @Test
    void shouldSearchBeerByIExist() {

        Beer beer = new Beer();
        beer.setId(333);
        beer.setName("Foreign Extra Stout");
        beer.setBrewery("Cuello Negro");
        beer.setCountry("Chile");
        beer.setCurrency("CLP");
        beer.setPrice(6.5);
        beerRepository.addBeers(beer); //Se agrega cerveza previamente
        Assertions.assertEquals(
                beer,
                beerRepository.searchBeerById(beer.getId())
        );

    }
}