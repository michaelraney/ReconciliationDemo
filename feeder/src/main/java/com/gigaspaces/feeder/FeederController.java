package com.gigaspaces.feeder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.gigaspaces.common.model.Account;
import com.gigaspaces.common.model.Item;
import com.gigaspaces.common.model.Order;
import com.gigaspaces.common.model.Payment;
import com.gigaspaces.feeder.generator.AccountGenerator;
import com.gigaspaces.feeder.generator.ItemGenerator;

import net.jini.core.lease.Lease;

import org.openspaces.core.GigaSpace;
import org.openspaces.core.SpaceInterruptedException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class FeederController implements InitializingBean, DisposableBean {

	private List<CreateOrderPaymentTask> taskList;

	private GigaSpace gigaSpace;

	private ThreadPoolTaskExecutor taskExecutor;

	public ThreadPoolTaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}

	public void loadAccounts() {
		
		if (gigaSpace.read(new Account()) == null) {
			Account[] accountEntries = AccountGenerator.generateAccounts();
			gigaSpace.writeMultiple(accountEntries, Lease.FOREVER);
			System.out.println("---FEEDER Loaded " + accountEntries.length
					+ "accounts");
		}

	}

	public void afterPropertiesSet() throws Exception {
		System.out.println("---FEEDER Started");

		loadAccounts();

		taskList = new ArrayList<CreateOrderPaymentTask>();

		for (int i = 0; i < taskExecutor.getCorePoolSize(); i++) {
			CreateOrderPaymentTask oneTask = new CreateOrderPaymentTask();
			taskList.add(oneTask);
			taskExecutor.execute(oneTask);
		}

	}

	public void destroy() throws Exception {
		while (taskExecutor.getActiveCount() > 0) {
			for (CreateOrderPaymentTask oneTask : taskList) {
				if (oneTask != null)
					oneTask.cancel();
			}
		}
		taskExecutor.shutdown();
	}

	public class CreateOrderPaymentTask implements Runnable {

		volatile Boolean keepAlive = true;

		int paymentCount = 0;
		long lease = 60000l; 
		
		public void run() {
			ThreadLocalRandom randomInstance = ThreadLocalRandom.current();
			try {
				while (keepAlive) {

					int accountIdForOrder = AccountGenerator.RandomAccountNumber(randomInstance);
					Item itemIdForOrder = ItemGenerator.getRandomItem(randomInstance);
					long quantity = randomInstance.nextLong(1, 10);
					long total = quantity * itemIdForOrder.getPrice();

					Order order = new Order(accountIdForOrder, new Date(),
							itemIdForOrder, quantity, total);

					gigaSpace.write(order, lease);
					Thread.sleep(200);
					
					/***
					 * Change payments
					 */
					int changePayment = randomInstance.nextInt(1, 100);
				
					if (changePayment > 10) {
						Payment payment = new Payment(accountIdForOrder,
								order.getId(), itemIdForOrder, quantity, total, new Date());

						gigaSpace.write(payment, lease);
					}else if(changePayment > 6){
						long oldPrice = itemIdForOrder.getPrice() +1;
						long newPrice = oldPrice +1;
						
						itemIdForOrder.setPrice(newPrice);
						
						long newTotal = quantity * newPrice;
						
						Payment payment = new Payment(accountIdForOrder,
								order.getId(), itemIdForOrder, quantity, newTotal, new Date());

						gigaSpace.write(payment, lease);
					}
					
				}

			} catch (SpaceInterruptedException e) {

			} catch (Exception e) {
				// e.printStackTrace();
			}
		}

		public void cancel() {
			keepAlive = false;
		}

	}

}
