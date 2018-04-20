package com.myCompany.myProject.pages;

import com.myCompany.util.SetUp;

public class Login extends Page {
	
	public static String title = "Login | Salesforce";
	
	private String or_id_txtlogin="username";
	private String or_id_txtpassword = "password";
	private String or_id_btnLogin = "Login";
	
	private String[] txtSSOUsername = {NAME,"pf.username"};
	private String[] txtSSOPassword = {NAME,"pf.pass"};
	private String[] btnSignIn = {XPATH,"//a[contains(text(),'Sign In')]"};
	public Login setUsername(String username) {
		setText(ID,or_id_txtlogin, username);
		return this;
	}
	
	/**
	 * Set the value of username defined in the properties file
	 * @return current page
	 */
	public Login setUsername() {
		return this.setUsername(SetUp.getPropertyValue("username"));
	}
	
	public Login setPassword(String password) {
		setText(ID , or_id_txtpassword, password);
		return this;
	}
	
	/**
	 * Set the value of the password defined in the properties file
	 * @return current page
	 */
	public Login setPassword() {
		return this.setPassword(SetUp.getPropertyValue("password"));
	}
	
	/**
	 * Click login button
	 * @return Welcome page
	 */
	public HomePage clickLogin() {
		clickElement(ID , or_id_btnLogin);
		return (new HomePage());
	}
	
	public HomePage login() {
		this.setUsername();
		this.setPassword();
		this.clickLogin();
		return (new HomePage());
	}
	
	public HomePage login(String username,String password) {
		this.setUsername(username);
		this.setPassword(password);
		this.clickLogin();
		return (new HomePage());
	}
	
	public void loginSSO(){
		deleteCookies();
		navigate_to(SetUp.getPropertyValue("urlSSO"));
		if(isExists_NoDelay(txtSSOUsername)){
			setText(txtSSOUsername, SetUp.getPropertyValue("usernameSSO"));
			setText(txtSSOPassword,SetUp.getPropertyValue("passwordSSO"));
			clickAndWaitElement(btnSignIn);
		}
	}
	
}
