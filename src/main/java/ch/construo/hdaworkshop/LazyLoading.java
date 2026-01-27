package ch.construo.hdaworkshop;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/lazy")
public class LazyLoading extends BaseView {

    @Location("lazy.html")
    Template eagerTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getData() {
        return eagerTemplate.instance();
    }
}
