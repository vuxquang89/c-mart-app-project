package com.example.cmart.app.dto;

import java.util.List;

import com.example.cmart.app.entity.DriverEntity;

public class ResponseDTO<T> {

	private String result_status;
	private String result_mess;
	private T result;
	public String getResult_status() {
		return result_status;
	}
	public void setResult_status(String result_status) {
		this.result_status = result_status;
	}
	public String getResult_mess() {
		return result_mess;
	}
	public void setResult_mess(String result_mess) {
		this.result_mess = result_mess;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	
	
}
