package user;

/**
 * Created by sgorawski on 27.10.17.
 */
public class Product {
    private String name;
    private Integer unitPrice;
    private Integer quantity;

    public Product(String name, Integer unitPrice, Integer quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }
}
