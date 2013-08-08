import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("/hello")
public class HelloService {
   @GET
   @Path("/{name}")
   public Response getText(@PathParam("name") String name) {
      String output = "Hello " + name + "\n";
      return Response.status(200).entity(output).build();
   }
}