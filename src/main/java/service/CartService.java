package service;

import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import model.Product;
import model.Cart;
import model.Customer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import javax.servlet.http.*; 


/*
  N Lankshear. s3529801. SEPT M2.
 */

@Transactional
@Service
public class CartService {

    @Autowired
    private SessionFactory sessionFactory;
    
    
    @Autowired 
    private HttpSession httpSession;

    
    public void setSessionFactory(SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;
    }  


    
    //generic
    public void save(Object object){
	sessionFactory.getCurrentSession().save(object);
    }
    
    public Cart saveCart(){
       String cartID = "cartID";
       Cart cart = new Cart();
       
       //simple random cartID generator for default user.
       Random r = new Random();
       int cartIDint = r.nextInt(20000);
       if (cartIDint < 0 ){
	   cartIDint = cartIDint * -1;}
       cartID += Integer.toString(cartIDint); 
       cart.setCartID(cartID);
       saveCart(cart);
       return cart;
    }
    
    public void saveCart(Cart cart){
	sessionFactory.getCurrentSession().saveOrUpdate(cart);
    }
    
    public void mergeCart(Cart cart){
	sessionFactory.getCurrentSession().merge(cart);
    }
    
    public String setCartInSession(Cart cart){
       httpSession.setAttribute("cartSESSION", cart);
       httpSession.setAttribute("name","milk");
       return httpSession.getId();
    }

    public Cart getCartInSession(){
	Cart cart = (Cart) httpSession.getAttribute("cartSESSION");
	//return httpSession.getAttribute("name");
	return cart;
	//return httpSession.getId();
    }
    
    public void removeFromCart(long productId) {
       //Cart cart = getShoppingCartInSession();
       //shoppingCart.removeItemFromCart(productId);
       //updateCartInSession(shoppingCart);
    }

    
    
    public void saveCustomer(Customer customer){
	
	sessionFactory.getCurrentSession().saveOrUpdate(customer);
    }

    //
    public List<Product> getAllCartProducts(String cartID){
	Query query = sessionFactory.getCurrentSession().createQuery("products from Cart where cartID=:cartID ");
	query.setString("cartID", cartID);
	List<Product> products = query.list();
	return products;
    }
    
    public Cart getCartByCartID(String cartID){
	Query query = sessionFactory.getCurrentSession().createQuery("from Cart where cartID=:cartID ");
	query.setString("cartID", cartID);
	Cart cart = (Cart) query.uniqueResult();
	return cart;
    }

    /*
    public boolean addProductToCart( ){	
    }
    */

    /*
    public List<Product> getAllCartProducts(){
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
	return criteria.list();
    }
    */
    /*
    public boolean removeProductFromCartByPCode(String code){
	Query query = sessionFactory.getCurrentSession().createQuery("from Product where code=:code ");
	query.setString("code", code);
	Product product = (Product) query.uniqueResult();
	sessionFactory.getCurrentSession().delete(product);
    }
    */
    /*
    public boolean deleteCart(    ) {}
    */
}

