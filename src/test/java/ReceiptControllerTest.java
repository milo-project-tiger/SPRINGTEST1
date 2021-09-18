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
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;

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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import  org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import  org.mockito.MockitoAnnotations.Mock;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;

//import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

//import org.junit.jupiter.api.Before;
//import org.junit.jupiter.api.Test;
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

    protected Cart cart;

    
    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(new ReceiptController(cartService, receiptService))
            //.setHandlerExceptionResolvers(exceptionResolver()) //crucial for standaloneSetup of MockMVC
            .build();
    }
    
    @BeforeEach
    public void setUpData() {    
	//  objectMapper.registerModule(new ProblemModule());
        //objectMapper.registerModule(new ConstraintViolationProblemModule());
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
       cartService.saveCart(this.cart);
       String cartID = this.cart.getCartID();	
       given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
       // add the products to cart.
       this.cart.setProducts(productList);
       cartService.mergeCart(this.cart);

    }
    
    @After
    public void tearDown(){
	this.productList = null;
	//this.cart = null;
    }   
    
    @Test   //FAIL
    public void RecieptFetchedCorrectlyByPassPhrase() throws Exception {
		
	//Cart cartTest = new Cart();
	//cartTest.setCartID("cart555");
	/*	String cartID = this.cart.getCartID();	
	//Assert.assertTrue(cartID!=null);
	String pcode = this.cart.getProducts().get(0).getPcode();
	Product prod = this.cart.getProducts().get(0);
	//cartTest.setProducts(productList);

	given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
	given(orderSystemService.getProductByPcode(pcode)).willReturn(prod);
	*/
	
	Receipt receiptTest = new Receipt();
	receiptTest.setPassPhrase("helloWorld");
	receiptTest.setCustomerName("Morgana");
	receiptTest.setDOB("090990");
	
	/*
	receiptTest.setPayment("crypto");
	receiptTest.setShipping("Ton Duc Tanh");
	receiptTest.setCart("090990");
	*/
	String pass = receiptTest.getPassPhrase();
	String DOB =  receiptTest.getDOB();
	given(receiptService.getReceiptByPassPhrase(pass,DOB )).willReturn(receiptTest);
	///getCart/{cartID}
        this.mockMvc.perform(put("/receipt/getReceipt/{passPhrase}",pass)
			     .param("customerName","Morgana")
			     .param("Payment", "crypto")
			     .param("shipping", "Ton Duc Tanh")	     
			     .param("cartID", "cartIDxxxx")
			     .param("status", "in-transit")
			     .param("DOB", "090990"))
	    .andExpect(status().isOk())
	    .andExpect(jsonPath("$.dob", is(receiptTest.getDOB())))
	    .andExpect(jsonPath("$.customerName", is(receiptTest.getCustomerName())))
	    .andExpect(jsonPath("$.shipping", is("Ton Duc Tanh")))
	    .andExpect(jsonPath("$.shipping", is("crypto")))
	    .andExpect(jsonPath("$.cart.cartID", is("cartIDxxxx")))
	    .andExpect(jsonPath("$.status", is("in-transit")));
	    
	//.andExpect(jsonPath(".$cart.products.size()", is(productList.size())));
	/*this.mockMvc.perform(get("/cart/getSessionCart/"))
	  .andExpect(status().isOk())
	  //   .andExpect(jsonPath("$.cartID", is(cartTest.getCartID())))
	  .andExpect(jsonPath("$[0].products.size()", is(productList.size())));
	*/
    }

    @Test
    public void receiptReturnsNullIfNoPassphraseORIncorrectFormatDOB(){




    }

    
	    /*
    @Test
    public void orderStatusSuccessfullyUpdates(){
       
       Receipt receiptTest = new Receipt();
       receiptTest.setPassPhrase("helloWorld");
       receiptTest.setCustomerName("Morgana");
       receiptTest.setPayment("crypto");
       receiptTest.setShipping("Ton Duc Tanh");
       receiptTest.setCart("090990");
       
       String pass = receiptTest.getPassPhrase();
       String DOB =  receiptTest.getDOB();
       given(receiptService.getReceiptByPassPhrase(pass,DOB )).willReturn(receiptTest);
           
      
    }
*/
	    

	    
    /*
    @Test  //and D.O.B
    public void receiptCanBeDisplayedByPassphrase() throws Exception {
	Cart cartTest = new Cart();
	cartTest.setCartID("cart303");
	String cartID = cartTest.getCartID();	
	Assert.assertTrue(cartID!=null);
	//Assert.assertTrue(this.cart!=null);
	                                             //Optional.of(      );
        given(cartService.getCartByCartID(cartID)).willReturn(cartTest);
	//	given(cartService.getCartByCartID(cartID)).willReturn(cartTest);
	///getCart/{cartID}
        this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(cartTest.getCartID())));
		
	 }*/
}
