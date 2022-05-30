package br.com.partidosassociados.PartidosAssociados.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;

@FeignClient(value = "partido-consumer", url = "https://localhost:8080/partidos/")
public interface PartidosFeign {

	@GetMapping(path = "/{id}")
	public PartidosDto listarPartidoPorId(@PathVariable Long id);

}
