package ch.construo.hdaworkshop;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/wc")
public class WebComponents extends BaseView {

    @Location("webcomponents.html")
    Template webComponentsTemplate;

    @GET
    @jakarta.ws.rs.Produces(MediaType.TEXT_HTML)
    public TemplateInstance home() {
        return webComponentsTemplate.instance();
    }
}
