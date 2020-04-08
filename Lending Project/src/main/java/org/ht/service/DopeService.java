package org.ht.service;

import java.util.List;
import java.util.Map;

import org.ht.pojo.Dope;

public interface DopeService {
	int insert(Dope dope);
	//paging query
	public List<Dope> findDope(Map<String, Object> map);
	//total row query
	public List total();
	//delete
	public void batchDeletes(Integer did);
}
