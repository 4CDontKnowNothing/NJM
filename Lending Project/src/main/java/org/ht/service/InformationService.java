package org.ht.service;

import java.util.List;
import java.util.Map;

import org.ht.pojo.Approveitem;
import org.ht.pojo.Certifrecord;
import org.ht.pojo.Users;

public interface InformationService {
	public Users query(Map<String, Object> map);

	// account settings
	public Users find(Map<String, Object> map);

	// Query the certification form
	public List<Approveitem> appquery();

	// Add ID card information
	public int addUsers(Map<String, Object> map);

	// Add authentication information to the authentication information table
	public int addcertifrecord(Certifrecord cer);
	
	//Simulate adding third-party identity information
	public int upucertnum(Map<String, Object> map);

	// Check if the password is entered correctly based on id
	public int updPassword(Map<String, Object> map);
	//Modify mobile phone number
	public int updphone(Map<String, Object> map);
	
	//Recharge amount
	public int userpay(Map<String, Object > map);
}
