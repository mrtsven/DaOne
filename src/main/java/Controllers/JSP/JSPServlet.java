package Controllers.JSP;

import Models.Car.Car;
import Repository.CarRepo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import java.io.IOException;

@WebServlet(name = "JSPServlet", urlPatterns = {"/jaas"})
public class JSPServlet {
    @EJB
    CarRepo carRepo;

    public JSPServlet(){
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
        request.getRequestDispatcher("/jaas/admin.jsp")
                .forward(request, response);
    }
}
