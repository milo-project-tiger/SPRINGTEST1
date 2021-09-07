package controller;

import java.io.Serializable;
import model.Product;
import model.StockNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.OrderSystemService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 
  N Lankshear. s3529801. SEPT M1.  T2 2021.
 */
 
@RestController
@RequestMapping(path = "/")
public class OrderSystemController {
    
   @Autowired
   private OrderSystemService orderSystemService;

    @RequestMapping("/")
   public String index(){
	return "hello world";
   }
   
    
   @PutMapping("/addProduct/") 
   public void addProduct(@RequestBody Product product) {
      System.out.println("here");
      orderSystemService.saveProduct(product);
   }     
    
   @PutMapping("/getProductByPcode/{code}")
   public Product getProductByPcode(@PathVariable String code){
      Product product = orderSystemService.getProductByPcode(code);
      return product;
   }
    
   @PutMapping("/placeStock/") 
   public void placeOrder(@RequestParam String product_id, @RequestParam int qty) {
      System.out.println("here");      
	
      Product product = orderSystemService.getProductByPcode(product_id);
      // could this check become a method.. or could it be simplified by introducing equals() in Model class?
      StockNote checkExistingNote =  orderSystemService.getStockNoteByPCode(product_id );
      if ( checkExistingNote !=null ){
	  checkExistingNote.setQTY(qty);
	  orderSystemService.mergeStockNote(checkExistingNote);
      }
      else{
	  StockNote stocknote = new StockNote(product, qty);
	  stocknote.setPcode(product_id);
	  orderSystemService.saveStockNote(stocknote);
	  product.setStockNote(stocknote);
	  stocknote.setPcode(product_id);
	  stocknote.setProduct(product);   
	  orderSystemService.saveProduct(product);
      }
   } 
    
    @RequestMapping(path = "getAllProducts/", method = RequestMethod.GET)
    public List<Product> getAllProducts(){  
	return orderSystemService.getAllProducts();
    }
    
    @RequestMapping(path = "getAllStockNotes/", method = RequestMethod.GET)
    public List<StockNote> getAllStockNotes(){
	return orderSystemService.getAllStockNotes();
    }
    
    @RequestMapping(path = "deleteProduct/{id}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable String id){
	orderSystemService.deleteProduct(id); 
    }
    
    @RequestMapping(path = "deleteStockNote/{id}", method = RequestMethod.DELETE)
    public void deleteStockNote(@PathVariable String id){
	orderSystemService.deleteStockNote(id); 
    }
}

