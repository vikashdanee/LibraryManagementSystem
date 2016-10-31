package librarysystem.models;

import java.io.Serializable;

import librarysystem.enums.Role;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8093511372121801726L;
	private String userName; 
	private String userPassword;
	private Role role;
	
	
	public User(String userName, String userPassword, Role role) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.role = role;
	}
	
	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	} 
	
	
	

}
