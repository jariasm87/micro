package com.usuario.service.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.usuario.service.modelos.Coche;
//url = "http://localhost:8002",
@FeignClient(name = "coche-service", path = "/coche")
//@RequestMapping("/coche")
public interface CocheFeignClient {

	@PostMapping()
	public Coche save(@RequestBody Coche coche);
	
	@GetMapping("/usuario/{usuarioId}")
	public List<Coche> getCoches(@PathVariable int usuarioId);
}
