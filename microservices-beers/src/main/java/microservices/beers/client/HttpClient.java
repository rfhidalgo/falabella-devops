package microservices.beers.client;


public interface HttpClient {

    HttpClientApiLayerEntity getCuotesByCurrencies(String currencies); //Obtiene todas las tasas de cambio para una moneda en particular
    HttpClientApiLayerEntity getCuotesFromCurrenciesSource(String source); //Obtiene todas las tasas de cambio de una moneda de origen en particular
    HttpClientApiLayerEntity getCurrencyConversionFromTo(String currencies,Integer amount);//Obtiene la conversión de una moneda de origen a una moneda destino y en base al monto de conversión
}