package Controllers;

import Models.Car;
import Repository.CarRepo;

import javax.ejb.EJB;
import javax.ws.rs.*;

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
}
