package com.moto.service.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.moto.service.entidades.Moto;
import com.moto.service.repositorio.MotoRepository;

@Service
public class MotoService {

	@Autowired
	private MotoRepository motoRepository;
	
	public List<Moto> getAll(){
		return motoRepository.findAll();
	}
	
	public Moto getMotoById(int id) {
		return motoRepository.findById(id).orElse(null);
	}
	
	public Moto save(Moto moto) {
		
		Moto nuevaMoto = motoRepository.save(moto);
		
		return nuevaMoto;
		
	}
	
	public List<Moto> byUsuarioId(int usuarioId){
		
		return motoRepository.findByUsuarioId(usuarioId);
		
	}
}