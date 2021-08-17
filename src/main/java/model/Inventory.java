package model;

import java.util.Date;
import java.io.Serializable;
import org.hibernate.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.FetchMode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Version;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Table(name = "inventory")
public class Inventory { 
private Set<StockNote> stocknotes = new HashSet<StockNote>();
   private int id;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ID")
   public int getId() {
      return this.id;
   }
    
   public void setId(int id) {
      this.id = id;
   }
        
   public String toString() {
     /*return "Contact - Id: " + id + ", First name: " + trading_name
	  + ", Last name: " + contact_name + ", Birthday: " + birthDate;*/
      return ""; //this.trading_name;
   }

   @OneToMany(mappedBy = "inventory",
	      orphanRemoval=true, fetch = FetchType.EAGER)
      @Cascade(CascadeType.ALL)
	       //
    public Set<StockNote> getStockNotes(){
       return this.stocknotes;
    }
    
    public void setStockNotes(Set<StockNote> StockNotes) {
       this.stocknotes = StockNotes;
    }
    public void addStockNote(StockNote stocknote) {
       //product.setProvider(this);
       getStockNotes().add(stocknote);
    } 
}
