package microservices.beers.exeption;


public class BeerException extends RuntimeException {

    final String detailMessage;
    final String location;
    final BeerStatusException statusException;

    @Override
    public String getMessage() {
        return this.detailMessage;
    }

    public String getLocation() {
        return location;
    }

    public BeerException(BeerStatusException statusException, String detailMessage, String location) {
        super();
        this.detailMessage=detailMessage;
        this.location = location;
        this.statusException=statusException;
    }
}
