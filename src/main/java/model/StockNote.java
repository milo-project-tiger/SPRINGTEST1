package model;

import java.util.Date;
import java.io.Serializable;
import org.hibernate.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.FetchMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Version;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.List;

/*
  N Lankshear. s3529801. SEPT M1.
 */

@Entity
@Table(name = "StockNote")
public class StockNote{  
    private Product product;
    private Inventory inventory;
    private int qty;
    private int PID;
    //product code
    private String pcode;
    private int id;
   
    
    public StockNote(){} 

    public StockNote(Product product,int qty) {
	//  this.product = product;
	this.qty = qty;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
	return this.id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Column(name = "productPcode")
    public String getPcode() {
	return this.pcode;
    }

    public void setPcode(String code) {
	this.pcode = code;
    } 


    @Column(name = "Quantity")
    public int getQTY() {
	return this.qty;
    }

    public void setQTY(int QTY) {
	this.qty = QTY;
    }

    public void reduceQTYByOne(){
	setQTY(this.qty -1);
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "INVENTORY_ID")
    public Inventory getInventory() {
	return this.inventory;
    }

    public void setInventory(Inventory inventory) {
	this.inventory = inventory;
    }

    @OneToOne(fetch = FetchType.EAGER)
    // @JsonIgnore
    @JoinColumn(name = "PRODUCT_ID")
    public Product getProduct() {
	return this.product;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public void setPID(int PID ){
	this.PID = PID;
    }

    @Column(name = "PID")
    public int  getPID() {
	return this.PID;
    }   
}
  
