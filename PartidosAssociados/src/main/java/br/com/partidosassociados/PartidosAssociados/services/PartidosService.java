package br.com.partidosassociados.PartidosAssociados.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosAssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;

public interface PartidosService {

	public Partidos inserirPartido(Partidos partidos);

	public PartidosDto atualizarPartido(Long id, Partidos partidos);

	public List<PartidosDto> listarTodos(Ideologia ideologia);

	public PartidosDto listaPartidoPorId(Long id);

	public PartidosAssociadosDto listarAssociadosAoPartido(Long id);

	public ResponseEntity<?> deletarPartidoPorId(Long id);

	public List<PartidosDto> validaParametroParaListarPartidos(Ideologia ideologia);

	public List<PartidosDto> converteListDePartidosParaDto(List<Partidos> par);

}
