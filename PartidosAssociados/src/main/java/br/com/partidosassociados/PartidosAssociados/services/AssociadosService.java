package br.com.partidosassociados.PartidosAssociados.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosSemVinculoAoPartidoDto;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosAUmPartidoForm;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosSemPartidoForm;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;

public interface AssociadosService {

	public Associados inserirAssociado(AssociadosSemPartidoForm associados);

	public AssociadosDto inserirAssociadoVinculadoAUmPartido(AssociadosAUmPartidoForm associadosAUmPartidoForm);

	public AssociadosDto atualizarAssociado(Long id, Associados associados);

	public List<AssociadosSemVinculoAoPartidoDto> listarTodos(CargoPolitico cargoPolitico);

	public AssociadosDto listaAssociadoPorId(Long id);

	public ResponseEntity<?> deletarAssociadoPorId(Long id);

	public ResponseEntity<?> deletarAssociadoAUmPartidoPorId(Long idAssociado, Long idPartido);

	public List<AssociadosSemVinculoAoPartidoDto> converteAssociadosSemVinculoParaDto(List<Associados> ass);

}
