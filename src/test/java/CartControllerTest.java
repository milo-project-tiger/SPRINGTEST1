package test;

import service.OrderSystemService;
import service.CartService;
import controller.CartController;
import java.util.Iterator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
public class CartControllerTest {

   private MockMvc mockMvc;

   @Mock
   private OrderSystemService orderSystemService;

   @Mock
   private CartService cartService;
    
   private List<Product> productList;
   protected Cart cart;
   private String cartID;
   
   public String testCartID(){
      return this.cart.getCartID();
   }
    
   @Before
   public void setup() {
      MockitoAnnotations.initMocks(this);
      mockMvc = MockMvcBuilders.standaloneSetup(new CartController(orderSystemService, cartService))
	 //.setHandlerExceptionResolvers(exceptionResolver()) //crutial for standaloneSetup of MockMVC
	 .build();
   }
    
   @Before
   public void setUpData() {    
      //  objectMapper.registerModule(new ProblemModule());
      //objectMapper.registerModule(new ConstraintViolationProblemModule());

      //list of products
      this.productList = new ArrayList<>();
      this.productList.add(new Product("Product1"));
      this.productList.add(new Product("Product2"));
      this.productList.add(new Product( "Product3"));

      Iterator iter = productList.iterator();
      int i = 1;
      int cartValue = 0;

      //for all products, set a product code and price.
      while (iter.hasNext()){
	 Product p = (Product) iter.next();
	 p.setPcode("hello" + Integer.toString(i*500));
	 p.setSellingPrice(i * 1000 );
	 //cart Value: 1000 + 2000 + 3000  = 6000
	 cartValue += p.getSellingPrice();  
	 i++;  
      }
      
      //create a cart
      this.cart = new Cart();
      this.cart.setCartID("cart303");
      //get cartID globally
      this.cartID = cart.getCartID();	
      // add the products to cart.
      this.cart.setProducts(productList);
      this.cart.setCartValue(cartValue);
   }
   
   @After
   public void tearDown(){
      this.productList = null;
      this.cart = null;
   }
   
   @Test  //S
   public void whenProductAddedToCartStocknoteQtyDecreases(){

   }
    
   @Test  //S  //R
   public void whenProductRemoveToCartStocknoteQtyIncreases(){

   }

   @Test  //R
   public void productsAddToCartAfterOneOrAllRemoved(){}

   @Test  //S
   public void outOfStockItemNotAdded(){}

    @Test   //CART SESSION?
   public void noDuplicateCarts(){}

   @Test   //R
   public void correctProductRemovedFromCart(){}

   @Test   //R
   public void allProductsCanBeRemovedFromCart(){}

   @Test   //R
   public void cartTotalPriceDecreasesCorrectly(){
      
   }

   
   @Test   //PASS
   public void cartIsCorrectlyAppendedToSession() throws Exception {
      Assert.assertTrue(this.productList.size()> 0);
      // String cartID = this.cart.getCartID();	
      Assert.assertTrue(cartID!=null);

      given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
      given(cartService.getCartInSession()).willReturn(this.cart);

      //setCartSession returns the Cart.
      this.mockMvc.perform(get("/cart/setCartSession/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.[0]cartID", is(this.cart.getCartID())))         //should this be array?
	 .andExpect(jsonPath("$[0].products.size()", is(productList.size())));

      //getSessionCart returns as expected from SESSION.
      this.mockMvc.perform(get("/cart/getSessionCart/"))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.[0]cartID", is(this.cart.getCartID())))
	 .andExpect(jsonPath("$[0].products.size()", is(productList.size())));
      
   }
       
   @Test  //PASS
   public void getCartByIDShouldReturnCorrectCart() throws Exception {
      //String cartID = cart.getCartID();	
      
      given(cartService.getCartByCartID(cartID)).willReturn(cart);
      this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(cart.getCartID())));
		
   }
   
   @Test //PASS
   public void shouldFetchAllCartProducts() throws Exception {

      //String cartID = cart.getCartID();	

      
      given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
      this.mockMvc.perform(get("/cart/getCart/{this.cart.getCartID()}", this.cart.getCartID()))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(this.cart.getCartID())))
	 .andExpect(jsonPath("$[0].products.size()", is(productList.size())));  
	
      //given(userService.mergeCart(any(Cart.class))).willAnswer((invocation.getArgument(0)));
      //-> invocation.getArgument(0));
	
      given(cartService.getAllCartProducts(cartID)).willReturn(productList);	
      this.mockMvc.perform(get("/cart/getAllCartProducts/{cartID}", this.cart.getCartID()))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.size()", is(productList.size())));  
   }
   
   @Test  // PASS
   public void cartReturnsCorrectTotalPrice() throws Exception {
      
      given(cartService.getCartByCartID(cartID)).willReturn(this.cart);
      this.mockMvc.perform(get("/cart/getCart/{this.cart.getCartID()}", this.cart.getCartID()))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(this.cart.getCartID())))
	 .andExpect(jsonPath("$[0].products.size()", is(productList.size())))
         .andExpect(jsonPath("$.[0]cartValue", is(cart.getCartValue())));

      Assert.assertEquals(cart.getCartValue(), 6000);
   }
   
   @Ignore  //tests that cart products increment and correct product is added.
   public void shouldUpdateCartWithNewProducts() throws Exception {
      int cartValue = cart.getCartValue();
      
      String pcode = productList.get(0).getPcode();
      Product prod = productList.get(0); 
      String pcode2 = productList.get(1).getPcode();
      Product prod2 = productList.get(1); 
      
      given(cartService.getCartByCartID(cartID)).willReturn(cart);
      given(orderSystemService.getProductByPcode(pcode)).willReturn(prod);

      //add a product to the cart...
      this.mockMvc.perform(put("/cart/addProductToCart/{pcode}", pcode)
			   .param("cartID", "cart303"))      
	 .andExpect(status().isOk())
	 //product size is 1 + the current array.
	 .andExpect(jsonPath("$.products.size()", is(4))) 
	 .andExpect(jsonPath("$.products").isArray())
	 //expect correct product code, name, desc, etc...

         //ensure product price is incrementing rationally.
	 .andExpect(jsonPath("$.cartValue", is(cartValue + prod.getSellingPrice())));

      //test for 0 values giving false positives.
      Assert.assertTrue(prod.getSellingPrice() + cartValue != cartValue);

      //add another.
      this.mockMvc.perform(put("/cart/addProductToCart/{pcode}", pcode2)
			   .param("cartID", "cart303"))      
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$.products.size()", is(5)))
	 .andExpect(jsonPath("$.cartValue", is(cart.getCartValue())));
      
      // correctProductAddedToCart
   } 

}
