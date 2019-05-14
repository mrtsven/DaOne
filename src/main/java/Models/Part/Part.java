package Models.Part;

import org.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Part implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "You cannot leave the description empty")
    private String description;
    @NotNull(message = "Specify if you can sell this car part")
    private boolean sellable;
    private double price;

    public Part() {
    }

    public Part(PartDTO partDTO) {
        this.description = partDTO.getDescription();
        this.sellable = partDTO.isSellable();
        this.price = partDTO.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSellable() {
        return sellable;
    }

    public void setSellable(boolean sellable) {
        this.sellable = sellable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public JSONObject toJsonCustom(){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("id", this.id);
        jsonObject.put("description", this.description);
        jsonObject.put("sellable", this.sellable);
        jsonObject.put("price", this.price);

        return  jsonObject;
    }
}
