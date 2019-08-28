package microservices.beers.exeption;

public class BeerNotFoundException extends BeerServiceException {

    final String detailMessage;
    final Integer idBeer;

    public Integer getIdBeer() {
        return idBeer;
    }

    @Override
    public String getMessage() {
        return this.detailMessage;
    }

    public BeerNotFoundException(String detailMessage, Integer idBeer) {
        super();
        this.detailMessage=detailMessage;
        this.idBeer = idBeer;
    }
}
