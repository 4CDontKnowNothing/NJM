package org.ht.util;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class Job1 extends QuartzJobBean{
	private int timeout;  
	private static int i = 0;  

	//After the dispatching factory is instantiated, the dispatching starts after the timeout time 

	public void setTimeout(int timeout) {  

	this.timeout = timeout;  

	}

	/** 

	* The specific task to be scheduled 

	*/  
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("Scheduled task execution…");  
	}  
	
}
