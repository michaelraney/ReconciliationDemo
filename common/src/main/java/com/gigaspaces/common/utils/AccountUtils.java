package com.gigaspaces.common.utils;

import org.openspaces.core.GigaSpace;

import com.gigaspaces.client.ChangeSet;
import com.gigaspaces.common.model.Account;
import com.gigaspaces.query.IdQuery;

public class AccountUtils {
	private static final String BALANCE_FIELD = "balance";
	
	public static void incrementBalance(Integer accountId, Long balance, GigaSpace gigaSpace){
		IdQuery<Account> accountQuery = new IdQuery<Account>(Account.class, accountId);
		
		gigaSpace.change(accountQuery,
				new ChangeSet().increment(BALANCE_FIELD, balance));
	}
	public static void incrementBalance(IdQuery<Account> accountQuery, Long balance, GigaSpace gigaSpace){
		gigaSpace.change(accountQuery,
				new ChangeSet().increment(BALANCE_FIELD, balance));
	}
	public static void incrementBalance(Account account, Long balance, GigaSpace gigaSpace){
		gigaSpace.change(account,
				new ChangeSet().increment(BALANCE_FIELD, balance));
	
	}
	public static void decrementBalance(Account account, Long balance, GigaSpace gigaSpace){
		gigaSpace.change(account,
				new ChangeSet().decrement(BALANCE_FIELD, balance));
	}
	
	public static void refundAccount(Account account, Long balance, GigaSpace gigaSpace){
		decrementBalance(account, balance, gigaSpace);
	}
	public static void chargeAccount(Account account, Long balance, GigaSpace gigaSpace){
		decrementBalance(account, balance, gigaSpace);
	}
	public static void processPayment(Account account, Long balance, GigaSpace gigaSpace){
		incrementBalance(account, balance, gigaSpace);
	}
}
