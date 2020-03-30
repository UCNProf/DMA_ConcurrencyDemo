package model;

import java.nio.ByteBuffer;

public class Customer {

	private int customerId;
	private String name;
	private String latestOrderStatus;
	private byte[] rowversion;
	
	public Customer(int customerId, String name, String latestOrderStatus, byte[] rowversion) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.latestOrderStatus = latestOrderStatus;
		this.rowversion = rowversion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLatestOrderStatus() {
		return latestOrderStatus;
	}

	public void setLatestOrderStatus(String latestOrderStatus) {
		this.latestOrderStatus = latestOrderStatus;
	}

	public int getCustomerId() {
		return customerId;
	}
	
	public byte[] getRowversion() {
		return rowversion;
	}

	@Override
	public String toString() {

		return "Customer: "+ name + ", Rowversion: "+ ByteBuffer.wrap(rowversion).getLong();
	}	
	
	
}
