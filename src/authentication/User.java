package authentication;

import core.clientTest;
import javafx.application.Application;
import javafx.stage.Stage;

public class User extends Application{
	private static int sessionId;
	
	private static int access;
	
	private static User instance = null;
	
	protected User(int id, int access) {
		this.sessionId = id;
		this.access = access;
	}
	
	
	 public static User getInstance() {
	      if(instance == null) {
	    	  instance = new User(-1,-1);
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

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
