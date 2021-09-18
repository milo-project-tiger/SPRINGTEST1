package model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by CoT on 10/14/17.
 */

@Entity
@Table(name = "receipt")
public class Receipt {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String customerName;

    @Column
    private String DOB;
    
    @Column
    private String shippingDetails;

    @Column
    private String payment;// { CASH, BITCOIN }
    
    @Column
    private String passPhrase;
    
    @OneToMany(mappedBy = "receipt", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ReceiptDetail> receiptDetails;

    public List<ReceiptDetail> getReceiptDetails() {
        return receiptDetails;
    }

    public void setReceiptDetails(List<ReceiptDetail> receiptDetails) {
        this.receiptDetails = receiptDetails;
    }

    public Receipt() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
    
    
    public void setShippingDetails(String sd) {
        this.shippingDetails = sd;
    }

    public String getShippingDetails() {
        return shippingDetails;
    }

    
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public void setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
    }
}
