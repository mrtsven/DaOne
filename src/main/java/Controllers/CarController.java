package Controllers;

import Models.Car.Car;
import Models.Car.CarDTO;
import Repository.CarRepo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Path("/car")
public class CarController {
    @EJB
    CarRepo carRepo;

    public CarController(){
    }

    @GET
    public String wtf(){
        return "What the flag";
    }

    @GET
    @Path("/get/{id}")
    public Car getCar(@PathParam("id") Long id){
        return carRepo.find(id);
    }

    @GET
    @Path("/get/all")
    public void getAll(@Context HttpServletRequest request,
                       @Context HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cars", carRepo.findAll());
        request.getRequestDispatcher("/carList.jsp")
                .forward(request, response);
    }

    @POST
    @Path("/{userId}/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Car createCar(@PathParam("userId") Long userId, CarDTO carDTO){
        Car car = new Car(carDTO);
        return carRepo.create(car);
    }
}
