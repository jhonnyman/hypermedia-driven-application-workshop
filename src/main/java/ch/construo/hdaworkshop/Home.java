package ch.construo.hdaworkshop;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
public class Home extends BaseView {

    @Location("home.html")
    Template homeTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance hello() {
        return homeTemplate.instance();
    }
}
