package test;

import service.OrderSystemService;
import service.CartService;
import controller.CartController;

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
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import  org.mockito.MockitoAnnotations;
import  org.mockito.MockitoAnnotations.Mock;

import org.junit.jupiter.api.BeforeEach;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderSystemService orderSystemService;

    @Mock
    private CartService cartService;
    
    private List<Product> productList;

    protected Cart cart;
    
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
    
    @BeforeEach
    public void setUpData() {    
	//  objectMapper.registerModule(new ProblemModule());
        //objectMapper.registerModule(new ConstraintViolationProblemModule());
		//list of products
       this.productList = new ArrayList<>();
       this.productList.add(new Product("Product1"));
       this.productList.add(new Product("Product2"));
       this.productList.add(new Product( "Product3"));

       //create a cart
       this.cart = new Cart();
       // add the products to cart.
       this.cart.setProducts(productList);

    }

    @After
    public void tearDown(){
	this.productList = null;
	this.cart = null;
    }
    
    @Test  //PASS
    public void getCartByIDShouldReturnCorrectCart() throws Exception {
	Cart cartTest = new Cart();
	cartTest.setCartID("cart303");
	String cartID = cartTest.getCartID();	
	Assert.assertTrue(cartID!=null);
	//Assert.assertTrue(this.cart!=null);
	                                             //Optional.of(      );
        given(cartService.getCartByCartID(cartID)).willReturn(cartTest);
	                                    ///getCart/{cartID}
        this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(cartTest.getCartID())));
		 
        /*given(userService.saveCart(any(Cart.class))).willAnswer((invocation) -> invocation.getArgument(0));
	//given(cartService.getAllCartProducts(cartID)).willReturn(productList);
	User user = new User(4L, "newuser1@gmail.com", "pwd#@4sfsS", "Name");

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
       	
       this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	   .andExpect(status().isOk());
	   //.andExpect(jsonPath("$.size()", is(productList.size())));  */
	// Assert.assertTrue(productList.size() > 0);
    }
    @Test //PASS
    public void shouldFetchAllCartProducts() throws Exception {

	this.productList = new ArrayList<>();
	this.productList.add(new Product("Product1"));
	this.productList.add(new Product("Product2"));
	this.productList.add(new Product( "Product3"));
	
	//create a cart
	Cart cartTest = new Cart();
	cartTest.setCartID("cart555");
	String cartID = cartTest.getCartID();	
	Assert.assertTrue(cartID!=null);
	
	// add the products to cart.
	cartTest.setProducts(productList);
	
	given(cartService.getCartByCartID(cartID)).willReturn(cartTest);
	this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(cartTest.getCartID())))
	.andExpect(jsonPath("$[0].products.size()", is(productList.size())));  
	
	//given(userService.mergeCart(any(Cart.class))).willAnswer((invocation.getArgument(0)));
	//-> invocation.getArgument(0));
	
        given(cartService.getAllCartProducts(cartID)).willReturn(productList);	
        this.mockMvc.perform(get("/cart/getAllCartProducts/{cartID}", cartID))
	    .andExpect(status().isOk())
	.andExpect(jsonPath("$.size()", is(productList.size())));  
	// Assert.assertTrue(productList.size() > 0);
	
	
    }
    @Test  //revise
    public void cartReturnsCorrectTotalPrice() throws Exception {

	this.productList = new ArrayList<>();
	this.productList.add(new Product("Product1"));
	this.productList.add(new Product("Product2"));
	this.productList.add(new Product( "Product3"));
	
	//create a cart
	Cart cartTest = new Cart();
	cartTest.setCartID("cart555");
	String cartID = cartTest.getCartID();	
	Assert.assertTrue(cartID!=null);
	
	// add the products to cart.
	cartTest.setProducts(productList);
	
	given(cartService.getCartByCartID(cartID)).willReturn(cartTest);
	this.mockMvc.perform(get("/cart/getCart/{cartID}", cartID))
	 .andExpect(status().isOk())
	 .andExpect(jsonPath("$[0].cartID", is(cartTest.getCartID())))
	.andExpect(jsonPath("$[0].products.size()", is(productList.size())));  
	
	//given(userService.mergeCart(any(Cart.class))).willAnswer((invocation.getArgument(0)));
	//-> invocation.getArgument(0));
	
        given(cartService.getAllCartProducts(cartID)).willReturn(productList);	
        this.mockMvc.perform(get("/cart/getAllCartProducts/{cartID}", cartID))
	    .andExpect(status().isOk())
	.andExpect(jsonPath("$.size()", is(productList.size())));  
	



	//this.mockMvc.perform(get("/cart/createCart/")).andDo(print()).andExpect(status().isOk());

	//.andExpect(content().string(containsString("hello")));
	
    }
    
    @Test
    public void shouldUpdateCartWithNewProducts() throws Exception {
	 /* Long userId = 1L;
        User user = new User(userId, "user1@gmail.com", "pwd$232DD12ff", "Name");
        
	given(userService.findUserById(userId)).willReturn(Optional.of(user));
        given(userService.updateUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));
	 */

	 /*
        this.mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
	     // .content(objectMapper.writeValueAsString(user)))
		.andExpect(status().isOk());
	     //    .andExpect(jsonPath("$.email", is(user.getEmail())))
	 */
     } 
	    
    @Ignore
    public void test1() throws IOException {

        try{
	        URL url = new URL(TestConfig.URL+"products");
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	        String line = "";
	        StringBuilder stringBuilder = new StringBuilder();
	
	        while((line = bufferedReader.readLine()) !=null){
	            stringBuilder.append(line);
	        }
	
	        Gson gson = new Gson();
	        String json = stringBuilder.toString();
	        List<Product> products = gson.fromJson(json, new TypeToken<List<Product>>(){}.getType());
	        //String s = stringBuilder.toString();
	        Assert.assertEquals(products.get(0).getName(), "Thanh");
	        
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
    }
}

}
