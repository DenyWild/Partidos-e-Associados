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

import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosSemVinculoAoPartidoDto;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosAUmPartidoForm;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosSemPartidoForm;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.services.impl.AssociadosServiceImpl;

@RestController
@RequestMapping("/associados")
public class AssociadosController {

	@Autowired
	private AssociadosServiceImpl associadosServiceImpl;

	@PostMapping
	@Transactional
	public ResponseEntity<Associados> inserirAssociado(
			@RequestBody @Valid AssociadosSemPartidoForm associadosSemPartidoForm) {
		Associados ass = associadosServiceImpl.inserirAssociado(associadosSemPartidoForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(ass);
	}

	@PutMapping(value = "/partidos")
	@Transactional
	public ResponseEntity<AssociadosDto> inserirAssociadoVinculadoAUmPartido(
			@RequestBody @Valid AssociadosAUmPartidoForm associadosAUmPartidoForm) {
		AssociadosDto ass = associadosServiceImpl.inserirAssociadoVinculadoAUmPartido(associadosAUmPartidoForm);
		return ResponseEntity.status(HttpStatus.CREATED).body(ass);
	}

	@PutMapping(path = "/{id}")
	@Transactional
	public AssociadosDto atualizarAssociado(@PathVariable Long id, @RequestBody @Valid Associados associados) {
		return associadosServiceImpl.atualizarAssociado(id, associados);
	}

	@GetMapping
	public List<AssociadosSemVinculoAoPartidoDto> listarTodos(
			@RequestParam(required = false) CargoPolitico cargoPolitico) {
		return associadosServiceImpl.listarTodos(cargoPolitico);
	}

	@GetMapping(path = "/{id}")
	public AssociadosDto listarAssociadoPorId(@PathVariable Long id) {
		return associadosServiceImpl.listaAssociadoPorId(id);
	}

	@DeleteMapping(path = "/{id}")
	@Transactional
	public ResponseEntity<?> deletarAssociadoPorId(@PathVariable Long id) {
		return associadosServiceImpl.deletarAssociadoPorId(id);
	}

	@DeleteMapping(path = "/{idAssociado}/partidos/{idPartidos}")
	@Transactional
	public ResponseEntity<?> deletarAssociadoAUmPartido(@PathVariable Long idAssociado, @PathVariable Long idPartidos) {

		return associadosServiceImpl.deletarAssociadoAUmPartidoPorId(idAssociado, idPartidos);

	}

}
