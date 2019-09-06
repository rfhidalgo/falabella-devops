package microservices.beers.exeption;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.net.URI;

@Produces
@Singleton
@Requires(classes = {BeerException.class, ExceptionHandler.class})
public class BeerExceptionHandler implements ExceptionHandler<BeerException, HttpResponse> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
     public HttpResponse handle(HttpRequest request, BeerException exception) {
        HttpStatus httpStatus;
        switch(exception.statusException) {
            case CONFLICT:
                httpStatus=HttpStatus.CONFLICT;
                break;
            case SERVICE_UNAVAILABLE:
                httpStatus=HttpStatus.SERVICE_UNAVAILABLE;
                break;
            case NOT_FOUND:
                httpStatus=HttpStatus.NOT_FOUND;
                break;
            case OK:
                httpStatus=HttpStatus.OK;
                break;
            default:
                httpStatus=HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }
       return HttpResponse
                .status(httpStatus)
                .body(exception.detailMessage)
                .headers(headers -> headers.location(location(exception.location)));
    }

    protected URI location(String location) {
        return URI.create(location);
    }
}