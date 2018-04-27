package authentication;

import core.clientTest;
import javafx.application.Application;
import javafx.stage.Stage;

public class User{
	public static int sessionId;
	
	private static int access;
	
	private static User instance = null;
	
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	private User() {
		sessionId = -1;
		access = -1;
	}
	
	
	 public static User getInstance() {
	      if(instance == null) {
	    	  instance = new User();
	      }
	      return instance;
	   }
	 
	public static void setUser(int sid, int accs) {
		sessionId = sid;
		access = accs;
	}
	 
	public int checkAccess() {
		return access;
	}
	
	public int getSession() {
		return sessionId;
	}


}
