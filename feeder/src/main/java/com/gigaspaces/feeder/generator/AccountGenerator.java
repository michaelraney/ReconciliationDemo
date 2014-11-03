package com.gigaspaces.feeder.generator;

import java.util.concurrent.ThreadLocalRandom;

import com.gigaspaces.common.model.Account;

public class AccountGenerator {
	private static Integer numberOfAccounts = 1000;
	
	public static Account[] generateAccounts(){
		return generateAccounts(numberOfAccounts);
	}
	public static Account[] generateAccounts(Integer numberOfAccounts){
		
		ThreadLocalRandom random = ThreadLocalRandom.current();
		Integer accountCounter=0;
		Account accountEntries[] = new Account[numberOfAccounts];
		
		while (accountCounter < numberOfAccounts) {
			
			String firstName = nameGenerator(random);
			String lastName = nameGenerator(random);
			
			Account account = new Account(accountCounter,
					0l, firstName, lastName);
			
			accountEntries[accountCounter] = account;
			
			accountCounter++;
		}
		return accountEntries;
	}
	public static Integer RandomAccountNumber(ThreadLocalRandom random){
		int num = random.nextInt(0, numberOfAccounts-1);
		
		return num;
	}
	public static String nameGenerator(ThreadLocalRandom random){
		
		String[] names = getAllNames();
		
		int nameIndex = random.nextInt(0, names.length-1);
		
		return names[nameIndex];
	}
	private static String[] getAllNames(){
		return new String[] {"Christopher", "Columbus", "Thomas", "Jefferson", "George", "Washington",
							"Michael", "Steven", "Kevin", "Heidi", "Ellen", "Doris", "Thomas", "Ashely",
							"Trish", "William", "John", "Patrick", "James", "Smith", "Brown", "Jordan", 
							"Skip", "Sam", "Dan", "Bob", "Franklin" };
	}
	
}
