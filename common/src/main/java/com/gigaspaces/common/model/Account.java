package com.gigaspaces.common.model;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceRouting;

@SpaceClass
public class Account {
	
	private Integer id;
	
	private Long balance;
	
	private String firstName;
	
	private String lastName;

	public Account(){
	}
	
	public Account(Integer id, Long balence, String firstName, String lastName){
		this.id = id;
		this.balance = balence;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	@SpaceRouting
	@SpaceId(autoGenerate=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balence) {
		this.balance = balence;
	}

	
	
	 public String toString() {
	        return "id:" + id + " balance:" + balance;
	    }

	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	
}
