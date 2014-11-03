package com.gigaspaces.common.model;


import java.util.Date;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class Order {

	private String id;

	private OrderState state;

	private Integer accountId;
	
	private Date creationTime;
	
	private Item item;
	
	private Long itemQuantity;
	
	private Long total;
	
	
	
	public Order() {
	}

	public Order(Integer accountId, Date creationTime, Item item, Long itemQuantity, Long total) {
		this.accountId = accountId;
		this.creationTime = creationTime;
		this.itemQuantity = itemQuantity;
		this.total = total;
		this.state = OrderState.NEW;
	}
	
	
	@SpaceId(autoGenerate=true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@SpaceRouting
	public Integer getAccountId() {
		return accountId;
	}
 
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	@SpaceIndex(type=SpaceIndexType.EXTENDED)
	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public Long getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Long itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String toString() {
		return "id:" + id + " account:" + accountId + " state:" + state;
	}


}
