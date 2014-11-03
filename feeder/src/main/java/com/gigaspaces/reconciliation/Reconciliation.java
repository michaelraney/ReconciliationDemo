package com.gigaspaces.reconciliation;


import com.gigaspaces.async.AsyncFuture;
import com.gigaspaces.reconciliation.distributedtask.OrphanTask;
import com.gigaspaces.reconciliation.distributedtask.RemediatePriceTask;

import org.openspaces.core.GigaSpace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Reconciliation {

	private GigaSpace gigaSpace;

	@Autowired
    public void setGigaSpace(GigaSpace gigaSpace) {
		this.gigaSpace = gigaSpace;
	}
	
	
	@Scheduled(fixedRate = 30000)
	public void run() {
		
		System.out.println("---RECON Started");
		
		AsyncFuture<Long> orphanTask = gigaSpace.execute(new OrphanTask());	
		
		try{
			System.out.println("***ORDER TASK RESULT***" + orphanTask.get());
		}catch(Exception ex){
			System.out.println("***ORDER TASK RESULT*** ERRROR");
		}
		
		AsyncFuture<Long> priceAdjustmentTask = gigaSpace.execute(new RemediatePriceTask());	
		
		try{
			System.out.println("***PRICE TASK RESULT***" + priceAdjustmentTask.get());
		}catch(Exception ex){
			System.out.println("***PRICE TASK RESULT*** ERRROR");
		}
	}	

}
