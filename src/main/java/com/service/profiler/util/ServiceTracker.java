package com.service.profiler.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.service.profiler.handler.WebServiceStatsHandler;


public class ServiceTracker {
	
	/*@Autowired
	WebServiceStatsHandler webServiceStatsHandler;
	*/
	private Map<Integer, Map<String, Long>> serviceMap=new HashMap<Integer, Map<String,Long>>();
	private Date startDate;
	
	
	
	public Map<Integer, Map<String, Long>> getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(Map<Integer, Map<String, Long>> serviceMap) {
		this.serviceMap = serviceMap;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void start(){
		setStartDate(new Date());
	}
	
	/*public Map<String,Long> stop(Object obj,String serviceName){
		Date endDate = new Date();
		serviceMap.put(serviceName, (endDate.getTime()-startDate.getTime()));
		WebServiceStatsHandler.updateServices(serviceMap);
		return serviceMap;
	}*/
	public void stop(Object obj,String serviceName){
		stop(obj,serviceName,false);
		}
	
	public void stop(Object obj,String serviceName,boolean update){
		Date endDate = new Date();
		Integer uiqueId = System.identityHashCode(obj);
		
		if(serviceMap.get(uiqueId) != null){
			HashMap<String, Long> childMap = (HashMap<String, Long>) serviceMap.get(uiqueId);
			childMap.put(serviceName, (endDate.getTime()-getStartDate().getTime()));
			serviceMap.put(uiqueId, childMap);
		}else{
			HashMap<String, Long> childMap = new HashMap<String, Long>();
			childMap.put(serviceName, (endDate.getTime()-getStartDate().getTime()));
			serviceMap.put(uiqueId, childMap);
		}
		
		if(update){
			System.out.println("inside updating to webservice");
			WebServiceStatsHandler.updateServices(serviceMap);
		}
	}

}
