package org.ht.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.ht.pojo.Biao;
import org.ht.pojo.Borrowcord;
import org.ht.pojo.Borrowmoney;
import org.ht.service.BiaoService;
import org.ht.service.BorrowcordService;
import org.ht.service.BorrowmoneyService;
import org.ht.util.BeanUtils;
import org.ht.util.ZqUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("brower")
public class BorrowmoneyController {
	@Autowired
	public BiaoService biaoService;
	@Autowired
	private BorrowmoneyService service;
	@Autowired
	private BorrowcordService bService;
	static final String str = "WEB-INF/view/";

	@RequestMapping("qurey")
	public String allMoney(HttpServletRequest request, Borrowmoney borrowmoney, Model model,
			@RequestParam(value = "currpage", required = false) String currpage) {
		final int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row
		Borrowmoney borrowmoney1 = new Borrowmoney();
		borrowmoney1.setBtype(borrowmoney.getBtype());
		borrowmoney1.setBstate(borrowmoney.getBstate());
		borrowmoney1.setBusername(borrowmoney.getBusername());

		if (borrowmoney.getBtype() == null || borrowmoney.getBtype().equals("")
				|| borrowmoney.getBtype().equals("Please make a choice")) {

			borrowmoney1.setBtype(null);
		}
		if (borrowmoney.getBstate() == null || borrowmoney.equals("") || borrowmoney.getBstate().equals("Please make a choice")) {

			borrowmoney1.setBstate(null);
		}
		List<Borrowmoney> list = service.findList(BeanUtils.toMap(borrowmoney1));
		totalrow = list.size();// get total row
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}
		totalpage = (totalrow + pagerow - 1) / pagerow;
		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			if (totalpage < 1) {
				totalpage = 1;
			}
			currpages = totalpage;
		}
		Integer startPage = (currpages - 1) * pagerow;
		borrowmoney1.setStartPage(startPage);
		borrowmoney1.setPageSize(pagerow);
		List<Borrowmoney> list2 = service.findList(BeanUtils.toMap(borrowmoney1));
		model.addAttribute("list", list2);
		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpages", currpages);
		model.addAttribute("totalpage", totalpage);
		List<Biao> bList = biaoService.findList(BeanUtils.toMap(new Biao()));

		model.addAttribute("page", list2);
		model.addAttribute("bList", bList);

		return str + "bk_moneylist";
	}

	@RequestMapping("audit")
	public String audit(Model model, @RequestParam(value = "currpage", required = false) String currpage,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "status", required = true) String status) {
		Borrowmoney borrowmoney1 = new Borrowmoney();
		// approval
		if (status.equals("1")) {
			borrowmoney1.setBstate("1");
		}
		// not approval
		if (status.equals("0")) {
			borrowmoney1.setBstate("2");
		}
		borrowmoney1.setId(Integer.parseInt(id));
		service.update(borrowmoney1);

		final int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row
		borrowmoney1.setBstate("0");
		List<Borrowmoney> list = service.findList(BeanUtils.toMap(borrowmoney1));
		totalrow = list.size();// get total row
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}
		totalpage = (totalrow + pagerow - 1) / pagerow;
		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			if (totalpage < 1) {
				totalpage = 1;
			}
			currpages = totalpage;
		}
		Integer startPage = (currpages - 1) * pagerow;
		borrowmoney1.setStartPage(startPage);
		borrowmoney1.setPageSize(pagerow);
		List<Borrowmoney> list2 = service.findList(BeanUtils.toMap(borrowmoney1));
		model.addAttribute("page", list2);
		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpages", currpages);
		model.addAttribute("totalpage", totalpage);

		return str + "bk_money_check";
	}

	@RequestMapping(value = "check")
	public String check(Borrowmoney borrowmoney, Model model,
			@RequestParam(value = "currpage", required = false) String currpage) {
		final int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row
		Borrowmoney borrowmoney1 = new Borrowmoney();
		borrowmoney1.setBstate("0");
		List<Borrowmoney> list = service.findList(BeanUtils.toMap(borrowmoney1));
		totalrow = list.size();// get total row
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}
		totalpage = (totalrow + pagerow - 1) / pagerow;
		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			if (totalpage < 1) {
				totalpage = 1;
			}
			currpages = totalpage;
		}
		Integer startPage = (currpages - 1) * pagerow;
		borrowmoney1.setStartPage(startPage);
		borrowmoney1.setPageSize(pagerow);
		List<Borrowmoney> list2 = service.findList(BeanUtils.toMap(borrowmoney1));

		model.addAttribute("page", list2);
		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpages", currpages);
		model.addAttribute("totalpage", totalpage);

		return str + "bk_money_check";

	}

	@RequestMapping("find")
	public String find(Model model, @Param(value = "id") String id) {
		if (id == null || id.equals("")) {
			id = 1 + "";
		}
		Integer ia = Integer.parseInt(id);
		Borrowmoney mBorrowmoney = service.get(ia);
		model.addAttribute("domain", mBorrowmoney);
		return str + "bk_money_detail";
	}

	// json Add repayment (front desk)
	@RequestMapping("toaddborr")
	@ResponseBody
	public String toadd(Borrowmoney borrowmoney) {
		service.toaddborr(borrowmoney);
		return "";
	}

	// repayment(Check all repayments required)
	@RequestMapping("tohk")
	public String updhuankuan(Model model) {
		System.out.println(service.updhuankuan().size() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		model.addAttribute("list", service.updhuankuan());

		return str + "bk_huankuanlist";
	}

	// See the payment details page
	@RequestMapping("tohuankuanupd")
	public String tohuankuan(Model model, @RequestParam(value = "id") Integer ids) {
		Borrowmoney borr = service.get(ids);
		model.addAttribute("borr", borr);
		model.addAttribute("list", bService.selborr(ids));

		return str + "bk_huankuanupdeta";
	}

	// Modify repayment status
	@RequestMapping("tohuankuanupds")
	public String tohuankuanupd(Model model, @RequestParam(value = "ids") Integer ids,
			@RequestParam(value = "id") Integer id) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>修改还款状态");
		bService.updborr(ids);
		return "redirect:tohuankuanupd.do?id=" + id;
	}

	// json
	@RequestMapping("tohuankuanupdison")
	@ResponseBody
	public List<Borrowcord> tohuankuanjson(@RequestParam(value = "id") Integer ids) {
		List<Borrowcord> list = bService.selborr(ids);
		return list;
	}

	// Click agree to enter the loan information to confirm the meeting
	@RequestMapping("borqr")
	public String borqr(Model model, @RequestParam(value = "ids") Integer ids) {
		Borrowmoney borro = service.borrowget(ids);
		model.addAttribute("borr", borro);
		return str + "bk_huankuanget";
	}

	// Click "agree" to enter the loan information to confirm meeting (to modify)
	@RequestMapping("borxg")
	public String borxg(Model model, Borrowmoney borrowmoney) {
		//change status
		borrowmoney.setBstate("1");
		service.update(borrowmoney);
		//Handle repayment records
		bService.borradd(borrowmoney.getBtimelimit(), borrowmoney.getId(), borrowmoney.getBserial());
		
		return "redirect:check.do";
	}

	// hjy
	@RequestMapping("hjyList")
	public String hjyList(Model m, @RequestParam(value = "currpage", required = false) String currpage) {
		Map<String, Object> wmap = service.selecthjy(currpage);
		List<Borrowmoney> llist = (List<Borrowmoney>) wmap.get("llist");
		m.addAttribute("pagerow", wmap.get("pagerow"));
		m.addAttribute("currpages", wmap.get("currpages"));
		m.addAttribute("wdlist", llist);
		m.addAttribute("totalpage", wmap.get("totalpage"));
		m.addAttribute("totalrow", wmap.get("totalrow"));
		return str + "Borrowmoneylist";
	}

	// hjy
	@RequestMapping("bajax")
	@ResponseBody
	public Borrowmoney ajax(@RequestParam(value = "id", required = false) int id) {
		System.out.println(id);
		System.out.println(service.get(id).getBrelname());
		return service.get(id);
	}

}
