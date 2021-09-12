package test;

import service.OrderSystemService;
import controller.OrderSystemController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Product;
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
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderSystemService orderSystemService;

    private List<Product> productList;

    @Before
    public void setup() {
	MockitoAnnotations.initMocks(this);
	mockMvc = MockMvcBuilders.standaloneSetup(new OrderSystemController(orderSystemService))
            //.setHandlerExceptionResolvers(exceptionResolver()) //crutial for standaloneSetup of MockMVC
            .build();

       this.productList = new ArrayList<>();
       this.productList.add(new Product("Product1"));
       this.productList.add(new Product("Product2"));
       this.productList.add(new Product( "Product3"));
    }
    
    @BeforeEach
    public void setUpData() {    
	//  objectMapper.registerModule(new ProblemModule());
        //objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @After
    public void tearDown(){
	this.productList = null;
    }
    
    @Test
    public void shouldFetchAllProducts() throws Exception {
       
        given(orderSystemService.getAllProducts()).willReturn(productList);
	
        this.mockMvc.perform(get("/getAllProducts/"))
	    .andExpect(status().isOk())
	.andExpect(jsonPath("$.size()", is(productList.size())));  
	// Assert.assertTrue(productList.size() > 0);
	}
   
    @Test
    public void shouldReturnDefaultMessage() throws Exception {
	this.mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
	.andExpect(content().string(containsString("hello")));
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
