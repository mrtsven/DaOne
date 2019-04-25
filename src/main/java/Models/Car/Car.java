package Models.Car;

import Models.Part.Part;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.List;

@Entity
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "The car name cannot be empty")
    private String carName;
    @NotNull(message = "You need to specify the car model")
    private String model;
    @NotNull(message = "You have to specify the car price")
    private double price;
    private Long liked_by;
    private Long owner_id;
    @OneToMany
    @Null
    private List<Part> parts;

    public Car() {
    }

    public Car(CarDTO carDTO) {
        this.carName = carDTO.getCarName();
        this.model = carDTO.getModel();
        this.price = carDTO.getPrice();
        this.liked_by = carDTO.getLiked_by();
        this.owner_id = carDTO.getOwner_id();
        this.parts = carDTO.getParts();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(Long liked_by) {
        this.liked_by = liked_by;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }
}
