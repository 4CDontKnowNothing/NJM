package org.ht.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ht.dao.UsersDao;
import org.ht.pojo.Users;
import org.ht.service.UsersService;
import org.ht.util.CreateRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersdao;

	

	public int insert(Users users) {
		return usersdao.insert(users);
	}

	// login
	public Users byNameFindUsers(@Param("unickname") String unickname, @Param("upassword") String upassword) {
		// Query by user name
		Users u = usersdao.byNameFindUsers(unickname, upassword);
		if (u == null || u.equals("")) {
			// the input user does not exist
			u = null;
			return u;
		} else {
			// User exists, check password
			if (!u.getUpassword().equals(upassword)) {
				u = null;
			}
			return u;
		}

	}

	
	public List<Users> queryUserslimits(Map<String, Object> map) {
		return usersdao.queryUserslimits(map);
	}


	public List<Users> userList() {
		return usersdao.userList();
	}
	/**
	 * @param map
	 * @explain users paging query
	 */
	
	@Override
	public List<Users> queryUser(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return usersdao.queryUser(map);
	}

	
	public List<Users> queryUserslimits(
			String unickname) {
		return usersdao.queryUserslimitss(unickname);
	}

}
