package test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;

import org.junit.Test;
import org.junit.Ignore;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import model.Product;
import model.Products;
import model.Cart;
import service.OrderSystemService;
import controller.OrderSystemController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
    org.hamcrest:hamcrest We use hamcrest for writing assertions on the response. We can use a variety of Matchers to validate if the response is what we expect.
    org.springframework:spring-test contains MockMvc and other test classes which we can use to perform and validate requests on a specific endpoint.
    org.mockito:mockito-core mocking framework for mocking data.
    com.jayway.jsonpath:json-path-assert Using jsonPath() we can access the response body assertions, to inspect a specific subset of the body. We can use hamcrest Matchers for asserting the value found at the JSON path.*/


//@ExtendWith(MockitoExtension.class)
//@Import(Config.class)
public class MyTest {
private final List<Product> products = new ArrayList<Product>();    
   
    private MockMvc mvc;

    @Mock
    private OrderSystemService orderSystemService;

    @InjectMocks
    private OrderSystemController orderSystemController;

    // This object will be magically initialized by the initFields method below.
    //private JacksonTester<> jsonSuperHero;

    //    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
	//        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
	//      mvc = MockMvcBuilders.standaloneSetup(orderSystemController)
	    //                .setControllerAdvice(new SuperHeroExceptionHandler())
	    //  .addFilters(new SuperHeroFilter())
	//      .build();
    }


    @Before
    public void init(){
        //MockitoAnnotations.initMocks(this);
        //mockMvc = MockMvcBuilders
	//  .standaloneSetup(orderSystemController)
	        //.addFilters(new CORSFilter())
	//	.addFilters(new CORSFilter())
	//   .build();
	Product product = new Product();
	/*product.setId(1l);
	  product.setFirstName("Chris");
	  product.setLastName("Schaefer");*/
	products.add(product);
    }  
    /*
By annotating the UserService with the @Mock annotation, we can return mocked data when we call a method from this service. Using the @InjectMocks annotation, we can inject the mocked service inside our UserController. Before each test, we must initialize these mocks using the MockitoAnnotations#initMocks(this).

The MockMvc is initialized using the MockMvcBuilders#standaloneSetup(...).build() method. Optionally we can add filters, interceptors or etc. using the .addFilter() or .addInterceptor() methods.
    */
    @Test
    public void testGetAllProducts() throws Exception {
	
	//ContactService contactService = mock(ContactService.class);
	//OrderSystemService orderSystemService = mock(OrderSystemService.class);
	//when(contactService.findAll()).thenReturn(contacts);
	//when(orderSystemService.getAllProducts()).thenReturn(products);
	// ContactController contactController = new ContactController();
	//OrderSystemController orderSystemController = new OrderSystemController();
	// ReflectionTestUtils.setField(contactController, "contactService", contactService);
	//ReflectionTestUtils.setField(orderSystemController, "orderSystemService", orderSystemService);
	//       orderSystemService,  orderSystemService
	//ExtendedModelMap uiModel = new ExtendedModelMap();
	//ExtendedModelMap uiModel = new ExtendedModelMap();
	//uiModel.addAttribute("contacts", contactController.listData());
	//uiModel.addAttribute("products", orderSystemController.getAllProducts());
      	//Contacts modelContacts = (Contacts) uiModel.get("contacts");
	//Cart modelProducts =  (Cart) uiModel.getProducts("products");
	
	//List<Product> productlist = orderSystemController.getAllProducts();  
	
	//assertEquals(1, productlist.size());

	
    }

    /*       
    @Test
    public void deleteProduct() throws Exception {
	
	//ContactService contactService = mock(ContactService.class);
	OrderSystemService orderSystemService = mock(OrderSystemService.class);
	//when(contactService.findAll()).thenReturn(contacts);
	when(orderSystemService.deleteProduct()).thenReturn(products);
	// ContactController contactController = new ContactController();
	OrderSystemController orderSystemController = new OrderSystemController();
	// ReflectionTestUtils.setField(contactController, "contactService", contactService);
	ReflectionTestUtils.setField(orderSystemController, "orderSystemService", orderSystemService);
	//       orderSystemService,  orderSystemService
	//ExtendedModelMap uiModel = new ExtendedModelMap();
	ExtendedModelMap uiModel = new ExtendedModelMap();
	//uiModel.addAttribute("contacts", contactController.listData());
	uiModel.addAttribute("products", orderSystemController.getAllProducts());
      	//Contacts modelContacts = (Contacts) uiModel.get("contacts");

	
	List<Product> productlist = orderSystemController.getAllProducts();  
	
	assertEquals(1, productlist.size());


    }

    */
    
    @Ignore
    public void testCreate() {
	/*final Contact newContact = new Contact();
	newContact.setId(999l);
	newContact.setFirstName("Rod");
	newContact.setLastName("Johnson");
	ContactService contactService = mock(ContactService.class);
	when(contactService.save(newContact)).thenAnswer(new Answer<Contact>() {
		public Contact answer(InvocationOnMock invocation) throws Throwable {
		    contacts.add(newContact);
		    return newContact;
		}
	    });
	
	ContactController contactController = new ContactController();
	ReflectionTestUtils.setField(contactController, "contactService",
				     contactService);
	Contact contact = contactController.create(newContact);
	assertEquals(Long.valueOf(999l), contact.getId());
	assertEquals("Rod", contact.getFirstName());
	assertEquals("Johnson", contact.getLastName());
	assertEquals(2, contacts.size()); */
    }
}
