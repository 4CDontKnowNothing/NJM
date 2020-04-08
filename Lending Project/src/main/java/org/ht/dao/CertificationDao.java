package org.ht.dao;

import java.util.Map;

import org.ht.pojo.Certification;

public interface CertificationDao {
	public String selMoney(Integer uid); //Find out the amount of a user
	public boolean updMoney(Map<String,Object> map);//Modify a user's amount
	public boolean upmoney(Map<String, Object> map);
	public int insert(Certification cer);//Add wallet data
	public Certification select(Integer uid);//Find data
}
