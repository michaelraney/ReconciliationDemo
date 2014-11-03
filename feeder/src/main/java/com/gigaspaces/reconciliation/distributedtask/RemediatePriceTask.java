package com.gigaspaces.reconciliation.distributedtask;

import java.util.Date;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.executor.TaskGigaSpace;

import com.gigaspaces.async.AsyncResult;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.common.model.Account;
import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.OrderState;
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.common.model.PaymentState;
import com.gigaspaces.common.utils.AccountUtils;
import com.j_spaces.core.client.SQLQuery;

public class RemediatePriceTask implements DistributedTask<Integer, Long> {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6392204786985649436L;
	
	
	@TaskGigaSpace
	private transient GigaSpace gigaSpace;

	public RemediatePriceTask() {
	}

	public Integer execute() throws Exception {

		System.out.println("---Remediate Price Task Started");
		
		Date threeSecondsAgo = new Date(new Date().getTime() - (3 * 60));

		Payment payments[] = gigaSpace.readMultiple(new SQLQuery<Payment>(
				Payment.class, "state=? AND creationTime < ?",
				PaymentState.REMEDIATE, threeSecondsAgo)
				.setProjections("id", "accountId", "total", "orderId"));
				
		int itemChangeCount = 0;
		
		if (payments != null) {
			
			for (Payment onePayment : payments) {
				Order oneOrder = gigaSpace.readById(Order.class, onePayment.getOrderId());
			
				gigaSpace.change(onePayment,
						    new ChangeSet().set("item", oneOrder.getItem()));
				
				
				Account oneAccount = new Account();
				oneAccount.setId(onePayment.getAccountId());
				
				long paymentTotal = onePayment.getTotal();
				long orderTotal = oneOrder.getTotal();
				
				/***
				 * Refund account
				 */
				if(paymentTotal > orderTotal){
					long accountDifference = paymentTotal - orderTotal;
					AccountUtils.refundAccount(oneAccount, accountDifference, gigaSpace);
				}else{
					long accountDifference = orderTotal - paymentTotal;
					AccountUtils.decrementBalance(oneAccount, accountDifference, gigaSpace);
				}
				
				gigaSpace.change(onePayment,
					    new ChangeSet().set("state", PaymentState.ADJUSTED));
			
				gigaSpace.change(oneOrder,
					    new ChangeSet().set("state", OrderState.ADJUSTED));
			
				itemChangeCount++;
			}
			
		}

		return itemChangeCount;
	}

	@Override
	public Long reduce(List<AsyncResult<Integer>> results) throws Exception {
		long sum = 0;
		for (AsyncResult<Integer> result : results) {
			if (result.getException() != null) {
				System.out.print("--PRICE Task exception:" + result.getException().getMessage());
				throw result.getException();
			}
			sum += result.getResult();
		}
		return sum;

	}
}
