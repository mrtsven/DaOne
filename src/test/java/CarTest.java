import Models.Car;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;


public class CarTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost/DaOne";
        RestAssured.port = 8080;
    }

//   @Test
//   public void getCar() {
//       get("/api/car/get/2")
//               .then()
//               .body("id", equalTo(2))
//               .body("carName", equalTo("BMW"));
//   }
//
//   @Test
//    public void createCar(){
//        Car car = new Car();
//        car.setCarName("testAuto");
//        Gson gson = new Gson();
//
//       Response response = given()
//               .contentType(ContentType.JSON)
//               .body(gson.toJson(car))
//               .when()
//               .post("/api/car/create");
//       // tests
//       response.then().body("carName", Matchers.is("testAuto"));
//   }

}