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
@Table(name = "SPIRIT_PRODUCT")
/*@NamedQueries({
  @NamedQuery(name="Product.findAllWithDetail", 
  query="select distinct p from Product p inner join fetch p.provider")
  })*/

public class Product implements Serializable {
   private int id;
   public String pcode;
   private int version;
   public String name;
   private String model;
   private String description;
   private int sellingPrice;  
   private StockNote stocknote;
   //private Category category; 
   //private Provider provider; 

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

   @Column(name = "code")
   public String getPcode() {
      return this.pcode;
   }
    
   public void setPcode(String code) {
      this.pcode = code;
   } 
    
   @Column(name = "MODEL")
   public String getModel() {
      return this.model;
   }
    
   public void setModel(String model) {
      this.model = model;
   }
    
   @OneToOne(fetch = FetchType.EAGER)
   // @JsonIgnore
   //@JoinColumn(name = "STOCKNOTE_ID")
   public StockNote getStockNote() {
      return this.stocknote;
   }
    
   public void setStockNote(StockNote stocknote) {
      this.stocknote = stocknote;
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
