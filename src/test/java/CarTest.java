import io.restassured.RestAssured;

import org.junit.BeforeClass;


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