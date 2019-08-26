package microservices.beers.client;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Map;


@MicronautTest
class HttpClientImplTest {

    @Inject
    HttpClientImpl httpClientImpl;


    @Test
    void getCuotesByCurrencies() {

        //API Access Key:804f092ad90a19a6c76968d6ab8b4bb3

        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesByCurrencies("EUR"); //Funcion Currencies es Gratis, por defecto source USD

        double moneyExpected = 0.90203; //Actualizar Tasa de cambio USDEUR: http://apilayer.net/api/live?access_key=804f092ad90a19a6c76968d6ab8b4bb3&currencies=EUR

        if (apiLayerEntity.isSuccess()) {

            Map<String, Object> quotes = apiLayerEntity.getQuotes();
            double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");

            Assertions.assertEquals(
                    changeMoney,
                    moneyExpected
            );
        }

    }

    @Test
    void getCuotesFromCurrenciesSource() {

        //API Access Key:804f092ad90a19a6c76968d6ab8b4bb3

        String source = "USD";
        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCuotesFromCurrenciesSource(source);// Funcion source es limitada, gratis solo source USD, se debe pagar API para elegir otro source o moneda de origen

        double moneyExpected = 0.90203; //Actualizar Tasa de cambio USDEUR: http://apilayer.net/api/live?access_key=804f092ad90a19a6c76968d6ab8b4bb3&source=USD

        if (apiLayerEntity.isSuccess()) {

            Map<String, Object> quotes = apiLayerEntity.getQuotes();
            double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");

            Assertions.assertEquals(
                    changeMoney,
                    moneyExpected
            );
        }


    }

    @Test
    void getCurrencyConversionFromTo() {

        //API Access Key:804f092ad90a19a6c76968d6ab8b4bb3

        //TODO: nombres de variables y atributos deben empezar con minusculas
        String Currencies = "EUR";
        Integer Amount = 10;
        HttpClientApiLayerEntity apiLayerEntity = httpClientImpl.getCurrencyConversionFromTo(Currencies, Amount); //Funcion convertir se debe pagar la API

        double moneyExpected = 0.90203; //Actualizar valor de conversion USD TO EUR Amount 10 ?: http://apilayer.net/api/convert?access_key=804f092ad90a19a6c76968d6ab8b4bb3&from=USD&to=EUR&amount=10

        if (apiLayerEntity.isSuccess()) {

            Map<String, Object> quotes = apiLayerEntity.getQuotes();
            double changeMoney = (Double) quotes.get(apiLayerEntity.getSource() + "EUR");

            Assertions.assertEquals(
                    changeMoney,
                    moneyExpected
            );
        }

    }
}