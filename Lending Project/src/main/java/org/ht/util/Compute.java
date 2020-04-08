package org.ht.util;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.ht.pojo.Product;
import org.ht.service.ProductService;

/**
 *   calculation class
 * 
 * @author Administrator
 *
 */
public class Compute {
		
	@Resource
	ProductService service;
	
	//Calculate investment progress
	public  double  CountTwo(int a,int b ){
		
		return (double)a/b;
	}
	
	
	//update investment progress
	
	public void  updProgres(List<Product> product){
		
		
		for (Product product2 : product) {
			double money=(product2.getPmoney());//Total amount raised
			double count=product2.getPtotalmoney(); //Total bids
				if (money>=count) {
					product2.setProgress(100+"");
				}else {
					double sum=(money/count)*100;
					DecimalFormat df = new DecimalFormat("#.00");
					String result=  df.format(sum);
					System.out.println("money    "+money);
					System.out.println("count   "+count);
					System.out.println("sum   "+sum);
					product2.setProgress(result+"");
					System.out.println("result===   "+result);
				}
				System.out.println("id   -----  "+product2.getId());
				System.out.println("Progress  -----  "+product2.getProgress());
				
			}
		
	}
	
}
