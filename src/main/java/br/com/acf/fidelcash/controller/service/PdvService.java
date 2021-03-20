package br.com.acf.fidelcash.controller.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.acf.fidelcash.modelo.Pdv;
import br.com.acf.fidelcash.repository.PdvRepository;

@Service
public class PdvService {
	
	@Autowired
	private PdvRepository pdvRepository;
	
	@Transactional(rollbackFor = { Exception.class })
	public void save(Pdv pdv) {
		pdvRepository.save(pdv);
	}

	public Optional<Pdv> findById(Integer id) {
		return pdvRepository.findById(id);
	}

	public Pdv findByMacAdress(String macAddress) {
		return pdvRepository.findByMacAddress(macAddress);
	}

	

}
