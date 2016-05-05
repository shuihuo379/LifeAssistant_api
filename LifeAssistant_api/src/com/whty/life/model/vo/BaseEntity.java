package com.whty.life.model.vo;

public class BaseEntity {
	private String status;
	private String message; //错误提示,成功返回""
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
