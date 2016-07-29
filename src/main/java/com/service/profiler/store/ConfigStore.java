package com.service.profiler.store;

public class ConfigStore {
	
	private static Boolean enabled;
	
	private static String applicationName;
	private static String applicationEnv;
	private static String applicationInstance;
	
	private static Boolean bufferEnabled;
	private static Integer bufferSize;
	
	private static Boolean sampleEnabled;
	private static Integer sampleFrequency;
	
	private static Boolean recorderEnabled;
	private static String recorderURL;
	private static Integer serviceTimeOut;
	private static String serviceErrorCode;
	
	
	
	public static Boolean isEnabled() {
		return enabled;
	}
	public static void setEnabled(Boolean enabled) {
		ConfigStore.enabled = enabled;
	}
	public static String getApplicationName() {
		return applicationName;
	}
	public static void setApplicationName(String applicationName) {
		ConfigStore.applicationName = applicationName;
	}
	public static String getApplicationEnv() {
		return applicationEnv;
	}
	public static void setApplicationEnv(String applicationEnv) {
		ConfigStore.applicationEnv = applicationEnv;
	}
	public static String getApplicationInstance() {
		return applicationInstance;
	}
	public static void setApplicationInstance(String applicationInstance) {
		ConfigStore.applicationInstance = applicationInstance;
	}
	public static Boolean isBufferEnabled() {
		return bufferEnabled;
	}
	public static void setBufferEnabled(Boolean bufferEnabled) {
		ConfigStore.bufferEnabled = bufferEnabled;
	}
	public static Integer getBufferSize() {
		return bufferSize;
	}
	public static void setBufferSize(Integer bufferSize) {
		ConfigStore.bufferSize = bufferSize;
	}
	public static Boolean isSampleEnabled() {
		return sampleEnabled;
	}
	public static void setSampleEnabled(Boolean sampleEnabled) {
		ConfigStore.sampleEnabled = sampleEnabled;
	}
	public static Integer getSampleFrequency() {
		return sampleFrequency;
	}
	public static void setSampleFrequency(Integer sampleFrequency) {
		ConfigStore.sampleFrequency = sampleFrequency;
	}
	public static Boolean isRecorderEnabled() {
		return recorderEnabled;
	}
	public static void setRecorderEnabled(Boolean recorderEnabled) {
		ConfigStore.recorderEnabled = recorderEnabled;
	}
	public static String getRecorderURL() {
		return recorderURL;
	}
	public static void setRecorderURL(String recorderURL) {
		ConfigStore.recorderURL = recorderURL;
	}
	public static Integer getServiceTimeOut() {
		return serviceTimeOut;
	}
	public static void setServiceTimeOut(Integer serviceTimeOut) {
		ConfigStore.serviceTimeOut = serviceTimeOut;
	}
	public static String getServiceErrorCode() {
		return serviceErrorCode;
	}
	public static void setServiceErrorCode(String serviceErrorCode) {
		ConfigStore.serviceErrorCode = serviceErrorCode;
	}
	
}
