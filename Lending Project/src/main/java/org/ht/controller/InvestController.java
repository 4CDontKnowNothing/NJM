package org.ht.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.ht.pojo.Biao;
import org.ht.pojo.Details;
import org.ht.pojo.InvestInfo;
import org.ht.pojo.Product;
import org.ht.pojo.Trade;
import org.ht.pojo.Users;
import org.ht.service.BiaoService;
import org.ht.service.CertificationService;
import org.ht.service.DetailsService;
import org.ht.service.InvestService;
import org.ht.service.ProductService;
import org.ht.service.TradeService;
import org.ht.util.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/invest")
public class InvestController {
	@Resource
	private InvestService investS;
	@Resource
	// private BorrowmoneyService service;
	private ProductService proS;

	@Resource
	private DetailsService detS;

	@Resource
	private BiaoService biaoS;

	@Resource
	private CertificationService cs;
	
	@Resource
	private TradeService tradeS;
	
	HttpSession hs = null;
	ServletContext application = null;
	
	@RequestMapping("investSel")
	public String investSel(HttpServletRequest req, Model model, String item,
			String param, String currpage) {// Query the list of bids @RequestParam(value =
											// "currpage", required = false)
		// Borrowmoney borrowmoney=new Borrowmoney();
		// PageInfo<Borrowmoney> page = service.findList(borrowmoney, 1, 1);
		// List<Borrowmoney> list = page.getList();
		// System.out.println(">>>>>>>>>>>>>>>>>>>>total bids"+list.size()+list.toString());
		// model.addAttribute("list", list);
//		String lastUrl = req.getHeader("Referer");//Get page's url jumps to
//		System.out.println("URL of last page"+lastUrl);
//		String nowUrl = "http://localhost:8080/p2p/invest/investSel.do";
//		boolean bl = false;
//		if(lastUrl != null){
//			bl = lastUrl.matches(nowUrl+"[\\s\\S]*");//The regular expression matches any string after the nowUrl character
//		}
//		
//		System.out.println("maching results： "+bl);
//		if(!bl){//!lastUrl.equals(nowUrl)
//			/////////////////this code can clear all the sessions
//			  Enumeration em = req.getSession().getAttributeNames();
//			  while(em.hasMoreElements()){
//				req.getSession().removeAttribute(em.nextElement().toString());
//			  }
//			  //////////////
//		}
	
		int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row

		int outcount = 0;// count on data no more than one page
		int count = 0;//

		if (item != null && !item.equals("")) {//!lastUrl.equals(nowUrl)  && bl
			Map<String, Object> map = new HashMap<String, Object>();
			if (hs == null) {
				hs = req.getSession();
			}
			if (item.equals("itemtype")) {// item type
				if (param.equals("-1")) {// no limit 
					if (hs.getAttribute("biaoId") != null) {
						System.out.println("successful access");
						hs.removeAttribute("biaoId");
					}
				} else{
					hs.setAttribute("biaoId", param);
				}
			}
			if (item.equals("rate")) {// interest rate
				if (param.equals("-1")) {// no limit
					if (hs.getAttribute("pincome") != null) {
						hs.setAttribute("startR", "-1");
						hs.removeAttribute("pincome");
					}
				}
				if (param.equals("1")) {// below 12%
					hs.setAttribute("startR", "0");
					hs.setAttribute("endR", "12");
					hs.setAttribute("pincome", "");
				}
				if (param.equals("2")) {// 12%-14%
					hs.setAttribute("startR", "12");
					hs.setAttribute("endR", "14");
					hs.setAttribute("pincome", "");
				}
				if (param.equals("3")) {// 14%-16%
					hs.setAttribute("startR", "14");
					hs.setAttribute("endR", "16");
					hs.setAttribute("pincome", "");
				}
				if (param.equals("4")) {// upper 16% 
					hs.setAttribute("startR", "16");
					hs.setAttribute("pincome", "");
					hs.setAttribute("endR", "");
				}
			}
			if (item.equals("timelimit")) {// time limit, by default 30days/month
				if (param.equals("-1")) {// no limit
					if (hs.getAttribute("pcount") != null) {
						hs.setAttribute("startT", "-1");
						hs.removeAttribute("pcount");
					}
				}
				if (param.equals("1")) {// below 1 month
					hs.setAttribute("startT", "0");
					hs.setAttribute("endT", "30");
					hs.setAttribute("pcount", "");
				}
				if (param.equals("2")) {// 1-3 months
					hs.setAttribute("startT", "30");
					hs.setAttribute("endT", "90");
					hs.setAttribute("pcount", "");
				}
				if (param.equals("3")) {// 3-6 months
					hs.setAttribute("startT", "90");
					hs.setAttribute("endT", "180");
					hs.setAttribute("pcount", "");
				}
				if (param.equals("4")) {// 6-12 months
					hs.setAttribute("startT", "180");
					hs.setAttribute("endT", "360");
					hs.setAttribute("pcount", "");
				}
				if (param.equals("5")) {// 12 months or more
					hs.setAttribute("startT", "360");
					hs.setAttribute("endT", "");
					hs.setAttribute("pcount", "");
				}
			}
			if (item.equals("repayway")) {// repay way
				if (param.equals("-1")) {// no limit
					hs.setAttribute("pway", "");
				}
				if (param.equals("1")) {
					hs.setAttribute("pway", "Repayment of principal and interest at maturity");
				}
				if (param.equals("2")) {
					hs.setAttribute("pway", "Monthly interest and principal due");
				}
				if (param.equals("3")) {
					hs.setAttribute("pway", "average capital plus interest");
				}

			}
			// +hs.getAttribute("endR")==null"": +hs.getAttribute("endT")
			System.out.println("sessionBidPrimaryKey " + hs.getAttribute("biaoId") + ""
					+ "StartInterestRateOfSession  " + hs.getAttribute("startR")
					+ "StartTimeOfSession" + hs.getAttribute("startT")
					+ "RepayMethodOfSession" + hs.getAttribute("way"));
			System.out.println("PrimaryKeyOfMap " + map.get("biaoId") + "startInterestRate "
					+ map.get("startR") + "Term starting point " + map.get("startT")
					+ "repay method " + map.get("way"));
			if (hs != null) {
				map.put("pincome", hs.getAttribute("pincome"));
				map.put("pcount", hs.getAttribute("pcount"));

				map.put("biaoId", hs.getAttribute("biaoId"));
				map.put("startR", hs.getAttribute("startR"));
				map.put("endR", hs.getAttribute("endR"));
				map.put("startT", hs.getAttribute("startT"));
				map.put("endT", hs.getAttribute("endT"));
				map.put("pway", hs.getAttribute("pway"));
			}
			System.out.println("Primary key of map " + map.get("biaoId") + "Interest rate start point "
					+ map.get("startR") + "term start point " + map.get("startT")
					+ "repay method " + map.get("pway"));

			List<Product> page = proS.selList(map);

			totalrow = page.size();// get total row
			if (currpage != null && !"".equals(currpage)) {
				currpages = Integer.parseInt(currpage);
			}
			// totalpage = (totalrow + pagerow - 1) / pagerow;

			outcount = totalrow % pagerow;
			count = totalrow / pagerow;

			totalpage = count;

			if (outcount > 0) {
				totalpage = count + 1;
			}

			if (currpages < 1) {
				currpages = 1;
			}
			if (currpages > totalpage) {
				currpages = totalpage;
			}

			if (currpages == 0) {
				currpages = 1;
			}

			Integer candp = (currpages - 1) * pagerow;
			map.put("startPage", candp);
			map.put("pageSize", 5);

			List<Product> pages = proS.selList(map);
			model.addAttribute("totalrow", totalrow);
			model.addAttribute("currpages", currpages);
			model.addAttribute("totalpage", totalpage);
			model.addAttribute("list", pages);

		} else {

			Product pro = new Product();
			@SuppressWarnings("unchecked")
			List<Product> page = proS.findList(BeanUtils.toMap(pro));

			totalrow = page.size();// get total row
			if (currpage != null && !"".equals(currpage)) {
				currpages = Integer.parseInt(currpage);
			}
			// totalpage = (totalrow + pagerow - 1) / pagerow;

			outcount = totalrow % pagerow;
			count = totalrow / pagerow;

			totalpage = count;

			if (outcount > 0) {
				totalpage = count + 1;
			}

			if (currpages < 1) {
				currpages = 1;
			}
			if (currpages > totalpage) {
				currpages = totalpage;
			}

			if (currpages == 0) {
				currpages = 1;
			}

			Integer candp = (currpages - 1) * pagerow;
			pro.setStartPage(candp);
			pro.setPageSize(5);

			@SuppressWarnings("unchecked")
			List<Product> list = proS.findList(BeanUtils.toMap(pro));
			model.addAttribute("totalrow", totalrow);
			model.addAttribute("currpages", currpages);
			model.addAttribute("totalpage", totalpage);
			model.addAttribute("list", list);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		List<Biao> biao = biaoS.findList(map);
		model.addAttribute("biao", biao);

		return "list";
	}

	@RequestMapping("recommendShow")
	public String recommendShow(HttpServletRequest req,Model model) {// Recommendations appear on the home page
		Map<String, Object> parameters = new HashMap<String, Object>();// query condition
		 
		// parameters.put("pandc", 2);
		// parameters.put("candp", 1);
		// parameters.put("bstate", "bidding");
		// parameters.put("brecommend", "recommended");
		
		 if(application == null){
				List<Product> proList = new ArrayList<Product>();

				List<Biao> list = biaoS.findList(parameters);
				if (list != null && list.size() > 0) {
					parameters.put("pageSize", 2);
					parameters.put("startPage", 0);
					for (int i = 0; i < list.size(); i++) {
						Biao biao = list.get(i);
						parameters.put("biaoId", biao.getId());
						List<Product> tlist = proS.selList(parameters);// two data
						for (int j = 0; j < tlist.size(); j++) {
							proList.add(tlist.get(j));// Save two pieces of data for each type into a list
						}
					}
				}
					
				parameters.remove("biaoId");
				parameters.put("pcount", "");//The recommended project duration is less than one month
				parameters.put("startT", "0");
				parameters.put("endT", "30");
				List<Product> tjl = proS.selList(parameters);
				
//				model.addAttribute("proList", proList);
//				model.addAttribute("biaoList", list);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("rowName", "inmoney");// Find out the total amount of investment
				map.put("tableName", "investinfo");
				
				Double tm = investS.sumMoney(map);
				
				application = req.getSession().getServletContext();
				application.setAttribute("proList", proList);
				application.setAttribute("biaoList", list);
				application.setAttribute("tjlist", tjl);
				application.setAttribute("ztz", tm);
		 }
		
		// List<Borrowmoney> list = service.pagingSel(parameters);
		// model.addAttribute("list", list);

		return "index";
	}

	@RequestMapping("investInfo")
	public String investInfo(
			String bmid,String currpage,
			Model model, HttpServletRequest req) {// Borrowmoney bm
		System.out.println(bmid.toString());
		// Borrowmoney bm = service.get(Integer.parseInt(bmid));
		// System.out.println(bm.toString());
		// model.addAttribute("Borrowmoney", bm);
		// HttpSession bms = req.getSession();
		// bms.setAttribute("Borrowmoney", bm);
////////////////////////////////////
		int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row

		int outcount = 0;// The number of data less than one page
		int count = 0;//

		Map<String, Object> parameters = new HashMap<String, Object>();// query condition 
		parameters.put("bid", bmid);
		List<InvestInfo> page = investS.investS(parameters);// get data number
		totalrow = page.size();// get total row
		System.out.println("The number of records of the underlying investment information"+totalrow);
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}

		outcount = totalrow % pagerow;
		count = totalrow / pagerow;

		totalpage = count;

		if (outcount > 0) {
			totalpage = count + 1;
		}

		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			currpages = totalpage;
		}

