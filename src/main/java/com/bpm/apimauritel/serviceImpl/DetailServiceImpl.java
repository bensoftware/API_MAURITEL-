package com.bpm.apimauritel.serviceImpl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.Detail;
import com.bpm.apimauritel.repositories.DetailRepository;
import com.bpm.apimauritel.services.AmountService;
import com.bpm.apimauritel.services.DetailService;
import com.bpm.apimauritel.services.ServiceMauritelService;

@Service
public class DetailServiceImpl implements DetailService {

	final Logger logger = LoggerFactory.getLogger(DetailServiceImpl.class);

	@Autowired
	DetailRepository detailRepository;

	private ServiceMauritelService serviceMauritelService;

	private AmountService amountService;

	public DetailServiceImpl(@Autowired ServiceMauritelService serviceMauritelService,
			@Autowired AmountService amountService) {
		this.amountService = amountService;
		this.serviceMauritelService = serviceMauritelService;
	}

	@Override
	public void save(Detail detail) throws Exception {
		// TODO Auto-generated method stub
		try {
			detailRepository.save(detail);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION SAVE DETAIL : " + e.getMessage());
		}

	}

	@Override
	public void update(Detail detail) throws Exception {
		// TODO Auto-generated method stub
		try {
			detailRepository.save(detail);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION SAVE DETAIL : " + e.getMessage());
		}
	}

	@Override
	public List<Detail> findAllDetailServices() throws Exception {
		// TODO Auto-generated method stub
		try {
		return	detailRepository.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("EXCEPTION SAVE DETAIL : " + e.getMessage());
		}
		return null;
	}

}
