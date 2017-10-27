package user;

/**
 * Created by sgorawski on 27.10.17.
 */
public class PaymentDetails {
    private String notifyUrl;
    private String customerIP;
    private String merchantPosID;
    private String description;
    private String currencyCode;
    private Integer totalAmount;
    private Product products;

    public PaymentDetails(String notifyUrl, String customerIP, String merchantPosID, String description, String currencyCode, Integer totalAmount, Product products) {
        this.notifyUrl = notifyUrl;
        this.customerIP = customerIP;
        this.merchantPosID = merchantPosID;
        this.description = description;
        this.currencyCode = currencyCode;
        this.totalAmount = totalAmount;
        this.products = products;
    }
}
