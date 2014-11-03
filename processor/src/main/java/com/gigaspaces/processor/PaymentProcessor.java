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
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.common.model.PaymentState;
import com.gigaspaces.common.utils.AccountUtils;

@EventDriven
@Polling
public class PaymentProcessor {

	private GigaSpace gigaSpace;

	@Autowired
	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}

	@EventTemplate
	Payment unprocessedOrder() {
		Payment template = new Payment();
		template.setState(PaymentState.NEW);
		return template;
	}

	@SpaceDataEvent
	public Payment processPayment(Payment payment) {
		Order order = null;
		
		try{
			order = gigaSpace.readById(Order.class, payment.getOrderId());
	
			long paymentTotal = payment.getTotal();
			long orderTotal = order.getTotal();
	
			AccountUtils.incrementBalance(payment.getAccountId(), paymentTotal, gigaSpace);
	
			if (paymentTotal != orderTotal) {
				gigaSpace.change(order,
						new ChangeSet().set("state", OrderState.REMEDIATE));
				payment.setState(PaymentState.REMEDIATE);
			} else {
	
				gigaSpace.change(order,
						new ChangeSet().set("state", OrderState.COMPLETE));
	
				payment.setState(PaymentState.ACCEPTED);
			}
		}catch(NullPointerException ex){
			System.out.println("PAYMENT PROCESS--- + " + payment);
		}
		return payment;
	}
}
