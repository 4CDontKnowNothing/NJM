package org.ht.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ht.pojo.Employee;

public interface EmployeeDao {

	// Unconditionally query all bonus pages
	List<Employee> findlist();

	// add new employee
	int insert(Employee emp);

	// login
	Employee empLogin(@Param("ename") String ename, @Param("epassword") String epassword);
	
	//add employee
	int add(Employee emp);
	
	//delete
	int del(Integer did);
	
	Employee toupd(Integer did);
	int upd(Employee emp);
	
	//vague query
	List<Employee> selectlike(String ename);
	
 	
}
