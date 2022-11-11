package com.tbc.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tbc.model.User;
import com.tbc.repository.UserRepository;
import com.tbc.service.EmailService;

public class CheckVerification extends QuartzJobBean 
{
	@Autowired private UserRepository ur;
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException 
	{
		Date current = Calendar.getInstance().getTime();
		List<User> user=ur.findByEnabled(false);
		for(int i=0;i<user.size();i++)
		{
			long diff=TimeUnit.MILLISECONDS.toSeconds(current.getTime()-user.get(i).getExpireAt().getTime());
			if (diff>=0) {
				ur.delete(user.get(i));
			}
		}
	}
}
