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
 * Created by CoT on 7/29/18.
 */
@RestController
@RequestMapping(path = "/")
public class OrderSystemController {
    
   @Autowired
   private OrderSystemService OrderSystemService;

  
   @PutMapping("/addProduct/") 
   public void addProduct(@RequestBody Product product) {
      System.out.println("here");
      OrderSystemService.saveProduct(product);
   }     
    
   @PutMapping("/getProductByPcode/{code}")
   public Product getProductByPcode(@PathVariable String code){
      Product product = OrderSystemService.getProductByPcode(code);
      return product;
   }
    
   @PutMapping("/placeStock/") 
   public void placeOrder(@RequestParam String product_id, @RequestParam int qty) {
      System.out.println("here");      
	
      //OrderSystemService.saveProduct(OrderSystemService);
      Product product = OrderSystemService.getProductByPcode(product_id);
      StockNote stocknote = new StockNote(product, qty);
      product.setStockNote(stocknote);
      stocknote.setPcode(product_id);
      stocknote.setProduct(product);
      OrderSystemService.saveProduct(product);
      OrderSystemService.saveStockNote(stocknote);
      //stocknote.setInventory 
   } 
    
   @RequestMapping(path = "getAllProducts/", method = RequestMethod.GET)
   public List<Product> getAllProducts(){    //@PathVariable("Id") int id){
      return OrderSystemService.getAllProducts();
   }

  @RequestMapping(path = "getAllStockNotes/", method = RequestMethod.GET)
  public List<StockNote> getAllStockNotes(){
      return OrderSystemService.getAllStockNotes();
  }
    
   @RequestMapping(path = "deleteProduct/{id}", method = RequestMethod.DELETE)
   public void deleteProduct(@PathVariable String id){
       OrderSystemService.deleteProduct(id); 

   }

}


/*
  @RequestMapping(path = "getAllProviders1/", method = RequestMethod.GET)
  public List<Object[]> getAllProviders(){    //@PathVariable("Id") int id){
     return companyservice.findAllWithDetail(); 
    }
    
    @RequestMapping(path = "getAllSales/", method = RequestMethod.GET)
    public List findjoin(){

	return companyservice.findjoin();
    }
    
    @RequestMapping(path = "getAllProviders/", method = RequestMethod.GET)
    public String findAllWithDetail(){    //@PathVariable("Id") int id){
	
	String id ="";
	for (Object[] contact: companyservice.findAllWithDetail()){
	    id += "trading_name: " + contact[0] + " id: " + contact[1] + "\n";
	}
	return "these are projected details of provider:\n" + id + "\n";
	
	//
    }
    
    /*  Make Orders...  API.   */
    /*
    @RequestMapping(path = "getAllOrders/", method = RequestMethod.GET)
    public List<Order> getAllOrders(){    //@PathVariable("Id") int id){
        return companyservice.getAllOrders();
    }
        
    @PostMapping("/placeOrder/")  
    public void placeOrder(@RequestParam int prov_id, @RequestParam String staffname,
			   @RequestParam int qty, @RequestParam int prod_id) {
	
	System.out.println("post prov_id: " + prov_id);
	System.out.println("post qty: " + qty);
	
	Provider provider = companyservice.getProviderById(prov_id);
	Order order = new Order();
	OrderDetails od = new OrderDetails();
	od.setQty(qty);
	Product product = companyservice.getProductById(prod_id);	
	od.setProduct(product);
	int price = product.getSellingPrice();
	od.setPrice(price);
	order.addOrderDetails(od);
	companyservice.saveOrder(order);
	provider.addOrder(order);
	companyservice.saveProvider(provider);
	//addOrder(Order order)
			
    }
    
    //append further items to an existing order. 
    @PutMapping("/addToOrder/{id}") 
    public void addOrder(@RequestBody Order order, @PathVariable int id) {
	System.out.println("here");
    } 
    
}
    */
    /*  
    @RequestMapping(path = "getProviderById/{Id}", method = RequestMethod.GET)
    public Provider getProviderById(@PathVariable("Id") int id){
        return companyservice.getProviderById(id);
    }
    */
    /*     
	   @RequestMapping(path = "getProviderByName/{name}", method = RequestMethod.GET)
	   public List<Provider> getProviderByName(@PathVariable("name") String s){
	   return companyservice.getProviderByName(s);
	}*/

    
 /*
      Add existing detail or ability to provider.
      
      //need to find the id of provider.. and the id of ability.. and then join them.. 
      how is that achived with the controller, using http?
    
      Answer.. PUT request.. or use two request paramaters and an pathvar?
    */
    /*
    @PutMapping("/provider/{id}") 
    public void addDetails(@RequestBody Details details, @PathVariable int id) {
	System.out.println("here");
	//System.out.println(details);
	
	// contact = contactDao.findById(1l);   
	Provider provider = companyservice.getProviderById(id);  
	//contact.setFirstName("Kim Fung");    
	provider.setFirstName("Kim .....Fung");   
	companyservice.saveProvider(provider);
	
    } 
   
    @PostMapping("/register")
    public void registerUser(@RequestParam(required = false) int id, @RequestParam String name) {
	System.out.println(name);
	System.out.println(id);
	//id = (Long) id;
	
	Provider provider = companyservice.getProviderById(id);
	provider.setFirstName("Kim .....Fung");   
	//provider.setId(mid);
	companyservice.saveProvider(provider);
	
  }
  
    /*
    @RequestMapping(path="providers", method=RequestMethod.POST)
    public @ResponseBody void addProvider(@RequestBody Provider provider){
	//	System.out.println(Id);
	companyservice.saveProvider(provider);
	//return student.getId();
    }

    */
    //add product to a unique provider.


  
