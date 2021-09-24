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
import java.util.Iterator;

import javax.servlet.http.*; 

/**
 * 
  N Lankshear. s3529801. SEPT M2.  T2 2021.
 */
 
@RestController
@RequestMapping(path = "/cart")
public class CartController {
    
   @Autowired
   private OrderSystemService orderSystemService;
    
   @Autowired
   private CartService cartService;
    
   public CartController(OrderSystemService orderSystemService, CartService cartService){
       this.orderSystemService = orderSystemService;
       this.cartService = cartService;
   }
    
   //Javascript traversal problem again.
   @RequestMapping(path = "getSessionCart/", method = RequestMethod.GET)
   public List<Cart> getSessionCart(){
	
      Cart cartSESSION = (Cart) cartService.getCartInSession();
      List<Cart> listCart = new ArrayList<Cart>();
      listCart.add(cartSESSION);
      return listCart;
   }

   
   @RequestMapping(path = "/setCartSession/{cartID}", method = RequestMethod.GET)
   public List<Cart> setSessionCart(@PathVariable String cartID){  
       //may require String Sanitization
       
       //get cart by ID
       Cart cart = cartService.getCartByCartID(cartID);
       //add the cart to session.

       cartService.setCartInSession(cart);
       Cart cartSession = cartService.getCartInSession();
       List<Cart> listCart = new ArrayList<Cart>();
       listCart.add(cartSession);
       return listCart; //return the cart from session variable.
   }

    @RequestMapping(path = "deleteStockNote/{id}", method = RequestMethod.DELETE)
    public void deleteFromCart(@PathVariable String id){
	//orderSystemService.deleteStockNote(id); 
    }

    
   @PutMapping("/createCustomer/") 
   public void addCustomer(@RequestBody Customer customer) {
       cartService.saveCustomer(customer);
   } 

   @RequestMapping(path = "createCart/", method = RequestMethod.GET) 
   public List<Cart> createCart() {
       
       Cart cart = cartService.saveCart();       
       List<Cart> listCart = new ArrayList<Cart>();
       listCart.add(cart);
       
       return listCart;
   }

    @RequestMapping(path = "/removeProductFromCart/{cartID}", method = RequestMethod.PUT) 
    public void removeFromCart(@PathVariable String cartID, @RequestParam String prod_ID){
	//find the cart
	Cart cart = cartService.getCartByCartID(cartID);
	
	//scroll through carts products..
	List<Product> products = cart.getProducts();
	Iterator iter = products.iterator();

	while(iter.hasNext()){
	    Product product = (Product) iter.next();
	    //remove the product we do not want...
	    if (product.getPcode().equals(prod_ID)){

		//collect the selling price and subtract it from cart Total.
		int deduct = product.getSellingPrice();
		int value = cart.getCartValue();
		int newValue = value - deduct;
		//set cartValue
		cart.setCartValue(newValue);

		iter.remove();
		break;
	    }
	}
	
	cart.setProducts(products);
	// i. merge cart
	cartService.mergeCart(cart);
	
	// ii. update cart in session...
	cartService.setCartInSession(cart);
	
	// iii. increment stock
	
    }
    
    //Bug: JSON is not returned to browser as Cart Object. 
    @RequestMapping(path = "/getCart/{cartID}", method = RequestMethod.GET) 
    public List<Cart> getCart(@PathVariable String cartID) {
	 Cart cart = cartService.getCartByCartID(cartID);
	 List<Cart> listCart = new ArrayList<Cart>();
	 listCart.add(cart);
	 return listCart;
    }
    
    @RequestMapping(path = "/getAllCartProducts/{cartID}", method = RequestMethod.GET)
    public List<Product> getAllCartProducts(@PathVariable String cartID){  
	return cartService.getAllCartProducts(cartID);
    }

    
    @RequestMapping(path = "/addProductToCart/{pcode}", method = RequestMethod.PUT) 
    public Cart addProductToCart(@PathVariable String pcode, @RequestParam String cartID) {

	Cart dummy = cartService.getCartByCartID(cartID);
	dummy.setName("magickshoes");
	StockNote stocknote =  orderSystemService.getStockNoteByPCode(pcode);
	
	if (stocknote != null) {   //& stocknote qty > 0
	    
	    Product product = orderSystemService.getProductByPcode(pcode);
	    //List<Product> products
	    //dummy.setProducts() 
	    dummy.addProduct(product);
	    //calculate total cost       //a function to refactor?
	    int cartValue = dummy.getCartValue();
	    cartValue += product.getSellingPrice();
	    dummy.setCartValue(cartValue);	
	    //////////
	    //dummy.addProduct(product);
	    //reduce stock
	    stocknote.reduceQTYByOne();
	    orderSystemService.saveStockNote(stocknote);
	    cartService.mergeCart(dummy);
	}
	else {
	    Product product = orderSystemService.getProductByPcode(pcode);
	    //List<Product> products
	    //dummy.setProducts() 
	    dummy.addProduct(product);
	    //calculate total cost       //this could be a function to refactor!
	    int cartValue = dummy.getCartValue();
	    cartValue += product.getSellingPrice();
	    dummy.setCartValue(cartValue);
	    
	    cartService.mergeCart(dummy);
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


    
    
    /*	
	package net.codejava;
	import java.io.*;
	import java.util.*;
	import javax.servlet.*;
	import javax.servlet.http.*;
	@WebServlet("/test_session")
	
	
	public class TestSessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public TestSessionServlet() {
	super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	
        HttpSession session = request.getSession();
        
        PrintWriter writer = response.getWriter();
        writer.println("Session ID: " + session.getId());
        writer.println("Creation Time: " + new Date(session.getCreationTime()));
        writer. println("Last Accessed Time: " + new Date(session.getLastAccessedTime()));
	   }
	}
	}
	*/
