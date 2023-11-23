package user_registration.model;

/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
*/

import java.io.Serializable;

//@Entity
//@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	//@Column(name = "username")
	 private String username;
	//@Column(name = "password")
	 private String password;
	//@Column(name = "mobileNo")
	 private String mobileNo;
	//@Column(name = "emailId")
	 private String emailId;
	 
	 
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
