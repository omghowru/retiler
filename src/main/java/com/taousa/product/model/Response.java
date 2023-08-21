package com.taousa.product.model;

public class Response {

	private String message;
	private int sttuscode;
	
	
	public Response(String message, int sttuscode) {
		this.message = message;
		this.sttuscode = sttuscode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getSttuscode() {
		return sttuscode;
	}
	public void setSttuscode(int sttuscode) {
		this.sttuscode = sttuscode;
	}
	
}
