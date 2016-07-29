package com.service.profiler.store;

import java.util.HashMap;
import java.util.Map;

public class StateStore {

	private static Map<String, String> requestContents=new HashMap<String, String>();
	private static Map<String, Long> requestCounts=new HashMap<String, Long>();
	private static Map<String, Integer> sampleCounts=new HashMap<String, Integer>();
	
	private static synchronized void incrementRequestCount(String key) {
		requestCounts.put(key, getRequestCount(key)+1);
	}
	
	private static synchronized long getRequestCount(String key) {
		Long requestCount=requestCounts.get(key);
		return (requestCount==null)?1:requestCount+1;
	}
	
	public static synchronized long storeRequestContent(String key, String content) {
		long count=getRequestCount(key);
		incrementRequestCount(key);
		requestContents.put(key+"-"+count, content);
		return count;
	}
	
	public static synchronized String retrieveRequestContent(String key, long count) {
		return requestContents.get(key+"-"+count);
	}
	
	private static synchronized void incrementSampleCount(String key) {
		sampleCounts.put(key, getSampleCount(key)+1);
	}
	
	private static synchronized void resetSampleCount(String key) {
		sampleCounts.put(key, 1);
	}
	
	private static synchronized int getSampleCount(String key) {
		Integer sampleCount=sampleCounts.get(key);
		return (sampleCount==null)?1:sampleCount+1;
	}
	
	public static synchronized boolean shouldProcess(String key, String direction) {
		Boolean shouldProcess = null;
		
		if(ConfigStore.isEnabled()) {
			shouldProcess=shouldConsider(key, direction);
		} else {
			shouldProcess=false;
		}
		
		return shouldProcess;
	}
	
	public static synchronized boolean shouldConsider(String key, String direction) {
		Boolean shouldConsider = null;
		
		if(ConfigStore.isSampleEnabled()) {
			if(getSampleCount(key)>=ConfigStore.getSampleFrequency()) {
				if(direction.equals("INBOUND"))
				resetSampleCount(key);
				shouldConsider=true;
			} else {
				if(direction.equals("INBOUND"))
				incrementSampleCount(key);
				shouldConsider=false;
			}
		}
		
		return shouldConsider;
	}
	
	public static synchronized boolean shouldRecord(String key) {
		Boolean shouldRecord = null;
		
		if(ConfigStore.isRecorderEnabled()) {
			shouldRecord=!shouldBuffer(key);
		} else {
			shouldRecord=false;
		}
		
		return shouldRecord;
	}
	
	public static synchronized boolean shouldBuffer(String key) {
		Boolean shouldBuffer = null;
		
		if(ConfigStore.isBufferEnabled()) {
			if(WebServiceStatsStore.getWebServiceStats(key).size()>=ConfigStore.getBufferSize()) {
				shouldBuffer=false;
			} else {
				shouldBuffer=true;
			} 
		} else {
			shouldBuffer=true;
		}
		
		return shouldBuffer;
	}

}
