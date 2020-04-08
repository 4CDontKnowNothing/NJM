package org.ht.dao;

import java.util.List;

import org.ht.pojo.Limi;

public interface LimitDao {

	//Query the user's permission module based on employee ID
	public List limitByeid(int eid);
	//delete
	public boolean limitdel(Integer eid);
	//add
	public boolean limitadd(Limi limi);
	
	
}
