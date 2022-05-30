package br.com.partidosassociados.PartidosAssociados.controllers;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosAssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import br.com.partidosassociados.PartidosAssociados.services.impl.PartidosServiceImpl;

@RestController
@RequestMapping("/partidos")
public class PartidosController {

	@Autowired
	private PartidosServiceImpl partidosServiceImpl;

	@PostMapping
	@Transactional
	public ResponseEntity<Partidos> inserirPartido(@RequestBody @Valid Partidos partidos) {
		Partidos par = partidosServiceImpl.inserirPartido(partidos);
		return ResponseEntity.status(HttpStatus.CREATED).body(par);
	}

	@PutMapping(path = "/{id}")
	@Transactional
	public PartidosDto atualizarPartido(@PathVariable Long id, @RequestBody @Valid Partidos partidos) {
		return partidosServiceImpl.atualizarPartido(id, partidos);
	}

	@GetMapping
	public List<PartidosDto> listarTodos(@RequestParam(required = false) Ideologia ideologia) {

		return partidosServiceImpl.listarTodos(ideologia);
	}

	@GetMapping(path = "/{id}")
	public PartidosDto listarPartidoPorId(@PathVariable Long id) {
		return partidosServiceImpl.listaPartidoPorId(id);
	}

	@GetMapping(path = "/{id}/associados")
	public PartidosAssociadosDto listarAssociadosAoPartido(@PathVariable Long id) {
		return partidosServiceImpl.listarAssociadosAoPartido(id);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletarPartidoPorId(@PathVariable Long id) {
		return partidosServiceImpl.deletarPartidoPorId(id);
	}

}
