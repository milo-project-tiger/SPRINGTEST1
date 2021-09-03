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

public class CartViewTests{
    @Ignore
    public void getCartTest(){
	
	try{
	    URL url = new URL(TestConfig.URL+"cart/getAllProducts/");
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
}
