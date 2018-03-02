package Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditTrailModel {
	
	private int id;
	private Date dateAdded;
	private String msg;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
		//DateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss"); df.format(dateAdded)
		
		return dateAdded.toString() +" : "+ msg;
	}
	
}
