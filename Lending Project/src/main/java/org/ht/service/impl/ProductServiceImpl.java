package org.ht.service.impl;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ht.dao.DetailsDao;
import org.ht.dao.ProductDao;
import org.ht.pojo.Details;
import org.ht.pojo.Product;
import org.ht.service.ProductService;
import org.ht.util.TimeCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao dao;

	@Resource
	DetailsDao detailsDao;

	@Override
	public Product get(Integer id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		detailsDao.deleteByPid(id);
		dao.delete(id);
	}

	@Override
	public void create(Product product) {
		// Here, the primary key ID of the primary table is returned when the primary table is added. 
		//In order to add the primary key ID to the primary key of the secondary table 
		//(the ID of the primary table is already configured in XML)

		dao.create(product);
		// When adding a target, you need to cascade the new details table
		Details details = product.getDetails();
		// Give the ID of the main table to the ID of the attached table so that they have the same value
		if (details != null) {
			details.setPid(product.getId());
			detailsDao.create(details);
		}
	}

	@Override
	public int update(Product product) {
		// TODO Auto-generated method stub

		return dao.update(product);
	}

	@Override
	public List<Product> findList(Map<String, Object> map) {
		List<Product> borrowmoneys = dao.findList(map);
		return borrowmoneys;
	}

	@Override
	public void updateProgres(List<Product> product) {

		for (Product product2 : product) {
			if (product2.getPtotalmoney() == null || product2.getPtotalmoney().equals("")
					|| product2.getPmoney() == 0) {
				product2.setPtotalmoney(0);
				product2.setPmoney(1);
			}
			double money = (product2.getPmoney());// Total amount raised
			double count = product2.getPtotalmoney(); // Total bids
			if (money >= count) {
				product2.setProgress(100 + "");
			} else {
				double sum = (money / count) * 100;
				DecimalFormat df = new DecimalFormat("#.00");
				String result = df.format(sum);
				if (result.length() < 4) {
					result = "0" + result;
				}
				product2.setProgress(result + "");
			}

			dao.updateProgres(product2);
		}
	}

	@Override
	public void updateStatus(List<Product> product) {

		for (Product product2 : product) {
			double pragess = Double.parseDouble(product2.getProgress());
			Date ptime = product2.getPtime();// Project duration
			TimeCompare time = new TimeCompare();
			int flag = time.Compare(new Date().toLocaleString(), ptime.toLocaleString());
			if (pragess < 100 && flag == 1) {
				product2.setPstate("1");// Raise
			} else if (pragess >= 100 && flag == -1) {
				product2.setPstate("2");// Raised
			} else if (pragess < 100 && flag == 0) {
				product2.setPstate("3");// Failure
			}
			dao.updateStatus(product2);
		}
	}

	/**
	 * @param product
	 * @explain Set and modify the annual interest rate, 
	 * 				fund raising end time, repayment time
	 */

	@Override
	public int setRateAndDeadline(Product product) {
		// TODO Auto-generated method stub
		return dao.setRateAndDeadline(product);
	}

	@Override
	public List<Product> selList(Map<String, Object> map) {
		return dao.myList(map);
	}

	@Override
	public int selIsExitsPrimaryKey() {
		// TODO Auto-generated method stub
		return dao.selIsExitsPrimaryKey();
	}

}
