import Models.Car.Car;
import Models.Car.CarDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

import org.junit.*;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Ignore
public class CarTest {
    private static Car createdCar = null;

    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "http://localhost/DaOne";
        RestAssured.port = 8080;
    }


   @Test
    public void A_createCar(){
       CarDTO carDTO = new CarDTO();
       carDTO.setCarName("testAuto");
       carDTO.setModel("Tesla");
       carDTO.setPrice(30000);
       carDTO.setOwner_id(1l);

       Response response = given()
               .contentType(ContentType.JSON)
               .body(carDTO)
               .when()
               .post("/api/car/1/create").then().statusCode(200).extract().response();

       createdCar = response.getBody().jsonPath().getObject("car", Car.class);

       System.out.println(createdCar.toString());

   }

    @Test
    public void B_getCar() {
        Response response = get("/api/car/" + createdCar.getId())
                .then()
                .statusCode(200).extract().response();
        System.out.println(response.getBody().prettyPrint());
    }

   @AfterClass
    public static void cleanUp()
   {
       if(createdCar != null){
           delete("/car/" + createdCar.getId()).then().statusCode(200);
       }
   }

}