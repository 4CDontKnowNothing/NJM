package org.ht.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SimpleTriggerJob extends QuartzJobBean{

	
	/**
	 *   This class is a business class that executes every so often. 
	 *   Write your business here
	 * 
	 */
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
	}

}
