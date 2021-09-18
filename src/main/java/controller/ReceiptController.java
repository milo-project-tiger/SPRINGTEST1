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
   public void addReceipt(@RequestParam String passPhrase, @RequestParam String customerName) {

       Receipt receipt = new Receipt();
       
       receipt.setPassPhrase( passPhrase );
       receipt.setCustomerName( customerName );
       // D.O.B
       // add address // add payment.
       receiptService.saveReceipt(receipt);
   }
    
   @RequestMapping(path = "getReceipt/{passPhrase}", method = RequestMethod.PUT)
   public Receipt getReceipt(@PathVariable String passPhrase,  @RequestParam String DOB){
       Receipt receipt = receiptService.getReceiptByPassPhrase(passPhrase, DOB);
       return receipt;
   }
}
