package com.bpm.apimauritel.serviceImpl;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.helpers.MauritelApiHelper;
import com.bpm.apimauritel.services.GlobalService;
import com.bpm.apimauritel.services.SmsService;
import com.bpm.apimauritel.services.TraitementService;

@Service
public class TraitementServiceImpl implements TraitementService{

	@Autowired
	private GlobalService globalService;
	
	@Autowired
	private SmsService smsService;

    private final Logger logger = LoggerFactory.getLogger(TraitementServiceImpl.class);

	@Override
	public void traitementException() {
		
		Date dateEx=  globalService.getDateException();
		int detail =  globalService.getDelaiException();
		
		if(dateEx!=null) {
			boolean verif=MauritelApiHelper.isDetailException(dateEx, detail);
			
			if(verif){
				Date dateNotification=globalService.getNotification();
				
				if(dateNotification==null) {
					// Envoyer une notif
					logger.info("MAURITEL API - Exception reponse BD MAURITEL date debut de l'exception : " + dateEx);
					try {
						smsService.sendSms("MAURITEL API - Exception reponse BD MAURITEL date debut de l'exception : " + dateEx);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					globalService.setNotification(new Date());
				}
				else {
					// Relance notification
					int detailRetry= globalService.getDelaiRetryException();
					
					boolean verifRetry=MauritelApiHelper.isDetailException(dateNotification, detailRetry);
                    
					if(verifRetry) {
						// Envoyer une notif
						logger.info("MAURITEL API - Rappel, Exception reponse BD MAURITEL date debut de l exception "+dateEx);
						try {
							smsService.sendSms("MAURITEL API - Rappel, Exception reponse BD MAURITEL date debut de l exception "+dateEx);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						globalService.setNotification(new Date());
					}
				}
			}	
		}
	}

	@Override
	public void responseException() {
		Date dateEx= globalService.getDateException();
		if(dateEx==null) {
			globalService.setDateException(new Date());
			logger.info("RESPONSE SUCCESS MOOV-Mauritel : " + new Date());
		}
	}

	@Override
	public void responseSuccess() {
		Date dateEx= globalService.getDateException();
		if(dateEx!=null) {
			globalService.setDateException(null);
			globalService.setNotification(null);
			logger.info("RESPONSE SUCCESS MOOV-Mauritel : " + new Date());
		}
	}
	
}
