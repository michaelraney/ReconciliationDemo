package com.gigaspaces.processor;



import java.util.Date;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;
import org.openspaces.core.GigaSpace;

import com.gigaspaces.common.model.Account;
import com.gigaspaces.common.model.Item;
import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.OrderState;
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.common.model.PaymentState;


/**
 * Integration test for the Processor. Uses similar xml definition file (ProcessorIntegrationTest-context.xml)
 * to the actual pu.xml. Writs an unprocessed Data to the Space, and verifies that it has been processed by
 * taking a processed one from the space.
 */
public class ProcessorIntegrationTest extends AbstractDependencyInjectionSpringContextTests {

    protected GigaSpace gigaSpace;
    
    public ProcessorIntegrationTest() {
        setPopulateProtectedVariables(true);
    }

    protected void onSetUp() throws Exception {
        gigaSpace.clear(null);
    }
    
    protected void onTearDown() throws Exception {
        gigaSpace.clear(null);
    }
    
    protected String[] getConfigLocations() {
        return new String[]{"com/gigaspaces/processor/ProcessorIntegrationTest-context.xml"};
    }
    
    public void testVerifyProcessing() throws Exception {
    	  // write the data to be processed to the Space
    
    	Account account = new Account(1, 0l, "MIKE", "MIKE");
        gigaSpace.write(account);
    	
        Item item = new Item("XYZ", "XYZ", 5l);
       
    	// write the data to be processed to the Space
        System.out.println("write order");
        Order order = new Order(1, new Date(), item, 1l, 5l);
        gigaSpace.write(order);
        
        Thread.sleep(1000);
     
       // write the data to be processed to the Space
        System.out.println("write payment");
        Payment payment = new Payment(1, order.getId(),item, 1l, 5l, new Date());
        gigaSpace.write(payment);
        
        Thread.sleep(1000);
        // create a template of the processed data (processed)
        Order template = new Order();
        template.setState(OrderState.COMPLETE);
        Order result = (Order)gigaSpace.take(template, 1000);
        
        Account accountTemplate = new Account();
        accountTemplate.setBalance(0l);
        Account accountResult = (Account)gigaSpace.take(accountTemplate, 1000);
        
        assertNotNull("Account not Found", accountResult);
        assertEquals("Account balance:", new Long(0), accountResult.getBalance());
        
        // wait for the result
      
        // verify it
        assertNotNull("No data object was processed", result);
        assertEquals("Order was not processed:"+ result.getState(), OrderState.COMPLETE, result.getState());
        
    }
    public void testVerifyUnpaid() throws Exception {
  	  // write the data to be processed to the Space
  
  	Account account = new Account(1, 0l, "MIKE", "MIKE");
      gigaSpace.write(account);
     Item item = new Item( "XYZ", "XYZ", 5l);
     
  	// write the data to be processed to the Space
      System.out.println("write order");
      Order order = new Order(1, new Date(), item, 10l, 5l);
      gigaSpace.write(order);
      
      Thread.sleep(1000);
   
     // write the data to be processed to the Space
      System.out.println("write payment");
      Payment payment = new Payment(1, order.getId(),item, 10l, 6l, new Date());
      gigaSpace.write(payment);
      
      Thread.sleep(1000);
      // create a template of the processed data (processed)
      Order template = new Order();
      template.setState(OrderState.REMEDIATE);
      Order result = (Order)gigaSpace.take(template, 1000);
     
      Payment paytemplate = new Payment();
      paytemplate.setState(PaymentState.REMEDIATE);
      Payment payresult = (Payment)gigaSpace.take(paytemplate, 1000);
      // verify it
      
      Account accountTemplate = new Account();
      accountTemplate.setBalance(1l);
      Account accountResult = (Account)gigaSpace.take(accountTemplate, 1000);
      
      assertNotNull("Account not Found", accountResult);
      assertEquals("Account balance:", new Long(1), accountResult.getBalance());
      
      
      assertNotNull("Order is not null", result);
      assertEquals("Orderstate:", OrderState.REMEDIATE, result.getState());
      
      assertNotNull("Payment is not null", payresult);
      assertEquals("PaymentState:", PaymentState.REMEDIATE, payresult.getState());
      
  }
    public void testVerifyOrfaned() throws Exception {
    	  // write the data to be processed to the Space
    
    	Account account = new Account(1, 0l, "MIKE", "MIKE");
        gigaSpace.write(account);
        Item item = new Item( "XYZ", "XYZ", 5l);
        
    	// write the data to be processed to the Space
        System.out.println("write order");
        Order order = new Order(1, new Date(),item, 1l, 5l);
        gigaSpace.write(order);
        
        Thread.sleep(1000);
     
      
        // create a template of the processed data (processed)
        Order template = new Order();
        template.setState(OrderState.PROCESSED);
        
        Order result = (Order)gigaSpace.take(template, 1000);
       
        Account accountTemplate = new Account();
        accountTemplate.setBalance(-5l);
        Account accountResult = (Account)gigaSpace.take(accountTemplate, 1000);
        
        assertNotNull("Account not Found", accountResult);
        
        assertEquals("Account balance:", new Long(-5), accountResult.getBalance());
        
        // verify it
        assertNotNull("Order is not null", result);
        assertEquals("Orderstate:", OrderState.PROCESSED, result.getState());
        
      
        
    }
    
}
