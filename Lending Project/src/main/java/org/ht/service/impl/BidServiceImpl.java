package org.ht.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ht.dao.BidDao;
import org.ht.pojo.Borrowmoney;
import org.ht.pojo.Certification;
import org.ht.pojo.InvestInfo;
import org.ht.pojo.Product;
import org.ht.pojo.Trade;
import org.ht.pojo.Users;
import org.ht.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BidServiceImpl implements BidService {

	@Autowired
	private BidDao bidDao;
	
	
	@Override
	public Integer tosize() {
		return bidDao.tosize().size();
	}
	@Override
	public Integer tosizew() {
		return bidDao.tosizew().size();
	}
	@Override
	public Integer tosizeb() {
		return bidDao.tosizeb().size();
	}

	@Override
	/** Inquiry of the target that expires on the day */
	public List<Product> todaoqi() {
		return bidDao.todaoqi();
	}

	@Override
	/** Change the status of the mark to complete */
	public void upzt(Integer id) {
		bidDao.upzt(id);
	}

	@Override
	/** Check investment records */
	public List<InvestInfo> totouzilist(Integer id) {
		return bidDao.totouzilist(id);
	}

	@Override
	/**
	 * Target expires
	 * 
	 * Total processing method 1. Add balance 2. Add transaction table 3. Change status
	 */
	public void chuli() {
		List<Product> pList = bidDao.todaoqi();
		if (pList.size()!=0&&pList!=null) {
			for (Product product : pList) {
				List<InvestInfo> list = bidDao.totouzilist(product.getId());
				for (InvestInfo investInfo : list) {
					// Amount
					Certification certification = bidDao.togetyue(investInfo.getBrrowid());
					Map<String, String> map = new HashMap<String, String>(2);
					Integer ye = Integer.parseInt(certification.getCtotalmoney());// 余额

					System.out.println(">>>>>>>>>>>before calculation:" + ye);
					// Balance calculation __________ (add date and time)
					int b = ((investInfo.getInmoney()).intValue() * (Integer.parseInt(product.getPincome())));// invest amount
//					int b = ((investInfo.getInmoney()).intValue() * (Integer.parseInt(product.getPincome())))*(/12);// invest amount
					ye = ye + b;
					System.out.println(">>>>>>>>>>>after calculation:" + ye);
					map.put("ctotalmoney", ye + "");
					map.put("cserial", investInfo.getBrrowid() + "");
					bidDao.toupyue(map);

					// *****Add to transaction log*******//
					Users us = bidDao.seluesr(investInfo.getBrrowid());

					Trade trade = new Trade(investInfo.getBrrowid(), us.getUnickname(), us.getUname(), "+" + b,
							product.getPname() + "When the bid expires, return the principal and income.", new Date(), " ");
					bidDao.toaddtrade(trade);

				}
				// 3. Modify the status of the target: 4 complete
				bidDao.upzt(product.getId());
			}
		}
	}

	/**Loan due*/
	public void chuli2() {
		//Query expired bid
		List<Product> liProducts = bidDao.togetck();
		if (liProducts.size()!=0&&liProducts!=null) {
			for (Product product : liProducts) {
				//Query investment record form
				List<InvestInfo> list = bidDao.totouzilist(product.getId());
				for (InvestInfo investInfo : list) {
					//According to user query account balance
					Certification certification = bidDao.togetyue(investInfo.getBrrowid());
					// Balance calculation
					Integer ye = Integer.parseInt(certification.getCtotalmoney());// Balance
					System.out.println(">>>>>>>>>>>before calculation:" + ye);
					int b = investInfo.getInmoney().intValue();// investment amount
					ye = ye + b;
					System.out.println(">>>>>>>>>>>after calculation:" + ye);
					//Balance modification
					Map<String, String> map = new HashMap<String, String>(2);
					map.put("ctotalmoney", ye + "");
					map.put("cserial", investInfo.getBrrowid() + "");
					bidDao.toupyue(map);
	
					// *****Add to transaction log*******//
					Users us = bidDao.seluesr(investInfo.getBrrowid());
					Trade trade = new Trade(investInfo.getUserid(), us.getUnickname(), us.getUname(), "+" + b,
							product.getPname() + "The fundraising is not completed, the principal will be returned.", new Date(), " ");
					bidDao.toaddtrade(trade);
				}
				// The status of the modification target is: 3 invalid
				bidDao.upzts(product.getId());
			}
		}
	}

}
