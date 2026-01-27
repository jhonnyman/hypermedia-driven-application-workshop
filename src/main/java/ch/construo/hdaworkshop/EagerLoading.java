package ch.construo.hdaworkshop;

import ch.construo.hdaworkshop.repositories.Database;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/eager")
public class EagerLoading extends BaseView {

    @Inject
    Database database;

    @Location("eager.html")
    Template eagerTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getData() {
        return eagerTemplate.instance().data("cedents", database.listAllCedents()).data("claims",
                database.listAllClaims());
    }
}
