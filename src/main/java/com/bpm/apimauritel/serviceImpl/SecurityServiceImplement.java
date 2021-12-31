package com.bpm.apimauritel.serviceImpl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.services.SecurityService;

@Service
public class SecurityServiceImplement implements SecurityService {

	private final static Logger logger = LoggerFactory.getLogger(SecurityServiceImplement.class);

	@Value("#{'${ipList.allowed}'.split(',')}")
	public List<String> ipListIPexisted;

	@Override
	public boolean checkIP(String inCommingIpAdress){
		// TODO Auto-generated method stub
		if(!inCommingIpAdress.isEmpty());
		return IsIpMatch(inCommingIpAdress);
	}

	@Override
	public boolean IsIpMatch(String IncommingIPadress){
		// String generatedSecuredPasswordHash =
		BCrypt.hashpw(IncommingIPadress,BCrypt.gensalt(12));
		// System.out.println(generatedSecuredPasswordHash);
		boolean matched = false;
		for (String encryptedIpInList : ipListIPexisted){
			if (BCrypt.checkpw(IncommingIPadress, encryptedIpInList))
			matched = true;
		}
		//logger.info(""+ipListIPexisted.size());
		return matched;
	}
	
	
	
}
