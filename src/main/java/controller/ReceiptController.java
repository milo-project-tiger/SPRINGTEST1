package controller;

import service.ReceiptService;
import service.CartService;
import java.io.Serializable;
import model.Product;
import model.StockNote;
import model.Cart;
import model.Customer;
import model.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.OrderSystemService;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import javax.servlet.http.*; 

/**
 * 
 N Lankshear. s3529801. SEPT M2.  T2 2021.
*/
 
@RestController
@RequestMapping(path = "/receipt")
public class ReceiptController {
    
   @Autowired
   private CartService cartService;
    
   @Autowired
   private ReceiptService receiptService;

       
   public ReceiptController(CartService cartService, ReceiptService receiptService){
      this.cartService = cartService;
      this.receiptService = receiptService;
   }
    
   @PutMapping("/createReceipt/{cartID}") 
   public void addReceipt(@RequestParam String passPhrase, @RequestParam String DOB, @RequestParam String payment,
			  @RequestParam String shipping) {

      Receipt receipt = new Receipt();
       
      receipt.setPassPhrase( passPhrase );
      //receipt.setCustomerName( customerName );
      receipt.setDOB(DOB);
      receipt.setPayment(payment);
      receipt.setShippingDetails(shipping);
      receipt.setOrderStatus("PENDING");

      //find and add cart object
      Cart cart = cartService.getCartInSession();
      //set total price from cart.
      int total = cart.getCartValue();
      receipt.setTotal(total);
      //receipt.setCart(cart);
      
      //save receipt
      receiptService.saveReceipt(receipt);
   }

   @RequestMapping(path = "getAllReceipts/", method = RequestMethod.GET)
   public  List<Receipt> getAllReceipts(){
      
      return receiptService.getAllReceipts();
   }
   
   @RequestMapping(path = "getReceipt/{passPhrase}", method = RequestMethod.PUT)
   public List<Receipt> getReceipt(@PathVariable String passPhrase){ //,  @RequestParam String DOB){
      Receipt receipt = receiptService.getReceiptByPassPhrase(passPhrase); //,DOB);
      List receiptList = new ArrayList();
      receiptList.add(receipt);
      return receiptList;
   }

   @RequestMapping(path = "changeStatus/{passPhrase}", method = RequestMethod.PUT)
   public boolean changeOrderStatus(@PathVariable String passPhrase, @RequestParam String status){
//,  @RequestParam String DOB,
      //@RequestParam String admincode){}

      Receipt receipt = receiptService.getReceiptByPassPhrase(passPhrase);
      //{ PENDING | APPROVED | SHIPPING | DONE  }
      if (status.equals("PENDING") || status.equals("APPROVED") ||
	  status.equals("SHIPPING") || status.equals("DONE")){
	    receipt.setOrderStatus(status);
	    receiptService.mergeReceipt(receipt);
	    return true;
      }
      else {
	 return false;
      }
	       
   }
}
