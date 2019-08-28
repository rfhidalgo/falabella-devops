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
@Requires(classes = {BeerAlreadyExistsException.class, ExceptionHandler.class})
public class BeerAlreadyExistsExceptionHandler implements ExceptionHandler<BeerAlreadyExistsException, HttpResponse> {


   @Override
   @Produces(MediaType.APPLICATION_JSON)
   @Status(HttpStatus.CONFLICT)
    public HttpResponse handle(HttpRequest request, BeerAlreadyExistsException exception) {
        return HttpResponse
                .ok(exception.getMessage())
                .status(HttpStatus.CONFLICT)
                .headers(headers -> headers.location(location()));
    }

    protected URI location() {
        return URI.create("/beers");
    }
}