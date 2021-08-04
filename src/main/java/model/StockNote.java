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
@Table(name = "StockNote")
public class StockNote{
     
    // Product         one to one.
    // Inventory       many to one.
    private int PID;
    private int qty;
    //product id...
    //qty...

    public StockNote(int PID, int qty) {
      this.PID= PID;
      this.qty  = qty;
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

   @Column(name = "PID")
   public int getPID() {
      return this.PID;
   }
    
   public void setPID(int PID) {
      this.PID = PID;
   }

    
   @Column(name = "Quantity")
   public int getQTY() {
      return this.qty;
   }
    
   public void setQuantity(int QTY) {
      this.qty = QTY;
   }


}
