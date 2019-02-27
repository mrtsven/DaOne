package Controllers;


import Models.Car;
import Repository.CarRepo;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test")
public class TestController extends HttpServlet {
    @EJB
    private CarRepo carRepo;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h3>Supuuuuu...</h3>");
        //created a test car will move this out later
//        Car car = new Car();
//        car.setCarName("BMW");
//
//        carRepo.create(car);
        for (Car car : carRepo.findAll()) {
            out.println(car.getCarName());
        }

    }
}
