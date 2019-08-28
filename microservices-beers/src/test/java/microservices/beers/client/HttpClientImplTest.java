package microservices.beers.client;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;


/*
 *  Casos de Test:
 *   shouldGetCuotesByCurrencies - Esperar un Objeto HttpClientApiLayerEntity con la tasa de cambio de USD a EUR
 *   shouldGetCuotesFromCurrenciesSource - Esperar un Objeto HttpClientApiLayerEntity con la tasa de cambio de USD a EUR
 *   shouldgetCurrencyConversionFromTo- Esperar un Objeto HttpClientApiLayerEntity con la tasa de cambio de USD a EUR
 *
 */

@MicronautTest
class HttpClientImplTest {

    @Inject
    HttpClientImpl httpClientImpl;


    @Test
    void shouldGetCuotesByCurrencies() {

     HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesByCurrencies("EUR"); //Funcion Currencies es Gratis, por defecto source USD
     double moneyExpected = 0.901598; //Actualizar Tasa de cambio USDEUR: http://apilayer.net/api/live?access_key=804f092ad90a19a6c76968d6ab8b4bb3&currencies=EUR

         if (apiLayerEntity.isSuccess()) {
          Map<String, Object> quotes = apiLayerEntity.getQuotes();
          double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");
            Assertions.assertEquals(
                    moneyExpected,changeMoney
            );
       }
    }



    @Test
    void shouldGetCuotesFromCurrenciesSource() {


        String source = "USD";
        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesFromCurrenciesSource(source);// Funcion source es limitada, gratis solo source USD, se debe pagar API para elegir otro source o moneda de origen
        double moneyExpected = 0.901598; //Actualizar Tasa de cambio USDEUR: http://apilayer.net/api/live?access_key=804f092ad90a19a6c76968d6ab8b4bb3&source=USD

        if (apiLayerEntity.isSuccess()) {

            Map<String, Object> quotes = apiLayerEntity.getQuotes();
            double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");

            Assertions.assertEquals(
                    moneyExpected,changeMoney
            );
        }
    }



    @Test
    void shouldgetCurrencyConversionFromTo() {

        String currencies = "EUR";
        Integer amount = 10;
        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCurrencyConversionFromTo(currencies, amount); //Funcion convertir se debe pagar la API

        double moneyExpected = 0.901598; //Actualizar valor de conversion USD TO EUR Amount 10 ?: http://apilayer.net/api/convert?access_key=804f092ad90a19a6c76968d6ab8b4bb3&from=USD&to=EUR&amount=10

        if (apiLayerEntity.isSuccess()) {

            Map<String, Object> quotes = apiLayerEntity.getQuotes();
           double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");

            Assertions.assertEquals(
                             moneyExpected,changeMoney
            );
        }

    }
}