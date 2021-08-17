package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.StockNote;
import model.Student;
import model.Product;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.Before;
import org.junit.After;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.io.IOException;

public class AdminViewTests {   

    @Before
    public void setUp() throws IOException, InterruptedException
    {

	//add five distinct products to database.
	ProcessBuilder processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"bubbles of powerpuff girls\",\"pcode\":\"555\"}' http://localhost:8080/addProduct/");
	Process process = processBuilder.start();	

	processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"Crowley Thoth Tarot Deck\",\"pcode\":\"666\"}' http://localhost:8080/addProduct/");
	process = processBuilder.start();	
	
	processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"Magick Oranges\",\"pcode\":\"777\"}' http://localhost:8080/addProduct/");
	process = processBuilder.start();	
	
	processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"Humanely acquired Human Organs from a US prison\",\"pcode\":\"888\"}' http://localhost:8080/addProduct/");
	process = processBuilder.start();	

	processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"Hoodoo Goopher Dust\",\"pcode\":\"999\"}' http://localhost:8080/addProduct/");
	process = processBuilder.start();	
	Thread.sleep(2);
    }
    
    @Ignore
    public void tearDown() throws IOException {

	//delete all products added by test.
	ProcessBuilder processBuilder = new ProcessBuilder();	
	processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/555");
	Process process = processBuilder.start();

	processBuilder = new ProcessBuilder();	
	processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/666");
        process = processBuilder.start();	
	
	processBuilder = new ProcessBuilder();	
	processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/777");
        process = processBuilder.start();	
	
	processBuilder = new ProcessBuilder();	
	processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/888");
        process = processBuilder.start();	

	processBuilder = new ProcessBuilder();	
	processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/999");
        process = processBuilder.start();	
	
    }

    @Ignore
    public void productCodeIsNotAcceptedForNewProductIfNotUnique(){
	

    }
    
    
    @Ignore
    public void getProductByIdReturnsCorrectProductOrNullIfNone( ){
	
	try {
	    
	 URL url = new URL("http://localhost:8080/getProductById/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	Assert.assertEquals(3,3);
    }
    
    @Ignore //Search for product by id.. to verify it exists in DB.. then delete.. see that it does not return.
    public void  ProductAddedtoProductListReturnedCorrectlyByID(){
	//PATH:   deleteProduct/ID
	
	try{
	    URL url = new URL(TestConfig.URL+"getAllProducts/");
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
	    Assert.assertTrue(products.size() > 2);
	    String found = "";
	    for ( Product product : products){
		if (product.pcode!= null){
		    if (product.pcode.contains("555")){
			    found =  product.getPcode();
		    }
		}
	    }
	    //product with PCode "555" is in the Product List
	    Assert.assertEquals("555",found);
	    
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    @Ignore
    public void ProductDeletedByIdNoLongerReturnsInProductList(){
	
	//ProductAddedtoProductListReturnedCorrectlyByID();
	//Delete product "555" with Delete method via deleteProduct/{id} route
	try{
	    
	    ProcessBuilder processBuilder = new ProcessBuilder();	
	    processBuilder.command("bash", "-c","curl  -X DELETE http://localhost:8080/deleteProduct/555");
	    Process process = processBuilder.start();
	    
	    URL url = new URL(TestConfig.URL+"getAllProducts/");
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
	    
	    String found = "";
	    for ( Product product : products){
		if (product.pcode!= null){
		    if (product.pcode.contains("555")){
			found =  product.getPcode();
		    }
		    
		}
	    }    	    	
	    //product with PCode "555" is no longer found in Product List
	    Assert.assertFalse(found.contains("555"));    
	    
	    //restore the product to keep the teardown method consistent.
	    
	    processBuilder = new ProcessBuilder();
	    processBuilder.command("bash", "-c","curl -H \"Accept: application/json\" -H \"Content-Type: application/json\" -X PUT -d '{\"name\":\"bubbles of powerpuff girls\",\"pcode\":\"555\"}' http://localhost:8080/addProduct/");
	    process = processBuilder.start();	
	

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}	
	
    }
    
    @Ignore
    public void getProductListReturnsCorrectNumberResults(){
	
	try{
	    URL url = new URL(TestConfig.URL+"getAllProducts/");
	    HttpURLConnection httpURLConnection = (HttpURLConnection)
		url.openConnection();
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	    String line = "";
	    StringBuilder stringBuilder = new StringBuilder();
	    
	    while((line = bufferedReader.readLine()) !=null){
		stringBuilder.append(line);
	    } 
	    Gson gson = new Gson();
	    String json = stringBuilder.toString();
	    List<Product> products = gson.fromJson(json, new TypeToken<List<Product>>(){}.getType());
	    
	    Assert.assertEquals(products.size(), 5);
	    
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }   
           
    @Ignore    //using process builder and shell to add test data with curl.
    public void productAddedMeetsAcceptanceCriteriaForEntry() throws IOException {

	String queryname =  "{\"name\":\"magic oranges\"}";
	String query =  "{\"name\":\"magic oranges\"," +
	    "\"description\":\"oranges that make you invulnerable to EMFs.\""
	    + ",\"sellingPrice\":\"900\"}";
	
	/*private String name; private String model; private String description; private int sellingPrice;  */
	ProcessBuilder processBuilder = new ProcessBuilder();
	//call curl from java to add product...
	/* processBuilder.command("bash", "-c", "curl -X PUT -d " + queryname
	   +  " http://localhost:8080/addProduct/");        */
	Process process = processBuilder.start();
	//test... 
	/*i. product is added successfully.  

	ii. does not add empty product name  

	iii. description is visible..
	*/
	
	
    }
    
    @Test  //after placing stocknote the correct stocknote can be fetched by ID..
    public void placeStockNoteTest() throws IOException{

	ProcessBuilder processBuilder = new ProcessBuilder();
	processBuilder.command("bash", "-c","curl -X PUT -d \"product_id=555&qty=8000\" " +
			       "http://localhost:8080/placeStock/");
	Process process = processBuilder.start();

	//get stocknotebyID returns correct stockNote..

	try{
	    URL url = new URL(TestConfig.URL+"getAllStockNotes/");
	    HttpURLConnection httpURLConnection = (HttpURLConnection)
		url.openConnection();
	    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
	    String line = "";
	    StringBuilder stringBuilder = new StringBuilder();
	    
	    while((line = bufferedReader.readLine()) !=null){
		stringBuilder.append(line);
	    }
	    
	    Gson gson = new Gson();
	    String json = stringBuilder.toString();
	    List<StockNote> stocknotes = gson.fromJson(json, new TypeToken<List<StockNote>>(){}.getType());

	    Assert.assertTrue(stocknotes.size()>0);
	    //qty is correct.. 
	    Assert.assertEquals(8000, stocknotes.get(0).getQTY());
	    //product id.. correct.. 
	    Assert.assertEquals("555", stocknotes.get(0).getPcode());
	    //product name correct.   //test relation between Product and StockNote
	    Assert.assertTrue(stocknotes.get(0).getProduct().getName().contains("Bubbles"));   //fails.
	    	    
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }  
}

/*
  Unit Test Cases for routes relevant to Admin View... 
  
  routes:   (OrderSystemController)
  
  "/addProduct/"  //add product to known products  
  
  test... 
  i. product is added successfully.
  ii. does not add empty product name     
  iii. 
    
  "/placeStock/"  //commit a product to the stock list .. with a quantity
  
  i.

  "getAllProducts/"   // return all known products
  
  i. expected number of products are displayed
  ii. 
  
  "products/{id}"    //delete products from knowledge
  
  i. the product is deleted  
  
  //"getAllStockedProducts"  
*/
