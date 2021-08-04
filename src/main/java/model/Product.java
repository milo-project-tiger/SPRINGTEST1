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

@Entity
@Table(name = "SPIRIT_PRODUCT")
/*@NamedQueries({
  @NamedQuery(name="Product.findAllWithDetail", 
  query="select distinct p from Product p inner join fetch p.provider")
  })*/

public class Product implements Serializable {
   private int id;
   private int version;
   private String name;
   private String model;
   private String description;
   private int sellingPrice;  //!
   private Inventory inventory;
   private int qty;
   //private Category category; //!
   //private Provider provider;  //!

   public Product() {
   }
    
   public Product(String name, String model) {
      this.name = name;
      this.model = model;
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


   @Column(name = "quantity")
   public int getQTY() {
      return this.qty;
   }
    
   public void setQTY(int qty) {
       this.qty = qty; 
   }  
     
    
   @Version
   @Column(name = "VERSION")
   public int getVersion() {
      return this.version;
   }
    
   public void setVersion(int version) {
      this.version = version;
   }
    
   @Column(name = "name")
   public String getName() {
      return this.name;
   }
    
   public void setName(String name) {
      this.name = name;
   }
    
   @Column(name = "MODEL")
   public String getModel() {
      return this.model;
   }
    
   public void setModel(String model) {
      this.model = model;
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
    
   @Column(name = "DESCRIPTION")
   public String getDescription() {
      return this.description;
   }
    
   public void setDescription(String description) {
      this.description = description;
   }
    /*
    */
   @Column(name = "SELLINGPRICE")
   public int getSellingPrice() {
      return this.sellingPrice;
   }
    
   public void setSellingPrice(int sellingPrice) {
      this.sellingPrice = sellingPrice;
   }    
}
