package com.myandroidproject.childlocator.properties;

public class Constants {
	public static final String REGISTER_URL = "http://10.0.2.2:9999/ChildTracker_php/webService/RegisterService.php";
	public static String SUCCESS = "SUCCESS";
	public static String LOGIN_ERROR = "LOGIN_ERROR";
	public static String WEB_SERVICE_ERROR = "WEB_SERVICE_ERROR";
	public static String JSON_ERROR = "JSON_ERROR";
	public static String LOGIN_URL = "http://10.0.2.2:9999/ChildTracker_php/webService/LoginService.php";
	public static String Username_Exists ="Username Already Exist";
	public static String DBError ="Unale to insert DBError";
	public static String Username_Password_Null ="Username or Password cannot be null";
	public static String Username_Password_length="UserName and Password must contain minimum 6 letters"; 
	public static final String ADD_Dependent = "http://10.0.2.2:9999/ChildTracker_php/webService/DependentService.php";
	public static final String Service_URL = "http://10.0.2.2:9999/ChildTracker_php/webService/UpdateService.php";
	
	
	private Constants() {
		
		
	}

}
