package core;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import demo1.MyNameBeanRemote;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class clientTest extends Application{

	private MyNameBeanRemote bean = null;
	private static clientTest instance = null;
	private InitialContext context = null;
	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub	 
			
	}
	
	   protected clientTest() {
	      // Exists only to defeat instantiation.
	    	  skeleton();
	   }
	   
	   public static clientTest getInstance() {
	      if(instance == null) {
	    	  instance = new clientTest();
	      }
	      return instance;
	   }

	private void skeleton() {
		Properties props = new Properties();
		//use the jboss factory for context to lookup the EJB remote methods 
		props.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		//URL is the jboss server; port 8080 is jboss default for remote corba access 
		props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		//below statement triggers the creation of a EJBClientContext containing a EJBReceiver capable of handling the EJB invocations 
		props.put("jboss.naming.client.ejb.context", "true");
		try {
		  //create and save context as instance var
		  context = new InitialContext(props);
		  //grab ref to bean’s remote interface
		  bean = (MyNameBeanRemote) context.lookup("MyEJB/MyNameBean!demo1.MyNameBeanRemote");
		} catch (NamingException e) {
		  e.printStackTrace();
		  Platform.exit();
		}
	}
	
	public String callGetName() {
		System.out.println("callGetName called");
		return bean.getName();
	}

	public void callSetName(String str) {
		System.out.println("callSetName called");
		bean.setName(str);
	}
	
	public int callLogin(String usr, String psw) {
		System.out.println("callLogin called");
		return bean.login(usr, psw);
	}
	
	public boolean callAccess(int sessionId,String func) {
		System.out.println("callAccess called");
		return bean.accessControl(sessionId, func);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
