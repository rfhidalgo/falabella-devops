package microservices.beers.exeption;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.annotation.Status;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;
import java.net.URI;

@Produces
@Singleton
@Requires(classes = {BeerServiceException.class, ExceptionHandler.class})
public class BeerServiceExceptionHandler implements ExceptionHandler<BeerServiceException, HttpResponse> {

 @Override
 @Produces(MediaType.APPLICATION_JSON)
 @Status(HttpStatus.INTERNAL_SERVER_ERROR)
    public HttpResponse handle(HttpRequest request, BeerServiceException exception) {
        return HttpResponse
                .serverError()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .headers(headers -> headers.location(location()));
    }

    protected URI location() {
        return URI.create("/beers");
    }
}