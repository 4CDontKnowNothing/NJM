package org.ht.service;

import java.util.List;

import org.ht.pojo.Limi;

public interface LimitService {

	//Query the user's permission module based on employee ID
		public List limitByeid(int eid);
		//delete
		public boolean limitdel(Integer eid);
		//add
		public boolean limitadd(Limi limi);
}
