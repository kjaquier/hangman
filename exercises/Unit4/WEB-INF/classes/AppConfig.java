import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@ApplicationPath("/rest")
public class AppConfig extends Application {
   public Set<Class<?>> getClasses() {
      return new java.util.HashSet<Class<?>>(Arrays.asList(HelloService.class));
   }
}