package org.ht.dao;

import java.util.List;
import java.util.Map;

import org.ht.pojo.Approveitem;
import org.ht.pojo.Certifrecord;
import org.ht.pojo.Users;

public interface InfromationDao {
	// my account
	public Users query(Map<String, Object> map);

	// account setting 
	public Users find(Map<String, Object> map);

	// Query the certification form
	public List<Approveitem> appquery();

	// add personal identification info
	public int addUsers(Map<String, Object> map);

	// Add authentication information to the authentication information table
	public int addcertifrecord(Certifrecord cer);
	
	//Simulate adding third-party identity information
	public int upucertnum(Map<String, Object> map);

	// change password
	public int updPassword(Map<String, Object> map);
	//Modify mobile phone number
	public int updphone(Map<String, Object> map);
}
