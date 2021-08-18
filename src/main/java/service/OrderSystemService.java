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
import model.StockNote;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//imp"ort javax.annotation.Resource;
import java.util.List;

@Transactional
@Service
public class OrderSystemService {

   @Autowired
   private SessionFactory sessionFactory;
    
   public void setSessionFactory(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   //generic
   public void save(Object object){
      sessionFactory.getCurrentSession().save(object);
   }
    
   public void saveProduct(Product product){
      sessionFactory.getCurrentSession().saveOrUpdate(product);
   }
   
   public void saveStockNote(StockNote stocknote){
      sessionFactory.getCurrentSession().saveOrUpdate(stocknote);       
   }
 
   public Product getProductByPcode(String code){
      //return (Person)sessionFactory.getCurrentSession().get(Person.class, id);
      Query query = sessionFactory.getCurrentSession().createQuery("from Product where code = :code ");
      query.setString("code", code);
      
      return (Product) query.uniqueResult();
   }
    
   public List<Product> getAllProducts(){
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Product.class);
      return criteria.list();
   }

    public List<StockNote> getAllStockNotes(){  
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(StockNote.class);
      return criteria.list();
   }

   public void deleteProduct(String code){
      Query query = sessionFactory.getCurrentSession().createQuery("from Product where code=:code");
      query.setString("code", code);
      Product product = (Product) query.uniqueResult();
      sessionFactory.getCurrentSession().delete(product);
   }

   public void deleteStockNote(String pcode){
      Query query = sessionFactory.getCurrentSession().createQuery("from StockNote where pcode=:pcode");
      query.setString("pcode", pcode);
      StockNote stocknote = (StockNote) query.uniqueResult();
      sessionFactory.getCurrentSession().delete(stocknote);
   }

}

	/*  SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(
                "select * from  product_detail, where  p.provider_id");
	        return query.list(); 
	*/
    //}
    /*
    public void saveProvider(Provider provider){
	   sessionFactory.getCurrentSession().saveOrUpdate(provider);
    }
    */
    //    @Override
    /*
    //Find products?
    @Transactional(readOnly=true)
    public List findjoin(){
	String hql =
	    "select distinct(p), pr.tradingName, od.qty from Product p inner join p.provider as pr inner join pr.orders as o inner join o.orderDetails od where p.sellingPrice > :price";
	//String hql = "from Product p, Provider pr where p.sellingPrice > :price AND p.id =pr.id";
	Query query =  sessionFactory.getCurrentSession().createQuery(hql);
	query.setInteger("price",19);
	List results = query.list();
	return results;
    }

    @Transactional(readOnly=true)
    public List findjoin2(){
	Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Provider.class);
	//Criteria crit = session.createCriteria(Provider.class);
	Criteria prdCrit = criteria.createCriteria("products");
	criteria.add(Restrictions.lt("sellingPrice",19));
	criteria.setResultTransformer( DistinctRootEntityResultTransformer.INSTANCE );
	//criteria.add(Restrictions.isNotNull("name"));
	/*ProjectionList projList = Projections.projectionList();
	 projList.add(Projections.property("tradingName"));
	 projList.add(Projections.property("id"));
	 //projList.add(Projections.property("description"));
	 criteria.setProjection(projList); */
	
    //List results = criteria.list();
    //	return results;
    //}


    
    //projection for a view...
    /* @Transactional(readOnly=true)
    public List<Object[]> findAllWithDetail(){
       Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Provider.class);
       ProjectionList projList = Projections.projectionList();
       projList.add(Projections.property("tradingName"));
       projList.add(Projections.property("id"));
       //projList.add(Projections.property("description"));
       criteria.setProjection(projList);
       //List<Object[]>
	   
       return criteria.list();
	  /*
	return sessionFactory.getCurrentSession().getNamedQuery(
	"Provider.findAllWithDetail2").list(); */
	//getNamedQuery("Product.findAllWithDetail").list();
  //    }
    
/*
    public List<Order> getAllOrders(){
        Criteria criteria =
	    sessionFactory.getCurrentSession().createCriteria(Order.class);
        return criteria.list();
    }
*/
/*  
    public Provider getProviderById(int id){
	//return (Person)sessionFactory.getCurrentSession().get(Person.class, id);
	 Query query = sessionFactory.getCurrentSession().createQuery("from Provider where id = :id ");
	 query.setInteger("id", id);
	 
	 return (Provider) query.uniqueResult();
   		 
	 //SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select * from person where id = "+ id);
	// return query.list();
    }

*/
     
  
//}

/*
    public List<Person> getPersonByName(String name){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
        criteria.add(Restrictions.like("name",name, MatchMode.ANYWHERE));
        return criteria.list();
    }

    public List<Person> getPersonByName2(String name){
        Query query = sessionFactory.getCurrentSession().createQuery("from Person where name like :name");
        query.setString("name", "%"+name+"%");
        return query.list();
    }

    public List<Person> getPersonByName3(String name){
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select * from newperson");
        return query.list();
    }

    //courses
    public Course getCourseById(int id){
        return (Course) sessionFactory.getCurrentSession().get(Course.class, id);
    }
    */

	/*Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Provider.class);
	//Criteria crit = session.createCriteria(Provider.class);
	Criteria prvCrit = criteria.createCriteria("products");
	prvCrit.add(Restrictions.gt("sellingPrice",19));
	prvCrit.setResultTransformer( DistinctRootEntityResultTransformer.INSTANCE );
	criteria.setResultTransformer( DistinctRootEntityResultTransformer.INSTANCE );
	//criteria.add(Restrictions.isNotNull("name"));
	
	*/
    /*
    public void saveAbility(Ability ability){
        sessionFactory.getCurrentSession().save(ability);
    }
    */

    /*
    public List<Person> getAllPersons(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Person.class);
        return criteria.list();
    }

    public void deletePerson(Person person){
        sessionFactory.getCurrentSession().delete(person);
    }
    */
    
    
