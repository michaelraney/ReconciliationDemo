package com.gigaspaces.reconciliation.distributedtask;

import java.util.Date;
import java.util.List;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.executor.DistributedTask;
import org.openspaces.core.executor.TaskGigaSpace;

import com.gigaspaces.async.AsyncResult;
import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.OrderState;
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.common.utils.AccountUtils;
import com.gigaspaces.query.IdQuery;
import com.j_spaces.core.client.SQLQuery;

public class OrphanTask implements DistributedTask<Integer, Long> {

	private static final long serialVersionUID = -8061247481554233702L;

	@TaskGigaSpace
	private transient GigaSpace gigaSpace;

	public OrphanTask() {
	}

	public Integer execute() throws Exception {

		System.out.println("---Orphan Order Task Started");
		
		Date threeSecondsAgo = new Date(System.currentTimeMillis() - (3 * 60));

		Order orders[] = gigaSpace.readMultiple(new SQLQuery<Order>(
				Order.class, "state=? AND creationTime < ?",
				OrderState.PROCESSED, threeSecondsAgo)
				.setProjections("id", "accountId", "total"));

		int orderCount = 0;
		
		for (Order oneOrder : orders) {
			
			Payment paymentTemplate = new Payment();
			paymentTemplate.setOrderId(oneOrder.getId());

			Payment onePayment = gigaSpace.read(paymentTemplate);

			if (onePayment == null) {
				Integer accountId = oneOrder.getAccountId();
				String orderId = oneOrder.getId();
				
				IdQuery<Order> orderQuery = new IdQuery<Order>(Order.class, orderId, accountId);
				
				gigaSpace.change(orderQuery,
						new ChangeSet().set("state", OrderState.ORPHANED));
				
				AccountUtils.incrementBalance(oneOrder.getAccountId(), oneOrder.getTotal() , gigaSpace);
				
				orderCount++;
			}else{
				System.out.print("----PAYMENT FOUND");
			}
		}
	

		return orderCount;
	}

	@Override
	public Long reduce(List<AsyncResult<Integer>> results) throws Exception {
		long sum = 0;
		for (AsyncResult<Integer> result : results) {
			if (result.getException() != null) {
				System.out.print("-ORDER error"+ result.getException().getMessage());
				throw result.getException();
			}
			sum += result.getResult();
		}
		return sum;

	}
}
