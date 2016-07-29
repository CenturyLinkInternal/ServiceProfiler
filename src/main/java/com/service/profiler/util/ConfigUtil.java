package com.service.profiler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.service.profiler.store.ConfigStore;
import com.service.profiler.store.DbStore;

public enum ConfigUtil {
	INSTANCE;
	
	public synchronized void initializeConfig() throws IOException {
		Properties properties=loadConfig("application.properties");
		Properties dbProperties=loadConfig("dbConfig.properties");
		storeConfig(properties);
		storeDbConfig(dbProperties);
	}
		
	private void storeDbConfig(Properties dbProperties) {
		DbStore.setDriver(dbProperties.getProperty("db.driverName"));
		DbStore.setUrl(dbProperties.getProperty("db.url"));
		DbStore.setUsername(dbProperties.getProperty("db.userName"));
		DbStore.setPassword(dbProperties.getProperty("db.password"));
	}

	public Properties loadConfig(String file) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream(file);  
		
		Properties properties = new Properties();
		properties.load(inputStream);
		
		return properties;
	}
	
	
	private void storeConfig(Properties properties) {
		ConfigStore.setEnabled(Boolean.valueOf(properties.getProperty("star.enabled").trim()));
		ConfigStore.setApplicationName(String.valueOf(properties.getProperty("star.application.name").trim()));
		ConfigStore.setApplicationEnv(String.valueOf(properties.getProperty("star.application.env").trim()));
		ConfigStore.setApplicationInstance(String.valueOf(properties.getProperty("star.appliation.instance").trim()));
		ConfigStore.setBufferEnabled(Boolean.valueOf(properties.getProperty("star.buffer.enabled").trim()));
		ConfigStore.setBufferSize(Integer.valueOf(properties.getProperty("star.buffer.size").trim()));
		ConfigStore.setSampleEnabled(Boolean.valueOf(properties.getProperty("star.sample.enabled").trim()));
		ConfigStore.setSampleFrequency(Integer.valueOf(properties.getProperty("star.sample.frequency").trim()));
		ConfigStore.setRecorderEnabled(Boolean.valueOf(properties.getProperty("star.recorder.enabled").trim()));
		ConfigStore.setRecorderURL(String.valueOf(properties.getProperty("star.recorder.url").trim()));
		ConfigStore.setServiceTimeOut(Integer.valueOf(properties.getProperty("serviceResponseTimedOut").trim()));
		ConfigStore.setServiceErrorCode(String.valueOf(properties.getProperty("serviceErrorCodes").trim()));
	}
}
