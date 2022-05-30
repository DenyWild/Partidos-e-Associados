package br.com.partidosassociados.PartidosAssociados.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosSemVinculoAoPartidoDto;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosAUmPartidoForm;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosSemPartidoForm;
import br.com.partidosassociados.PartidosAssociados.exceptions.EntityNotFound;
import br.com.partidosassociados.PartidosAssociados.exceptions.InsertNotAllowed;
import br.com.partidosassociados.PartidosAssociados.exceptions.UpdateNotAllowed;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.repositories.AssociadosRepository;
import br.com.partidosassociados.PartidosAssociados.repositories.PartidosRepository;
import br.com.partidosassociados.PartidosAssociados.services.AssociadosService;
import br.com.partidosassociados.PartidosAssociados.services.PartidosFeign;

@Service
public class AssociadosServiceImpl implements AssociadosService {

	@Autowired
	private AssociadosRepository associadosRepository;

	@Autowired
	private PartidosRepository partidosRepository;

	@Autowired
	private PartidosFeign partidoFeign;

	public Associados inserirAssociado(@Valid AssociadosSemPartidoForm associados) {
		Associados ass = new Associados(associados.getNome(), associados.getCargoPolitico(),
				associados.getDataDeNascimento(), associados.getSexo(), null);
		return associadosRepository.save(ass);
	}

	public AssociadosDto inserirAssociadoVinculadoAUmPartido(@Valid AssociadosAUmPartidoForm associadosAUmPartidoForm) {
		Optional<Associados> ass = associadosRepository.findById(associadosAUmPartidoForm.getIdAssociado());
		Optional<Partidos> par = partidosRepository.findById(associadosAUmPartidoForm.getIdPartido());
		if (ass.isPresent()) {
			if (par.isPresent()) {
				ass.get().setPartidos(par.get());
				associadosRepository.save(ass.get());
				AssociadosDto assD = new AssociadosDto(ass.get());
				return assD;
			}

		}

		throw new InsertNotAllowed("Não é permitido inserir um associado sem vinculo a um partido nesse endpoint");

	}

	public AssociadosDto atualizarAssociado(@PathVariable Long id, @Valid Associados associados) {
		Associados associadoEscolhido = associadosRepository.findById(id).orElseThrow(() -> EntityNotFound(id));
		if (associados.equals(associadoEscolhido)) {
			throw new UpdateNotAllowed("Não teve mudança em nenhum campo, por isso não é possível atualizar");
		}
		BeanUtils.copyProperties(associados, associadoEscolhido, "id");
		Associados associado = associadosRepository.save(associadoEscolhido);
		AssociadosDto assoc = new AssociadosDto(associado);
		return assoc;

	}

	public List<AssociadosSemVinculoAoPartidoDto> listarTodos(CargoPolitico cargoPolitico) {
		if (cargoPolitico != null) {
			List<Associados> ass = associadosRepository.findByCargoPolitico(cargoPolitico);
			return converteAssociadosSemVinculoParaDto(ass);
		}
		List<Associados> ass = associadosRepository.findAll();
		return converteAssociadosSemVinculoParaDto(ass);

	}

	public AssociadosDto listaAssociadoPorId(Long id) {
		Associados associado = associadosRepository.findById(id).orElseThrow(() -> EntityNotFound(id));
		AssociadosDto associ = new AssociadosDto(associado);
		return associ;
	}

	public ResponseEntity<?> deletarAssociadoPorId(Long id) {
		Optional<Associados> associados = associadosRepository.findById(id);
		if (associados.isPresent()) {
			associadosRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> deletarAssociadoAUmPartidoPorId(Long idAssociado, Long idPartido) {
		Optional<Associados> associado = Optional
				.of(associadosRepository.findById(idAssociado).orElseThrow(() -> EntityNotFound(idAssociado)));
		Optional<Partidos> partido = Optional
				.of(partidosRepository.findById(idPartido).orElseThrow(() -> EntityNotFound(idPartido)));
		if (associado.isPresent()) {
			if (partido.isPresent()) {
				if (associado.get().getPartidos() == partido.get()) {
					associado.get().setPartidos(null);
					return ResponseEntity.noContent().build();
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	public List<AssociadosSemVinculoAoPartidoDto> converteAssociadosSemVinculoParaDto(List<Associados> ass) {
		List<AssociadosSemVinculoAoPartidoDto> listAll = ass.stream().map(a -> new AssociadosSemVinculoAoPartidoDto(a))
				.collect(Collectors.toList());
		return listAll;
	}

	public EntityNotFound EntityNotFound(Long id) {
		return new EntityNotFound("Id not found " + id);
	}

}
