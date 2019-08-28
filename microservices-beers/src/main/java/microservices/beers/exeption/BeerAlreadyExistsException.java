package microservices.beers.exeption;

public class BeerAlreadyExistsException extends BeerServiceException {

   final String detailMessage;

   @Override
    public String getMessage() {
        return this.detailMessage;
    }

    public BeerAlreadyExistsException(String detailMessage) {
        super();
        this.detailMessage=detailMessage;
    }
}
