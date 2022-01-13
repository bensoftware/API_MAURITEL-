package com.bpm.apimauritel.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.bpm.apimauritel.services.TraitementService;



@Configuration
@EnableScheduling
@EnableAsync
public class TachePlannifierConfig {

    final Logger logger = LoggerFactory.getLogger(TachePlannifierConfig.class);

    

	@Autowired
	TraitementService traitementService;
	
	@Async
	@Scheduled(cron = "${cron.notification.exception}")
	public void notificationException() throws Exception  {
		try {
			logger.info("Batch traitement Exception API recharge Mauritel");
			traitementService.traitementException();		
		} catch (Exception e) {
				}
	}
	

}