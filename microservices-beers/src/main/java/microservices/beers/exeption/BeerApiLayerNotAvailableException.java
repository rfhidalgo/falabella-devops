package microservices.beers.exeption;

public class BeerApiLayerNotAvailableException extends BeerServiceException {

    final String detailMessage;

    @Override
    public String getMessage() {
        return this.detailMessage;
    }

    public BeerApiLayerNotAvailableException(String detailMessage) {
        super();
        this.detailMessage=detailMessage;
    }
}
