package ch.construo.hdaworkshop;

import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
public class BaseView {

    @Location("errors/404.html")
    Template notFound;

    @Location("errors/500.html")
    Template serverError;

    @ServerExceptionMapper(NotFoundException.class)
    @Priority(Priorities.USER)
    public Response handleNotFoundException() {
        return Response.ok(notFound.instance()).header("Content-Type", MediaType.TEXT_HTML).build();
    }

    @ServerExceptionMapper
    @Priority(Priorities.USER)
    public Response handleUncaughtExceptions(Exception e) {
        return Response.ok(serverError.instance().data("error", e))
                .header("Content-Type", MediaType.TEXT_HTML).build();
    }
}
