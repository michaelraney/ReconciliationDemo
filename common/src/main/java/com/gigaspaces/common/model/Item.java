package com.gigaspaces.common.model;

import java.io.Serializable;



public class Item implements Serializable{


	private static final long serialVersionUID = -7942506745443825895L;
	
	private String name;
	private String symbol;
	private Long price;
	
	public Item(){}
	
	public Item(String name, String symbol, Long price){
		this.name= name;
		this.symbol = symbol;
		this.price = price;
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
	public String toString() {
		return " symbol[" + symbol + "] + name[" + name + "]  price[" + price + "]";
	}

	

	
}
