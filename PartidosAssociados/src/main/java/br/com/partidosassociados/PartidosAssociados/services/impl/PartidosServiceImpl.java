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

import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosAssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;
import br.com.partidosassociados.PartidosAssociados.exceptions.EntityNotFound;
import br.com.partidosassociados.PartidosAssociados.exceptions.PartyHasNoMembers;
import br.com.partidosassociados.PartidosAssociados.exceptions.UpdateNotAllowed;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import br.com.partidosassociados.PartidosAssociados.repositories.PartidosRepository;
import br.com.partidosassociados.PartidosAssociados.services.PartidosService;

@Service
public class PartidosServiceImpl implements PartidosService {

	@Autowired
	private PartidosRepository partidosRepository;

	public Partidos inserirPartido(@Valid Partidos partidos) {
		return partidosRepository.save(partidos);

	}

	public PartidosDto atualizarPartido(@PathVariable Long id, @Valid Partidos partidos) {
		Partidos partidoEscolhido = partidosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFound("id not found" + id));

		if (partidos.equals(partidoEscolhido)) {
			throw new UpdateNotAllowed("Não teve mudança em nenhum campo, por isso não é possível atualizar");
		}
		BeanUtils.copyProperties(partidos, partidoEscolhido, "id");
		Partidos partido = partidosRepository.save(partidoEscolhido);
		PartidosDto partidoDto = new PartidosDto(partido);
		return partidoDto;

	}

	public List<PartidosDto> listarTodos(Ideologia ideologia) {
		return validaParametroParaListarPartidos(ideologia);

	}

	public PartidosDto listaPartidoPorId(Long id) {
		Partidos part = partidosRepository.findById(id).orElseThrow(() -> new EntityNotFound("id not found " + id));
		PartidosDto partido = new PartidosDto(part);
		return partido;
	}

	public PartidosAssociadosDto listarAssociadosAoPartido(Long id) {
		Partidos pars = (partidosRepository.findById(id).orElseThrow(() -> new EntityNotFound("id not found " + id)));
		if (pars.getAssociados().isEmpty()) {
			throw new PartyHasNoMembers("Party id with members not found ");
		}
		PartidosAssociadosDto parAssoc = new PartidosAssociadosDto(pars);
		return parAssoc;

	}

	public ResponseEntity<?> deletarPartidoPorId(Long id) {
		Optional<Partidos> partidos = partidosRepository.findById(id);
		if (partidos.isPresent()) {
			partidosRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	public List<PartidosDto> validaParametroParaListarPartidos(Ideologia ideologia) {
		if (ideologia != null) {
			List<Partidos> par = partidosRepository.findByIdeologia(ideologia);
			return converteListDePartidosParaDto(par);
		}
		List<Partidos> par = partidosRepository.findAll();
		return converteListDePartidosParaDto(par);

	}

	public List<PartidosDto> converteListDePartidosParaDto(List<Partidos> par) {
		List<PartidosDto> listAll = par.stream().map(x -> new PartidosDto(x)).collect(Collectors.toList());
		return listAll;
	}

}
