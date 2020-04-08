package org.ht.service.impl;

import java.util.List;
import java.util.Map;

import org.ht.dao.ApproveDao;
import org.ht.pojo.Approveitem;
import org.ht.service.ApproveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Name: ApproveServiceImpl
 * @Description:The implementation layer of the service set by the authentication item
 */
@Service
@Transactional
public class ApproveServiceImpl implements ApproveService {
	@Autowired
	private ApproveDao approveDao;

	/**
	 * Description：Obtain certification items according to the conditions, 
	 * and if the conditions are empty, put back all certification items
	 * 
	 * @return List
	 */
	@Override
	public List<Approveitem> queryApproves(Map<String, Object> map) {
		// TODO Auto-generated method stub
		List<Approveitem> approveitems = approveDao.queryApproves(map);
		return approveitems;
	}

	/**
	 * Description：Modify certification
	 * 
	 * @param map
	 */
	@Override
	public void updateApproves(Map<String, Object> map) {
		// TODO Auto-generated method stub
		approveDao.updateApproves(map);
	}

	/**
	 * Description：Add authentication
	 * 
	 * @param map
	 */
	@Override
	public void addApproves(Map<String, Object> map) {
		// TODO Auto-generated method stub
		approveDao.addApproves(map);
	}

}
