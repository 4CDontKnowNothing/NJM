package org.ht.dao;

import java.util.List;
import java.util.Map;

import javax.swing.plaf.metal.OceanTheme;

import org.ht.pojo.Product;

public interface ProductDao extends BaseDao<Object, Product> {

	public void updateProgres(Product product);
	
	public void updateStatus(Product product);
	
	public List<Product> myList(Map<String,Object> map);
	
	/**
	 * @param product
	 * @explain Set and modify the annual interest rate, fundraising end time, repayment time
	 */
	public int setRateAndDeadline(Product product);
	
	//-------------Raised funds due------------------//
	
	public List<Product> todaoqilist();
	
	int  selIsExitsPrimaryKey();
	   
}