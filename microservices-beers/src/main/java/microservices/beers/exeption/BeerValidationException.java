package microservices.beers.exeption;

public class BeerValidationException extends BeerServiceException {

    final String detailMessage;

    @Override
    public String getMessage() {
        return this.detailMessage;
    }

    public BeerValidationException(String detailMessage) {
        super();
        this.detailMessage=detailMessage;

    }
}
