package microservices.beers.client;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.uri.UriTemplate;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class HttpClientImpl implements HttpClient{


    private final RxHttpClient httpClient;
    private final String uri;

    public HttpClientImpl(@Client(HttpClientConfiguration.BASE_URL) RxHttpClient httpClient,
                          HttpClientConfiguration configuration) {
        this.httpClient = httpClient;
        String path = HttpClientConfiguration.ENDPOINT + "?access_key=" + HttpClientConfiguration.ACCESS_KEY;
        uri = UriTemplate.of(path).expand(configuration.toMap());
    }

   @Override
   public HttpClientApiLayerEntity getCuotesByCurrencies(String currencies) { //Obtiene todas las tasas de cambio para una moneda en particular
        HttpRequest<?> req = HttpRequest.GET(uri+"&currencies="+currencies);
        return (HttpClientApiLayerEntity) httpClient.retrieve(req, Argument.of(List.class, HttpClientApiLayerEntity.class)).blockingSingle().get(0);
    }


    @Override
    public HttpClientApiLayerEntity  getCuotesFromCurrenciesSource(String source) { //Obtiene todas las tasas de cambio de una moneda de origen en particular
        HttpRequest<?> req = HttpRequest.GET(uri+"&source="+source);
        return (HttpClientApiLayerEntity) httpClient.retrieve(req, Argument.of(List.class, HttpClientApiLayerEntity.class)).blockingSingle().get(0);

    }

    @Override
    public HttpClientApiLayerEntity getCurrencyConversionFromTo(String currencies,Integer amount) { //Obtiene la conversión de una moneda de origen a una moneda destino y en base al monto de conversión
        HttpRequest<?> req = HttpRequest.GET(uri+"&from"+HttpClientConfiguration.MONEY_SOURCE +"&to="+currencies+"&amount="+amount);
        return(HttpClientApiLayerEntity) httpClient.retrieve(req, Argument.of(List.class, HttpClientApiLayerEntity.class)).blockingSingle().get(0);

    }
}