package test;

import service.OrderSystemService;
import service.CartService;
import controller.ReceiptController;
import service.ReceiptService;

import java.util.Iterator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Receipt;
import model.Product;
import model.Cart;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import  org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import  org.mockito.MockitoAnnotations.Mock;

import org.springframework.http.MediaType;

//import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.JsonPathResultMatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:testContextConfig.xml")  //empty, but necessary for tests to work.
@WebAppConfiguration
public class ReceiptControllerTest {

   private MockMvc mockMvc;

   @Mock
   private OrderSystemService orderSystemService;

   @Mock
   private CartService cartService;

   @Mock
   private ReceiptService receiptService;
    
   private List<Product> productList;

   private List<Receipt> receiptList  = new ArrayList();;
   
   private List<String> intList = new ArrayList();
   
   protected Cart cart;

   private String cartID;
   private Receipt receipt;
    
   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(new ReceiptController(cartService, receiptService))
	 //.setHandlerExceptionResolvers(exceptionResolver()) //crucial for standaloneSetup of MockMVC
	 .build();
   }
    
   @Before
   public void setUpData() {    
      //  objectMapper.registerModule(new ProblemModule());
      //objectMapper.registerModule(new ConstraintViolationProblemModule());
      
      //receipt for Order of tiger beer.
      this.receipt = new Receipt();
      this.receipt.setPassPhrase("mojojojo");
      this.receipt.setCustomerName("Morgana");
      this.receipt.setDOB("090990");
      this.receipt.setOrderStatus("PENDING");
      this.receipt.setPayment("crypto");
      this.receipt.setShippingDetails("Ton Duc Tanh");

      Receipt receipt1 = this.receipt;
      //this.receipt.setCart("tigerbeercart");
      this.receiptList.add(receipt1);
	
      Receipt receipt2 = new Receipt();
      receipt2.setPassPhrase("milomilomilo");
      receipt2.setCustomerName("Bubbles");
      receipt2.setDOB("010110");
      receipt2.setOrderStatus("PENDING");
      receipt2.setPayment("CASH");
      receipt2.setShippingDetails("Ton Duc Tanh"); 
     
      //receipt2.setCart("lemonadecart");
      this.receiptList.add(receipt2);
	    
      Receipt receipt3 = new Receipt();
      receipt3.setPassPhrase("somtum");
      receipt3.setCustomerName("mojojojo");
      receipt3.setDOB("030330");
      receipt3.setOrderStatus("PENDING");
      receipt3.setPayment("crypto");
      receipt3.setShippingDetails("Nguyen Van Linh"); 
      //receipt3.setCart("weapons of mass destruction");
      this.receiptList.add(receipt3);
      
      //list of products
      this.productList = new ArrayList<>();
      Product p1 = new Product("Product1");
      p1.setPcode("123abc");
      this.productList.add(new Product("Product1"));
      this.productList.add(new Product("Product2"));
      this.productList.add(new Product( "Product3"));

      
      //create a cart
      this.cart = new Cart();
      this.cart.setCartID("milk123");
      this.cart.setCartValue(1000);
      //cartService.savetCart(this.cart);
      this.cartID = cart.getCartID();	
      //given(cartService.getCartByCartID(cartID)).willReturn(this.cart);

      Assert.assertTrue(this.cartID!=null);
      
      // add the products to cart.
      // this.cart.setProducts(productList);
      
      // when(cartService.mergeCart(any(Cart.class))).thenReturn(this.cart);    
   }
    
   @After
   public void tearDown(){
      this.productList = null;
      this.cart = null;
      this.receipt = null;
   }   

   @Test  
   public void newOrderMustHaveUniqueCode(){

      
   }
   
   @Test   //PASS
   public void getAllReceiptsReturnsCorrectNumberOfResults() throws Exception{
      
      Assert.assertEquals(receiptList.size(), 3);
      given(receiptService.getAllReceipts()).willReturn(this.receiptList);
      
      this.mockMvc.perform(get("/receipt/getAllReceipts/"))
	 .andExpect(status().isOk())
      .andExpect(jsonPath("$.size()", is(receiptList.size())));
   }
   
   @Test   //PASS
   public void orderStatusUpdatesCorrectly() throws Exception{
      // "changeStatus/{passPhrase}" //passPhrase status
      String pass = this.receipt.getPassPhrase();
      
      //update status with acceptable value
      given(receiptService.getReceiptByPassPhrase(pass)).willReturn(this.receipt);
      this.mockMvc.perform(put("/receipt/changeStatus/{passPhrase}",pass)
			   //.param("customerName","Morgana")
			   //.param("Payment", "crypto")
			   .param("status", "DONE"))	     
			   //.param("cartID", "cartIDxxxx")
			   // .param("status", "in-transit")
			   //.param("DOB", "090990")) 
	 .andExpect(status().isOk());
      this.mockMvc.perform(put("/receipt/getReceipt/{passPhrase}",pass))
	 //returns expected result
	 .andExpect(jsonPath("$[0].orderStatus", is("DONE")));
      
   }

   @Test   //PASS
   public void incorrectStatusValueDoesNotUpdateOrderStatus() throws Exception{

      String pass = this.receipt.getPassPhrase();
      //update status with acceptable value
      given(receiptService.getReceiptByPassPhrase(pass)).willReturn(this.receipt);
      
      //update status with incorrect value 
      this.mockMvc.perform(put("/receipt/changeStatus/{passPhrase}",pass)
			   .param("status", "WRONG"))	     
	 .andExpect(status().isOk());
      this.mockMvc.perform(put("/receipt/getReceipt/{passPhrase}",pass))
	 //returns expected result
	 .andExpect(jsonPath("$[0].orderStatus", is("PENDING")));
         
   }
   
   
   @Test   //FAIL
   public void ReceiptFetchedCorrectlyByPassPhrase() throws Exception {
      String pass = this.receipt.getPassPhrase();
      given(receiptService.getReceiptByPassPhrase(pass)).willReturn(this.receipt);
      //setCartInSession
      cartService.setCartInSession(this.cart);
      given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
      given(cartService.getCartInSession()).willReturn(this.cart);	 
      
      this.mockMvc.perform(put("/receipt/getReceipt/{passPhrase}",pass)
			   .param("DOB", "090990"))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].dob", is(this.receipt.getDOB())))
	 .andExpect(jsonPath("$[0].customerName", is(this.receipt.getCustomerName())))
      .andExpect(jsonPath("$[0].shippingDetails", is("Ton Duc Tanh")))
	  .andExpect(jsonPath("$[0].payment", is("crypto")))
	  // .andExpect(jsonPath("$.cart.cartID", is("cartIDxxxx")))
	 .andExpect(jsonPath("$[0].orderStatus", is("PENDING")));
//         .andExpect(jsonPath("$[0].total", is("1000")));
      
      //.andExpect(jsonPath(".$cart.products.size()", is(productList.size())));
      /*this.mockMvc.perform(get("/cart/getSessionCart/"))
	.andExpect(status().isOk())
	//   .andExpect(jsonPath("$.cartID", is(cartTest.getCartID())))
	.andExpect(jsonPath("$[0].products.size()", is(productList.size())));
      */
   }

   @Test
   public void receiptSavedandRetrievedCorrectly(){
      
   }

   @Test
   public void receiptReturnsNullIfNoPassphraseORIncorrectFormatDOB(){
      
   }
}