		Integer candp = (currpages - 1) * pagerow;
		if(candp < 0){
			candp = 0;
		}
		parameters.put("pandc", 5);
		parameters.put("candp", candp);
		
		List<InvestInfo> lists = investS.investS(parameters);

		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpages", currpages);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("bmid", bmid);
		model.addAttribute("record", lists);

		// find total number
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowName", "inmoney");// Find out the total amount of investment
		map.put("tableName", "investinfo");
		map.put("bid", bmid);//Obtain records of all investments in the subject matter
		
		Double tm = investS.sumMoney(map);
		model.addAttribute("tm", tm);
		System.out.println("tm" + tm);
		map.put("rowName", "profitmoney");// Check total income

		Double gm = investS.sumMoney(map);
		model.addAttribute("gm", gm);
		System.out.println("gm" + gm);

		Map<String, Object> bmap = new HashMap<String, Object>();
		List<Biao> biao = biaoS.findList(bmap);
		model.addAttribute("biao", biao);
//////////////////////////////////////////
		
		
		Product pro = proS.get(Integer.parseInt(bmid));
		HttpSession bms = req.getSession();
		bms.setAttribute("Borrowmoney", pro);

		List<Details> list = detS.detailslist(pro.getId());
		System.out.println("Size of the detail list" + list.size());
		bms.setAttribute("Product", pro);
		bms.setAttribute("Details", list);

