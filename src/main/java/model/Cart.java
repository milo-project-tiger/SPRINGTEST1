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
@Table(name = "UserCart")
public class Cart{  
    private String name;       //Load with default user...
    private Customer customer;  //fetch details when logged in.
    private List<Product> products = new List<Product>;


    public Cart(String name){
	this.name = name;
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "CART_ID")
    public Products getProducts() {
	return this.products;
    }

    public void setProducts(Products products) {
	this.products = products;
    }
       
    public void addProduct(Product product) {
 	this.products.add(product);
    }

   
}
  
