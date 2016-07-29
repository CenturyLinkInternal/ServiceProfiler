package com.service.profiler.store;

public class DbStore {
	
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	public static String getDriver() {
		return driver;
	}
	public static void setDriver(String driver) {
		DbStore.driver = driver;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		DbStore.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		DbStore.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		DbStore.password = password;
	}
	
	

}
