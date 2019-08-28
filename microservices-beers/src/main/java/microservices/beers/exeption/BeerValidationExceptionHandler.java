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
@Requires(classes = {BeerValidationException.class, ExceptionHandler.class})
public class BeerValidationExceptionHandler implements ExceptionHandler<BeerValidationException, HttpResponse> {

    @Override

   @Produces(MediaType.APPLICATION_JSON)
   @Status(HttpStatus.BAD_REQUEST)
    public HttpResponse handle(HttpRequest request, BeerValidationException exception) {
        return HttpResponse
                .badRequest("Request invalida:" + exception.getMessage())
                .status(HttpStatus.BAD_REQUEST);
    }

    protected URI location() {
        return URI.create("/beers");
    }
}