package com.bpm.apimauritel.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bpm.apimauritel.entities.DetailService;
import com.bpm.apimauritel.repositories.DetailServiceRepository;
import com.bpm.apimauritel.services.DetailServiceService;

@Service
public class DetailServiceServiceImpl implements DetailServiceService {

	@Autowired
	DetailServiceRepository detailServiceRepository;

	@Override
	public void save(DetailService detailService) throws Exception {
		// TODO Auto-generated method stub
		try {
			detailServiceRepository.save(detailService);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void update(DetailService detailService) throws Exception {
		try {
			detailServiceRepository.save(detailService);
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public DetailService findDetailServiceByDescription(String description) {
		// TODO Auto-generated method stub
		return detailServiceRepository.findDetailServiceByDescription(description);
	}

	@Override
	public DetailService findDetailServiceByIdService(Long idService) throws Exception {
		// TODO Auto-generated method stub
		return detailServiceRepository.getDetailServiceByIdService(idService);
	}

}
