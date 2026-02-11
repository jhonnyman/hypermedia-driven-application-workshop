package ch.construo.hdaworkshop;

import java.util.UUID;

import org.jboss.resteasy.reactive.RestForm;

import ch.construo.hdaworkshop.dto.Cedent;
import ch.construo.hdaworkshop.dto.Claim;
import ch.construo.hdaworkshop.dto.ClaimStatus;
import ch.construo.hdaworkshop.repositories.Database;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/lazy")
public class LazyLoading extends BaseView {

    @Inject
    Database database;

    @Location("lazy.html")
    Template lazyTemplate;

    @Location("cedents.html")
    Template cedentsTemplate;

    @Location("claims.html")
    Template claimsTemplate;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getData() {
        return lazyTemplate.instance();
    }

    @Path("/cedents")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getCedents() throws InterruptedException {
        Thread.sleep(2000);
        return cedentsTemplate.instance().data("cedents", database.listAllCedents());
    }

    @Path("/cedents")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance createEagerCedent(@RestForm String name) {
        database.addCedent(new Cedent(name));
        return cedentsTemplate.instance().data("cedents", database.listAllCedents());
    }

    @Path("/cedents/{name}")
    @DELETE
    @Produces(MediaType.TEXT_HTML)
    public Response deleteCedent(@PathParam("name") String name) {
        database.deleteCedent(name);
        return Response.ok().header("HX-Refresh", "true").build();
    }

    @Path("/claims")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getClaims() throws InterruptedException {
        Thread.sleep(2000);
        return claimsTemplate.instance().data("claims", database.listAllClaims());
    }

    @Path("/claims/{id}")
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deleteCedent(@PathParam("id") UUID claimId,
            @RestForm ClaimStatus status) {
        var persistedClaim = database.getClaim(claimId);
        persistedClaim.ifPresent(claim -> database.updateClaim(
                new Claim(claim.id(), claim.cause(), claim.cedent(), claim.amount(), status)));
        persistedClaim.orElseThrow(() -> new NotFoundException("Claim does not exist"));

        return claimsTemplate.instance().data("claims", database.listAllClaims());
    }

    @Path("/reset")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance resetData() {
        database.generateDummyData();

        return lazyTemplate.instance();
    }
}
