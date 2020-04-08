package org.ht.dao;

import java.util.List;
import java.util.Map;

import org.ht.pojo.Approveitem;

/**
 * @Name: ApproveDao
 * @Description:Dao layer for authentication item setting
 */
public interface ApproveDao {
	/**
	 * Description：Obtain certification items according to the conditions, 
	 * and if the conditions are empty, put back all certification items
	 * 
	 * @param map
	 * @return List
	 */
	public List<Approveitem> queryApproves(Map<String, Object> map);

	/**
	 * Description：Add new certification
	 * 
	 * @param map
	 */
	public void addApproves(Map<String, Object> map);

	/**
	 * Description：Modify certification
	 * 
	 * @param map
	 */
	public void updateApproves(Map<String, Object> map);
}
