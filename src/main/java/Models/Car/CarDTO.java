package Models.Car;

import Models.Part.Part;

import java.util.List;

public class CarDTO {
    private String carName;
    private String model;
    private double price;
    private Long liked_by;
    private Long owner_id;
    private List<Part> parts;

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
