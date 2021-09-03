package controller;

import service.CartService;
import java.io.Serializable;
import model.Product;
import model.StockNote;
import model.Cart;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.OrderSystemService;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

/**
 * 
  N Lankshear. s3529801. SEPT M2.  T2 2021.
 */
 
@RestController
@RequestMapping(path = "/cart")
public class CartController {
    
   @Autowired
   private OrderSystemService OrderSystemService;
    
   @Autowired CartService CartService;

   @PutMapping("/createCustomer/") 
   public void addCustomer(@RequestBody Customer customer) {
       CartService.saveCustomer(customer);
   } 
       
   @RequestMapping(path = "createCart/", method = RequestMethod.GET) 
   public Cart createCart() {
       String cartID = "cartID";
       Cart cart = new Cart();
       Random r = new Random();
       //  Cart cart = new Cart();
       
       int cartIDint = r.nextInt(20000);
       if (cartIDint < 0 ){
	   cartIDint = cartIDint * -1;}
       cartID += Integer.toString(cartIDint); 
       cart.setCartID(cartID);
       CartService.saveCart(cart);
       
       return CartService.getCartByCartID(cartID);
   }

    @RequestMapping(path = "/getCart/{cartID}", method = RequestMethod.GET) 
    public List<Cart> getCart(@PathVariable String cartID) {
	 Cart cart = CartService.getCartByCartID(cartID);
	 List<Cart> listCart = new ArrayList<Cart>();
	 listCart.add(cart);
	 return listCart;
    }
    
    @RequestMapping(path = "/addProductToCart/{pcode}", method = RequestMethod.GET) 
    public Cart addProductToCart(@PathVariable String pcode) {
       // @RequestParam int qty    
	//Product product = OrderSystemService.getProductByPcode(product_id);

	//a cart to add products to.
	String dummyCart = "cartID15292";
	Cart dummy = CartService.getCartByCartID(dummyCart);
	
	StockNote stocknote =  OrderSystemService.getStockNoteByPCode(pcode);
	if (stocknote != null) {   //& stocknote qty > 0
	    Product product = OrderSystemService.getProductByPcode(pcode);
	    //List<Product> products
	    dummy.getProducts().add(product);
	    
	    CartService.mergeCart(dummy);
	    stocknote.reduceQTYByOne();
	    OrderSystemService.saveStockNote(stocknote);
	    
	    //product.getCarts().add(dummy);
	    //OrderSystemService.save(product);
	}
	
	return dummy;
   }
   
    
    /*
   @PutMapping("/getProductByPcode/{code}")
   public Product getProductByPcode(@PathVariable String code){
      Product product = OrderSystemService.getProductByPcode(code);
      return product;
   }
    
   @PutMapping("/placeStock/") 
   public void placeOrder(@RequestParam String product_id, @RequestParam int qty) {
      System.out.println("here");      
	
      Product product = OrderSystemService.getProductByPcode(product_id);
      StockNote stocknote = new StockNote(product, qty);
      stocknote.setPcode(product_id);
      OrderSystemService.saveStockNote(stocknote);
      product.setStockNote(stocknote);
      stocknote.setPcode(product_id);
      stocknote.setProduct(product);
      OrderSystemService.saveProduct(product);

   } 
    
   @RequestMapping(path = "getAllProducts/", method = RequestMethod.GET)
   public List<Product> getAllProducts(){  
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

   @RequestMapping(path = "deleteStockNote/{id}", method = RequestMethod.DELETE)
   public void deleteStockNote(@PathVariable String id){
       OrderSystemService.deleteStockNote(id); 
   }
    */
}

