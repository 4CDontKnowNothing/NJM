package org.ht.dao;

import java.util.List;
import java.util.Map;

import org.ht.pojo.Dope;

public interface DopeDao {
	int insert(Dope dope);
	//paging query
	public List<Dope> findDope(Map<String, Object> map);
	//query total rows
	public List total();
	//delete
	public void batchDeletes(Integer did);
}
