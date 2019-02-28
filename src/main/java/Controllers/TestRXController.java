package Controllers;

import Models.Car;
import Repository.CarRepo;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/car")
public class TestRXController {
    @EJB
    CarRepo carRepo;

    public TestRXController(){

    }

    @GET
    public String wtf(){
        return "What the flag";
    }

    @GET
    @Path("/get/{id}")
    public Car getCar(@PathParam("id") long id){
        return carRepo.find(id);
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public void createCar(Car car){
        carRepo.create(car);
    }
}