		System.out.println("pro.getPstate()the value get   " + pro.getPstate());
		long days = (pro.getPcount().getTime() - pro.getPtime().getTime())
				/ (24 * 60 * 60 * 1000);
		bms.setAttribute("days", days);
		if (pro.getPstate().equals("1")) {
			Users us = (Users) req.getSession().getAttribute("globaluser");
			if(us != null){
				String kymoney = cs.selectM(us.getUid());
				System.out.println("Enter the user balance on the input amount page   user balance"+kymoney);
				bms.setAttribute("kymoney", kymoney);
			}
			return "inforadd";
		} else {
			System.out.println("Go to the display page");
			return "infor";
		}
	}

	@RequestMapping("investAdd")
	public String investAdd(
			@RequestParam(value = "money", required = false) String money,
			HttpServletRequest req,Model model) {// bidding
										// @RequestParam(value="",requested=false)
										// InvestInfo ii
		HttpSession hs = req.getSession();
		// Borrowmoney bm = (Borrowmoney) hs.getAttribute("Borrowmoney");
		// System.out.println(bm.getBlimit());

		Product pro = (Product) hs.getAttribute("Product");

		InvestInfo ii = new InvestInfo();
		Users user = (Users) hs.getAttribute("globaluser");
		// inid; //'Investment information table primary key',
		// ii.setInid(2);
		if(user != null){
			ii.setUserid(user.getUid()); // 'primary key for investment',
			// ii.setBrrowid(bm.getId()); //'primary key of bidding',
			ii.setBrrowid(pro.getId());//
			ii.setInmoney(new BigDecimal(money)); // 'investment amount',
			ii.setInstatus("no"); // 'Investment status 0 investment in income 1 completed investment',
			ii.setInstyle("no"); // 'invest ment type',
			// brrowstatus;// 'Whether the loan status is completed',
			ii.setBrrowstatus("no");
			ii.setInterest(pro.getPincome()); // 'invest rate',
			ii.setProfitmodel(pro.getPway()); // 'Profit method such as equal principal',
			ii.setProfitmoney(new BigDecimal("0.00")); // 'Profit amount',
			Date date = new Date();
			// @SuppressWarnings("deprecation")
			// String d = date.toLocaleString();
			@SuppressWarnings("deprecation")
			Timestamp ts = new Timestamp(date.getYear(), date.getMonth(),
					date.getDay(), date.getHours(), date.getMinutes(),
					date.getSeconds(), 0);
			ii.setIndate(ts); // 'Investment time can be empty'

			// ii.setReplaydate(Integer.parseInt(bm.getBlimit())); //
			long days = (pro.getPcount().getTime() - pro.getPtime().getTime())
					/ (24 * 60 * 60 * 1000);// time difference
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String ds = sdf.format(pro.getPcount());

			ii.setReplaydate(ds + "(total" + days + "days)");
			ii.setMarkstatus(0); // 'Bid status 0 The default bid is in progress 1 The bid passes (winning) 2 The bid fails (losing the bid)';

			System.out.println(ii.toString());
			hs.removeAttribute("Product");
			hs.removeAttribute("Details");
			investS.investA(ii);//Add investment record

			// Deduct the amount from the user's available balance
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("uid", user.getUid());
			String kym = (String) req.getSession().getAttribute("kymoney");//total usable balance
			String nkym = (Double.parseDouble(kym) - Double.parseDouble(money))+"";//Available amount remaining after deducting investment
			map.put("money",nkym);
			cs.updateM(map);
			hs.removeAttribute("kymoney");
			//Write to the user account amount record table
			Trade td = new Trade();
			td.setuID(user.getUid());
			td.setUname(user.getUnickname());
			td.setZname(user.getUname());
			td.setJymoney(money);
			td.setOther("\r\n" + 
					"If you want to invest, you have to be willing to spend money");
			tradeS.addDate(td);
			//Modify the project and gather funds
//			Product product = proS.get(pro.getId());
			Double updMoney = Double.parseDouble(pro.getPmoney()+"") + Double.parseDouble(money);
			System.out.println("modified amount"+updMoney);
			pro.setPmoney(updMoney.intValue());
			proS.update(pro);
			
			//Determine whether the project is full
			DecimalFormat df = new DecimalFormat( "0.00");
			String udm = df.format(updMoney).toString();
			String odm = df.format(pro.getPtotalmoney()).toString();
			if(udm.equals(odm)){//just gathered 
				pro.setPstate("2");//modified to fund up
				proS.update(pro);
			}
			hs.setAttribute("end", "end");
		}
		
		return "redirect:investInfo.do?bmid="+pro.getId();
	}

	@RequestMapping("investRecord")
	public String investRecord(Model model,
			@RequestParam(value = "currpage", required = false) String currpage,HttpServletRequest req) {// check invest record
		Users u = (Users) req.getSession().getAttribute("globaluser");
		
		int pagerow = 5;// 5 row per page
		int currpages = 1;// current page
		int totalpage = 0;// total page
		int totalrow = 0;// total row

		int outcount = 0;// data amount out count
		int count = 0;//

		Map<String, Object> parameters = new HashMap<String, Object>();// query condition
		if(u != null){//The user has logged in to find out the user's data otherwise all data
			parameters.put("uid", u.getUid());
		}
		List<InvestInfo> page = investS.investS(parameters);// check data amout
		totalrow = page.size();// get total row
		System.out.println("\r\n" + 
				"Number of investment information records for this subject"+totalrow);
		if (currpage != null && !"".equals(currpage)) {
			currpages = Integer.parseInt(currpage);
		}
		// totalpage = (totalrow + pagerow - 1) / pagerow;

		outcount = totalrow % pagerow;
		count = totalrow / pagerow;

		totalpage = count;

		if (outcount > 0) {
			totalpage = count + 1;
		}

		if (currpages < 1) {
			currpages = 1;
		}
		if (currpages > totalpage) {
			currpages = totalpage;
		}
		// Integer pandc = pagerow * currpages;
		Integer candp = (currpages - 1) * pagerow;
		if(candp < 0){
			candp = 0;
		}
		parameters.put("pandc", 5);
		parameters.put("candp", candp);
		List<InvestInfo> list = investS.investS(parameters);

		model.addAttribute("totalrow", totalrow);
		model.addAttribute("currpages", currpages);
		model.addAttribute("totalpage", totalpage);
		model.addAttribute("record", list);

		// check total money
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rowName", "inmoney");// Find the total investment
		map.put("tableName", "investinfo");
		if(u != null){//The user has logged in to find out the user's data otherwise all data
			map.put("uid", u.getUid());
		}
		
		Double tm = investS.sumMoney(map);//check total invest money
		model.addAttribute("tm", tm);
		System.out.println("tm" + tm);
		map.put("rowName", "profitmoney");

		Double gm = investS.sumMoney(map);// check total profit
		model.addAttribute("gm", gm);
		System.out.println("gm" + gm);

		//Detect the refunded principal
		List<Trade> tmonery = tradeS.selectMoney(u.getUid());
		Integer allM = 0;
		for(Trade tr : tmonery){
			String money = tr.getJymoney().replace("+", "");
			allM += Integer.parseInt(money);
		}
		System.out.println("Total refunded principal"+allM);
		
		//Identify total revenue
		Integer gtm = investS.getMoney(u.getUid());
		model.addAttribute("gtm", gtm);
		
		Map<String, Object> bmap = new HashMap<String, Object>();
		List<Biao> biao = biaoS.findList(bmap);
		model.addAttribute("biao", biao);
		model.addAttribute("thm", allM);
		return "investrecord";
	}

	public static void main(String s[]) {
		Date date = new Date();
		long dl = date.getTime();// Convert date to milliseconds
		System.out.println(dl + "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		try {
			d = sdf.parse("2017-03-05 20:27:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long dt = d.getTime();
		long day = (dt - dl) / (24 * 60 * 60 * 1000);
		System.out.println(day + "days");
	}
}
