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
@Requires(classes = {BeerNotFoundException.class, ExceptionHandler.class})
public class BeerNotFoundExceptionHandler implements ExceptionHandler<BeerNotFoundException, HttpResponse> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    @Status(HttpStatus.NOT_FOUND)
    public HttpResponse handle(HttpRequest request, BeerNotFoundException exception) {
        return HttpResponse
                .notFound(exception.getMessage())
                .headers(headers -> headers.location(location(exception.getIdBeer())));
    }

    protected URI location(Integer id) {
        return URI.create("/beers/" + id);
    }
}