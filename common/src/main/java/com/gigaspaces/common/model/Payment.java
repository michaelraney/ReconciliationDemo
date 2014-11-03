package com.gigaspaces.common.model;

import java.util.Date;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceIndex;
import com.gigaspaces.annotation.pojo.SpaceRouting;
import com.gigaspaces.metadata.index.SpaceIndexType;

@SpaceClass
public class Payment {

	private String id;
	
	private Integer accountId;
	
	private String orderId;

	private Item item;
	
	private Long itemQuantity;
	
	private Long total;
	
	private PaymentState state;
	
	
	private Date creationTime;
	
	
	public Payment(){
		
	}
	
	public Payment(Integer accountId, String orderId, Item item, Long itemQuantity, Long total, Date creationTime){
		this.accountId = accountId;
		this.orderId = orderId;
		this.item = item;
		this.itemQuantity = itemQuantity;
		this.total = total;
		this.setCreationTime(creationTime);
		this.state = PaymentState.NEW;
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	

	@SpaceIndex(type=SpaceIndexType.BASIC)
	public PaymentState getState() {
		return state;
	}

	public void setState(PaymentState state) {
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
		return "id:"+id +" account:" + accountId + " order:" + orderId;
	}
	
	
}
