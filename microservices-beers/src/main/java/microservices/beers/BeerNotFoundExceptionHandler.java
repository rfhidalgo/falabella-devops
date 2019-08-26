package microservices.beers;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import javax.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {BeerNotFoundException.class, ExceptionHandler.class})
public class BeerNotFoundExceptionHandler implements ExceptionHandler<BeerNotFoundException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, BeerNotFoundException exception) {
        return HttpResponse.notFound();
    }
}