package model;
import java.util.List;
import java.util.ArrayList;

public class Products{

    private List<Product> products = new ArrayList<Product>();
    
    public Products(){

    }

    public List<Product> getProducts(){
	return this.products;
    }

}
