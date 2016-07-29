package com.service.profiler.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.service.profiler.dto.WebServiceStatsDTO;


public class WebServiceStatsStore {
	
	private static Map<String, List<WebServiceStatsDTO>> webServiceStats=new HashMap<String, List<WebServiceStatsDTO>>();
	
	public static synchronized List<WebServiceStatsDTO> getWebServiceStats(String key) {
		return webServiceStats.get(key);
	}
	
	public static synchronized void addWebServiceStats(String key, WebServiceStatsDTO stats) {
		List<WebServiceStatsDTO> webServiceStatsList=getWebServiceStats(key);
		
		if(webServiceStatsList==null) {
			webServiceStatsList=Collections.synchronizedList(new ArrayList<WebServiceStatsDTO>());
			webServiceStats.put(key, webServiceStatsList);
		}
		
		webServiceStatsList.add(stats);
	}	
}
