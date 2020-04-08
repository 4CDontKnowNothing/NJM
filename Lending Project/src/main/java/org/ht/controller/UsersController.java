package org.ht.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.http.HttpSession;

import org.ht.pojo.Certification;
import org.ht.pojo.Users;
import org.ht.service.CertificationService;
import org.ht.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("users")
public class UsersController {

	@Autowired
	private UsersService usersservice;
	@Autowired
	
	@Resource
	private CertificationService Certificat;

	@RequestMapping("list")
	public String list(Model model,Users users,
			@RequestParam(value = "currpage", required = false) String currpage,
			@RequestParam(value = "unickname", required = false) String unickname) {
		// query all
		int pagerow = 6;// 6 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row
		Map<String, Object> parameters = new HashMap<String, Object>();
		totalrow = usersservice.userList().size();//get total row
		
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}
		totalpage = (totalrow + pagerow - 1) / pagerow;

		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			currpages = totalpage;
		}
		Integer candp = (currpages - 1) * pagerow;
		parameters.put("pandc", pagerow);
		parameters.put("candp", candp);
		List<Users> ulist = usersservice.queryUserslimits(parameters);
		if (unickname!=null) {
			ulist=usersservice.queryUserslimits(unickname);
			model.addAttribute("sta",1);
			
		}
		if (ulist.size()==0) {
			model.addAttribute("stas",1);
		}else{
			model.addAttribute("stas",0);
		}
		model.addAttribute("ulist", ulist);
		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpage", currpages);
		model.addAttribute("totalpage", totalpage);

		return "WEB-INF/view/bk_userslist";
	}

	// new user registering, start
	@RequestMapping("insert")
	public String insert(Users users, Model model,
			@RequestParam(value = "unickname", required = false) String unickname,
			@RequestParam(value = "upassword", required = false) String upassword,
			@RequestParam(value = "uphonenumber", required = false) String uphonenumber) {

		users.setUnickname(unickname);
		users.setUpassword(upassword);
		users.setUphonenumber(uphonenumber);
		// add data into database
		usersservice.insert(users);//users.getUid()
		Certification cer = new Certification();
		cer.setCserial(users.getUid()+"");
		cer.setCbalance("0");
		cer.setCusername("1");
		cer.setCrealname("1");
		cer.setCfreeze("0");
		cer.setCdue("0");
		cer.setCpaid("0");
		cer.setCtotalmoney("0");
		Certificat.insert(cer);
		model.addAttribute("unickname", unickname);
		// Return to the successful registration interface
		return "register1";
	}
	// new user registering, end

	// login, start
	@RequestMapping("login")
	public String login(Model model, HttpSession session,
			@RequestParam(value = "unickname", required = false) String unickname,
			@RequestParam(value = "upassword", required = false) String upassword) {

		String status;
		// Judge according to whether the account query is null
		System.out.println(unickname + "-----------------" + upassword);
		Users user = usersservice.byNameFindUsers(unickname, upassword);

		if (user == null) {
			// login error
			status = "incorrect username or passowrd";
			model.addAttribute("status", status);
			return "login";
		} else {
			// login successful

			model.addAttribute("users", user);
			// Save login information to session
			session.setAttribute("globaluser", user);
			return "index";
		}
	}
	// login, end

	// log out, start
	@RequestMapping("exit")
	public String exit(HttpSession session) {

		Enumeration em = session.getAttributeNames();
		while (em.hasMoreElements()) {
			session.removeAttribute(em.nextElement().toString());
		}
		return "index";
	}

	// log out, end
	@RequestMapping("findByName")
	@ResponseBody
	public int findByName(@RequestParam(value = "unickname", required = false) String unickname){
		System.out.println("unickname"+unickname);
		List<Users> ulist = usersservice.queryUserslimits(unickname);
		if(ulist.size()>0){
			return 2;
		}else{
			return 1;
		}
	}

}
