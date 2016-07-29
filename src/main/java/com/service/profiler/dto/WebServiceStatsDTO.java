package com.service.profiler.dto;

import java.util.Date;

public class WebServiceStatsDTO {
	
	private String key;
	private String request;
	private String response;
	private String status;
	private Date entry;
	private Date exit;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getEntry() {
		return entry;
	}
	public void setEntry(Date entry) {
		this.entry = entry;
	}
	public Date getExit() {
		return exit;
	}
	public void setExit(Date exit) {
		this.exit = exit;
	}

}
