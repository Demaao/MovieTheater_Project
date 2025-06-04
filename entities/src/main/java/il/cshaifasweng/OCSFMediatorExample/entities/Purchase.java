package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table (name = "purchases")
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String productType;
    private LocalDateTime purchaseDate;
    private String paymentMethod;
    private String purchaseDescription;
    private String branchName;
    private double pricePaid;
    private int quantity;
    private int seatNum;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    public Purchase() {}

    //for cinema movies purchases
    public Purchase(String productType, LocalDateTime purchaseDate, String paymentMethod, double pricePaid, Customer customer,String branchName, int Quantity, String purchaseDescription) {
        this.productType = productType;
        this.purchaseDate = purchaseDate;
        this.paymentMethod = paymentMethod;
        this.pricePaid = pricePaid;
        this.customer = customer;
        this.branchName = branchName;
        this.quantity = Quantity;
        this.purchaseDescription = purchaseDescription;
    }

    public Purchase(String productType, LocalDateTime purchaseDate, String paymentMethod, double pricePaid, Customer customer,String branchName, int Quantity, String purchaseDescription, int seatNum) {
        this.productType = productType;
        this.purchaseDate = purchaseDate;
        this.paymentMethod = paymentMethod;
        this.pricePaid = pricePaid;
        this.customer = customer;
        this.branchName = branchName;
        this.quantity = Quantity;
        this.purchaseDescription = purchaseDescription;
        this.seatNum = seatNum;
    }

    public int getSeatNum(){
        return seatNum;
    }

    //for home movies purchases
    public Purchase(String productType, LocalDateTime purchaseDate, String paymentMethod, double pricePaid, Customer customer, String purchaseDescription) {
        this.productType = productType;
        this.purchaseDate = purchaseDate;
        this.paymentMethod = paymentMethod;
        this.pricePaid = pricePaid;
        this.customer = customer;
        this.purchaseDescription = purchaseDescription;
        this.quantity = 1;

    }

    // Getters and setters
    public String getProductType() {
        return productType;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(double pricePaid) {
        this.pricePaid = pricePaid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getPurchaseDescription() {return purchaseDescription;}
    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }

    public String getBranchName() {return branchName;}
    public void setBranchName(String branchName) {this.branchName = branchName;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
