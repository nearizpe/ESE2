package demo1;

import javax.ejb.Remote;

@Remote
public interface MyNameBeanRemote {
	public String getName();
	public void setName(String newName);
}
