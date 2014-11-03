package com.gigaspaces.processor;


import org.openspaces.core.GigaSpace;
import org.openspaces.events.EventDriven;
import org.openspaces.events.EventTemplate;
import org.openspaces.events.adapter.SpaceDataEvent;
import org.openspaces.events.polling.Polling;
import org.springframework.beans.factory.annotation.Autowired;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.common.model.Account;
import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.OrderState;
import com.gigaspaces.common.utils.AccountUtils;

@EventDriven @Polling
public class OrderProcessor {

    private GigaSpace gigaSpace;

    @Autowired
    public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}
    
    @EventTemplate
    Order unprocessedOrder() {
        Order template = new Order();
        template.setState(OrderState.NEW);
        return template;
    }
    
    @SpaceDataEvent
    public Order processOrder(Order order) {
    	
    	Account accountTemplate = new Account();
    	accountTemplate.setId(order.getAccountId());
    	
    	long orderTotal = order.getTotal();
    	
    	AccountUtils.decrementBalance(accountTemplate, orderTotal, gigaSpace);
		
    	order.setState(OrderState.PROCESSED);

       return order;
    }
    

}
