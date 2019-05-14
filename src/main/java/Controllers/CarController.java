package Controllers;

import Configuration.JwtTokenUtil;
import Models.Car.Car;
import Models.Car.CarDTO;
import Repository.CarRepo;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Path("/car")
public class CarController {
    @EJB
    CarRepo carRepo;
    JwtTokenUtil jwt = new JwtTokenUtil();

    public CarController(){
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") long id)
    {
        JSONObject response = new JSONObject();
        response.put("car", carRepo.find(id).toJsonCustom());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/car/carId")));
        return Response.ok(response.toString(2)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars()
    {
        JSONObject response = new JSONObject();
        response.put("cars", carRepo.findAll());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/car")));

        return Response.ok(response.toString(2)).build();
    }


    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(CarDTO carDTO){
        Car car = new Car(carDTO);
        car = carRepo.create(car);

        JSONObject response = new JSONObject();
        response.put("car", car.toJsonCustom());
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/create")));

        return Response.ok(response.toString(2)).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id){
        JSONObject response = new JSONObject();

        carRepo.delete(id);

        response.put("id", id);
        response.put("_links", getLinks(URI.create("http://localhost:8080/DaOne/api/car/carId")));

        return Response.ok(response.toString(2)).build();
    }

    private Map<String, URI> getLinks(URI self)
    {
        Map<String, URI> links = new HashMap<>();

        links.put("self", self);
        links.put("save", URI.create("http://localhost:8080/DaOne/api/car/userId/create"));
        links.put("delete", URI.create("http://localhost:8080/DaOne/api/car/carId"));
        links.put("get", URI.create("http://localhost:8080/DaOne/api/car/carId"));
        links.put("get all", URI.create("http://localhost:8080/DaOne/api/car"));

        return links;
    }
}
