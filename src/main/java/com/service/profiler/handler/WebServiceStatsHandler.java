package com.service.profiler.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import javax.xml.ws.soap.SOAPFaultException;

import com.service.profiler.processor.WebServiceStatsProcessor;
import com.service.profiler.store.ConfigStore;
import com.service.profiler.store.StateStore;
import com.service.profiler.util.ConfigUtil;


public class WebServiceStatsHandler implements SOAPHandler<SOAPMessageContext> {
	
	static Map<Integer, Map<String, Long>> serviceMap=new HashMap<Integer, Map<String,Long>>();
	static Map<Long,Long> threadMap = new ConcurrentHashMap<Long, Long>();
	Long threadId = Thread.currentThread().getId();
	
	
	static {
		initializeConfig();
	}
	
	private static void initializeConfig() {
		try {
			ConfigUtil.INSTANCE.initializeConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public boolean handleMessage(SOAPMessageContext context) {			
		final String key=getKey(context);
		String direction=getDirection(context);
		SOAPMessage soapMessage = context.getMessage();
		try {
				if (StateStore.shouldProcess(key, direction)) {
					if (direction.equals("OUTBOUND")) {
						if(threadMap.get(threadId)!= null){
							 if ((System.currentTimeMillis() - threadMap.get(threadId)) >= ConfigStore.getServiceTimeOut() ){
								 System.out.println("waited for long period");
								 threadMap.remove(threadId);
								 List<String> reqRes = printContent(context, key);
								 processStats(context, key,reqRes, "TIMEDOUT",serviceMap);
								 generateSOAPErrMessage(soapMessage, "service is timedout");
							 } else {
								    System.out.println("threadId in outbound " + threadId);
								    
								 	List<String> reqRes = printContent(context, key);
								 	String response = reqRes.get(1);
								 	if (response.contains("<serviceReturn>")) {
								 		String pattern1 = "<serviceReturn><returnCode>";
									 	String pattern2 = "</returnCode></serviceReturn>";
									 	String regexString = Pattern.quote(pattern1) + "(.*?)" + Pattern.quote(pattern2);
									 	Pattern pattern = Pattern.compile(regexString);
									 	Matcher matcher = pattern.matcher(response);
									 	if (matcher.find()) {
									 		  String textInBetween = matcher.group(1); 
									 		  if(ConfigStore.getServiceErrorCode().contains(textInBetween)){
									 			 processStats(context, key,reqRes, "failure",serviceMap);
									 		  } else {
									 			 processStats(context, key,reqRes, "SUCCESS",serviceMap);
									 		  }
									 		  
									 	} else {
									 		processStats(context, key,reqRes, "SUCCESS",serviceMap);
									 	}
								 	} else {
								 		processStats(context, key,reqRes, "SUCCESS",serviceMap);
								 	}
								 	
							 }
							
						}
				     } else if (direction.equals("INBOUND")) {
						          captureContent(context, key);
								  setTimestamp(context);
								  System.out.println("threadId in inbound " + threadId);
				    	          long entryDate = System.currentTimeMillis();
				    	          threadMap.put(threadId, entryDate);
				    }
			}
			
		} catch (Exception e) {
			generateSOAPErrMessage(soapMessage, "Generic error");
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		String key=getKey(context);
		if(StateStore.shouldProcess(key, "OUTBOUND")) {
			processStats(context, key,null, "FAILURE",serviceMap);
		}
		
		return true;
	}
	

	@Override
	public void close(MessageContext context) {

	}

	@Override
	public Set getHeaders() {
		return null;
	}
	
	private String getDirection(SOAPMessageContext context) {
		Boolean outbound = (Boolean) context.get(context.MESSAGE_OUTBOUND_PROPERTY);
	    return outbound?"OUTBOUND":"INBOUND";	
	}

	private static String getAttribute(SOAPMessageContext context, String attribute) {
		QName qname = (QName) context.get(attribute);
	    return (qname==null)?"":qname.getLocalPart();
	}
	
	private void setCount(SOAPMessageContext context, long count) {
		context.put("STAR-COUNT", count);
	}
	
	private static long getCount(SOAPMessageContext context){
		long count=(Long) context.get("STAR-COUNT");		
		return count;
	}
	
	private void setTimestamp(SOAPMessageContext context) {	
		context.put("STAR-TIMESTAMP", new Date());
	}
	
	private static Date getTimestamp(SOAPMessageContext context) {
		Date timestamp=(Date) context.get("STAR-TIMESTAMP");		
		return timestamp;
	}
	
	private static void processStats(SOAPMessageContext context, String key, List<String> reqRes,String status,Map<Integer,Map<String, Long>> serviceMap) {
		Date exit=new Date();
		Date entry=getTimestamp(context);
		
		WebServiceStatsProcessor.processStats(key,reqRes, status, entry, exit,serviceMap);
	}
	private static synchronized void processTimedOutStats(Date entryTime, String key, List<String> reqRes,String status,Map<Integer,Map<String, Long>> serviceMap) {
		Date exit=new Date();
		Date entry=entryTime;
		
		WebServiceStatsProcessor.processStats(key,reqRes, status, entry, exit,serviceMap);
	}
	
	private String captureContent(SOAPMessageContext context, String key) {
		String request = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			context.getMessage().writeTo(baos);
			request=baos.toString();
			long count=StateStore.storeRequestContent(key, request);
			setCount(context, count);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	private static List<String> printContent(SOAPMessageContext context, String key) {
		List<String> list = new ArrayList<String>();
		try {
			long count=getCount(context);
			String request=StateStore.retrieveRequestContent(key, count);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			context.getMessage().writeTo(baos);
			String response=baos.toString();
			list.add(request);
			list.add(response);
			System.out.println("REQUEST1: \n"+request+"\n RESPONSE1: \n"+response);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static String getKey(SOAPMessageContext context) {
		String wsdlService=getAttribute(context, SOAPMessageContext.WSDL_SERVICE);
		String wsdlInterface=getAttribute(context, SOAPMessageContext.WSDL_INTERFACE);
		String wsdlPort=getAttribute(context, SOAPMessageContext.WSDL_PORT);
		String wsdlOperation=getAttribute(context, SOAPMessageContext.WSDL_OPERATION);
		return wsdlService+" - "+wsdlInterface+" - "+wsdlPort+" - "+wsdlOperation;
	}
	
	public static synchronized void updateServices(Map<Integer,Map<String, Long>> map1){
		System.out.println("*******************" + map1.toString());
		if(map1 != null){
			serviceMap =  map1;
		}
	}

	private void generateSOAPErrMessage(SOAPMessage message, String reason) {
	       try {
	          SOAPBody soapBody = message.getSOAPPart().getEnvelope().getBody();
	          SOAPFault soapFault = soapBody.addFault();
	          soapFault.setFaultString(reason);
	       }
	       catch(SOAPException e) { }
	    }
}
