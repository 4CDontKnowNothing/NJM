package org.ht.ount;

import org.ht.service.BidService;
import org.ht.service.impl.BidServiceImpl;

/**
 * Bid maturity calculation category is divided into fund due and repayment due
 */
public class BidCount {
	BidService bid = new BidServiceImpl();

	/** Project due processing */
	public void toRaiseMoney() {
		bid.chuli();
	}
	
	/** Fundraising due processing */
	public void to() {
		bid.chuli2();
	}

}
