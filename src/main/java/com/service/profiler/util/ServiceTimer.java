/**
 * 
 */
package com.service.profiler.util;

import java.util.Date;

import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 * @author ab33767
 *
 */
public class ServiceTimer {
	
	private String status;
	private long time;
	private SOAPMessageContext context;
	private String inboundRequest;
	private Date entryTime;
	private String key;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public SOAPMessageContext getContext() {
		return context;
	}
	public void setContext(SOAPMessageContext context) {
		this.context = context;
	}
	public String getInboundRequest() {
		return inboundRequest;
	}
	public void setInboundRequest(String inboundRequest) {
		this.inboundRequest = inboundRequest;
	}
	
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
