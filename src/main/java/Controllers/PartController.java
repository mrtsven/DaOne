package Controllers;

import Configuration.JwtTokenUtil;
import Models.Car.Car;
import Models.Part.Part;
import Models.Part.PartDTO;
import Repository.CarRepo;
import Repository.PartRepo;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/part")
public class PartController {
    @EJB
    private PartRepo partRepo;
    @EJB
    private CarRepo carRepo;
    JwtTokenUtil jwt = new JwtTokenUtil();

    public PartController(){};

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPart(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("part", partRepo.find(id).toJsonCustom());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/part/partId")));
        return Response.ok(response.toString(2)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParts()
    {
        JSONObject response = new JSONObject();
        response.put("parts", partRepo.findAll());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/part")));

        return Response.ok(response.toString(2)).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPart(PartDTO partDTO){
        Part part = new Part(partDTO);
        List<Part> list = new ArrayList<>();
        list.add(part);

        Car foundCar =  carRepo.find((long) 1);
        foundCar.setParts(list);

        part = partRepo.create(part);
        carRepo.update(foundCar);



        JSONObject response = new JSONObject();
        response.put("part", part.toJsonCustom());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/part")));

        return Response.ok(response.toString(2)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id){
        JSONObject response = new JSONObject();


        response.put("id", id);
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/part/partId")));

        return Response.ok(response.toString(2)).build();
    }

    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();

        links.put("self", self);
        links.put("save", URI.create("http://localhost:8080/DaOne/api/part/create"));
        links.put("delete", URI.create("http://localhost:8080/DaOne/api/part/partId"));
        links.put("get", URI.create("http://localhost:8080/DaOne/api/part/partId"));
        links.put("get all", URI.create("http://localhost:8080/DaOne/api/part"));

        return links;
    }
}
