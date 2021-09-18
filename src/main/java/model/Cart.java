package model;

import java.util.Date;
import java.io.Serializable;
import org.hibernate.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Fetch;
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
import java.util.ArrayList;

/*
  N Lankshear. s3529801. SEPT M2.
 */

@Entity
@Table(name = "UserCart")
public class Cart{  
    private String name;       //Load with default user...
    private Customer customer;  //fetch details when logged in.
    private List<Product> products = new ArrayList<Product>();
    private int ID;
    private String cartID;
    private int cartValue = 0;
    
    public Cart(){
	
    }

    public Cart(String name){
	this.name = name;
    }
    
    @Column(name = "cartValue")
    public int getCartValue() {
	return this.cartValue;
    }

    public void setCartValue(int cartValue) {
	this.cartValue= cartValue;
    
    }
    @Column(name = "CartID")
    public String getCartID() {
	return this.cartID;
    }

    public void setCartID(String cartID) {
	this.cartID= cartID;
    } 
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public int getId() {
	return this.ID;
    }

    public void setId(int ID) {
	this.ID = ID;
    }

    @Column(name = "customerName")
    public String getName() {
	return this.name;
    }

    public void setName(String name) {
	this.name = name;
    } 

    //customer has one cart at a time 
    @OneToOne(fetch = FetchType.EAGER)
    // @JsonIgnore
    @JoinColumn(name = "CUSTOMER_ID")
    public Customer getCustomer() {
	return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer= customer;
    }
    
    //one cart has many products
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "product_usercart",
	       joinColumns = @JoinColumn(name = "CART_ID"),
	       inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
	public List<Product> getProducts() {
	   return this.products;
    }
   
    public void setProducts(List<Product> products) {
	this.products = products;
    }
       
    public void addProduct(Product product) {
 	this.products.add(product);
    }

    //public boolean equals
}
  
