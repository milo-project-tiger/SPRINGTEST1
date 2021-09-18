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
import model.Receipt;
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
public class ReceiptService {

    @Autowired
    private SessionFactory sessionFactory;
    
    //generic
    public void save(Object object){
	sessionFactory.getCurrentSession().save(object);
    }
    
    public void saveReceipt(Receipt receipt){
	sessionFactory.getCurrentSession().saveOrUpdate(receipt);
    }
    
    public void mergeReceipt(Receipt receipt){
	sessionFactory.getCurrentSession().merge(receipt);
    }

    //URGENT add D.O.B
    public Receipt getReceiptByPassPhrase(String passPhrase, String DOB){
	Query query = sessionFactory.getCurrentSession().createQuery("from Receipt where passPhrase=:passPhrase and"
                                    + " DOB=:DOB");
	query.setString("passPhrase", passPhrase);
	query.setString("DOB", DOB);
	Receipt receipt = (Receipt) query.uniqueResult();
	return receipt;
    } 

    //There is an issue with deserialization. Consider bring this up with Dr Minh.
    /*    public List<Product> getAllCartProducts(String cartID){
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
	} */
}
