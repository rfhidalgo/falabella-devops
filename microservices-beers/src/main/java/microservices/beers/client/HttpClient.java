package microservices.beers.client;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client(HttpClientConfiguration.BASE_URL)
public interface HttpClient {

    @Get
    HttpClientApiLayerEntity getCuotesByCurrencies(String currencies);

    @Get
    HttpClientApiLayerEntity getCuotesFromCurrenciesSource();

    @Get
    HttpClientApiLayerEntity getCurrencyConversionFromTo(String currencies,Integer amount);

}