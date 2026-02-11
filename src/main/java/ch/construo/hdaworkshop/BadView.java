package ch.construo.hdaworkshop;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/bad")
public class BadView extends BaseView {

    @GET
    public Response throwError() {
        throw new RuntimeException("This View is broken");
    }
}
