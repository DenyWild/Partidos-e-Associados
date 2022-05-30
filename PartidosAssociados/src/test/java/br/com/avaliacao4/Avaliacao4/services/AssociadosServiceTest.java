package br.com.avaliacao4.Avaliacao4.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.avaliacao4.Avaliacao4.builders.AssociadosBuilder;
import br.com.avaliacao4.Avaliacao4.builders.PartidosBuilder;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosSemVinculoAoPartidoDto;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosAUmPartidoForm;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosSemPartidoForm;
import br.com.partidosassociados.PartidosAssociados.exceptions.EntityNotFound;
import br.com.partidosassociados.PartidosAssociados.exceptions.MethodArgumentNotValid;
import br.com.partidosassociados.PartidosAssociados.exceptions.UpdateNotAllowed;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.models.enums.Sexo;
import br.com.partidosassociados.PartidosAssociados.repositories.AssociadosRepository;
import br.com.partidosassociados.PartidosAssociados.repositories.PartidosRepository;
import br.com.partidosassociados.PartidosAssociados.services.impl.AssociadosServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssociadosServiceTest {

	private static final long idInexistente = 5L;

	@MockBean
	private AssociadosRepository associadosRepository;

	@MockBean
	private PartidosRepository partidosRepository;

	@Autowired
	private AssociadosServiceImpl associadosServiceImpl;

	@Test
	@DisplayName(" Lista Todos os Associados com um Determinado Cargo Político ")
	public void deveriaListarTodosAssociadosPeloCargoPolitico() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findByCargoPolitico(CargoPolitico.NENHUM)).thenReturn(List.of(associado));

		List<AssociadosSemVinculoAoPartidoDto> asso = this.associadosServiceImpl.listarTodos(CargoPolitico.NENHUM);

		assertNotNull(asso);
		assertEquals(1, asso.size());
		assertEquals(AssociadosSemVinculoAoPartidoDto.class, asso.get(0).getClass());
	}

	@Test
	@DisplayName(" Lista Todos os Associados ")
	public void deveriaListarTodosAssociados() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findAll()).thenReturn(List.of(associado));

		List<AssociadosSemVinculoAoPartidoDto> asso = this.associadosServiceImpl.listarTodos(null);

		assertNotNull(asso);
		assertEquals(1, asso.size());
		assertEquals(AssociadosSemVinculoAoPartidoDto.class, asso.get(0).getClass());
	}

	@Test
	@DisplayName(" Busca Associado ")
	public void deveriaBuscarAssociadoPorId() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));

		AssociadosDto associado2 = this.associadosServiceImpl.listaAssociadoPorId(associado.getId());

		assertNotNull(associado2);
		assertEquals(associado2.getId(), associado.getId());
		assertEquals(associado2.getNome(), associado.getNome());
		assertEquals(associado2.getCargoPolitico(), associado.getCargoPolitico());
		assertEquals(associado2.getDataDeNascimento(), associado.getDataDeNascimento());
		assertEquals(associado2.getSexo(), associado.getSexo());
		assertEquals(associado2.getPartidos(), associado.getPartidos());

	}

	@Test
	@DisplayName(" Inserir um Associado ")
	public void deveriaInserirAssociado() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.save(any(Associados.class))).thenReturn(associado);

		Associados asso = this.associadosServiceImpl.inserirAssociado(AssociadosBuilder.getAssociadosSemPartidoForm());

		assertNotNull(asso);
		assertThat(asso.getId()).isNotNull();
		assertThat(asso.getNome()).isEqualTo(associado.getNome());
		assertThat(asso.getCargoPolitico()).isEqualTo(associado.getCargoPolitico());
		assertThat(asso.getDataDeNascimento()).isEqualTo(associado.getDataDeNascimento());
		assertThat(asso.getSexo()).isEqualTo(associado.getSexo());

	}

	@Test
	@DisplayName(" Atualiza um Associado vinculando a um Partido ")
	public void deveriaAtualizarAssociadoVinculadoAUmPartido() {

		AssociadosAUmPartidoForm associado = AssociadosBuilder.getAssociadosAUmPartidoForm();

		Associados associad = AssociadosBuilder.getAssociados();

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associad));
		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		AssociadosDto asso = this.associadosServiceImpl.inserirAssociadoVinculadoAUmPartido(associado);

		assertNotNull(asso);

	}

	@Test
	@DisplayName(" Atualiza um Associado ")
	public void deveriaAtualizarAssociado() {

		Associados associado = AssociadosBuilder.getAssociados();
		Associados assoc = outroAssociado();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		when(this.associadosRepository.save(any(Associados.class))).thenReturn(associado);

		AssociadosDto assocDto = this.associadosServiceImpl.atualizarAssociado(associado.getId(), assoc);

		assertNotNull(assocDto);
		assertNotNull(assocDto.getId());
		assertNotEquals(assocDto.getClass(), associado.getClass());
		assertNotEquals(assocDto, associado);

	}

	@Test
	@DisplayName(" Lança Exception quando na atualização é passado um id Inexistente ")
	public void deveriaLancarUpdateNotAllowedSeIdPassadoNaAtualizacaoForInexistente() {

		Associados associado = AssociadosBuilder.getAssociados();
		Associados assoc = outroAssociado();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		when(this.associadosRepository.save(any(Associados.class))).thenReturn(associado);
		try {
			AssociadosDto assocDto = this.associadosServiceImpl.atualizarAssociado(idInexistente, assoc);
		} catch (Exception ex) {
			assertEquals(UpdateNotAllowed.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception quando tentar atualizar uma entidade com os mesmos conteúdos ")
	public void deveriaLancarUpdateNotAllowedSeAssociadoPassadoNaAtualizacaoForIgualAoExistente() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		when(this.associadosRepository.save(any(Associados.class))).thenReturn(associado);
		try {
			AssociadosDto assocDto = this.associadosServiceImpl.atualizarAssociado(associado.getId(), associado);
		} catch (Exception ex) {
			assertEquals(UpdateNotAllowed.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception quando for inserir e faltar conteudo em qualquer campo da entidade ")
	public void deveriaLancarMethodArgumentNotValidQuandoFaltarAlgumDadoNaInsercaoDoAssociado() {

		Associados associado = AssociadosBuilder.getAssociados();
		associado.setNome(null);

		when(this.associadosRepository.save(any(Associados.class))).thenReturn(associado);

		AssociadosSemPartidoForm assoc = AssociadosBuilder.getAssociadosSemPartidoForm();
		assoc.setNome(null);

		try {
			Associados asso = this.associadosServiceImpl.inserirAssociado(assoc);
		} catch (Exception ex) {

			assertEquals(MethodArgumentNotValid.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception quando Associado buscado é nulo ")
	public void deveriaLancarNullPointerExceptionSeAssociadoBuscadoENUlo() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		try {
			AssociadosDto asso = this.associadosServiceImpl.listaAssociadoPorId(idInexistente);

		} catch (Exception ex) {
			assertEquals(NullPointerException.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception quando busca Associado por id Inexistente ")
	public void deveriaLancarEntityNotFoundAoBuscarAssociadoPorIdInexistente() {

		Associados associado = AssociadosBuilder.getAssociados();

		Mockito.when(this.associadosRepository.findById(anyLong())).thenThrow(new EntityNotFound("id not found"));

		try {
			this.associadosServiceImpl.listaAssociadoPorId(null);
		} catch (Exception ex) {
			assertEquals(EntityNotFound.class, ex.getClass());

		}

	}

	@Test
	@DisplayName(" Deleta um Associado ")
	public void deveriaDeletarAssociadoPorId() {

		Associados associado = AssociadosBuilder.getAssociados();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		doNothing().when(this.associadosRepository).deleteById(anyLong());

		this.associadosServiceImpl.deletarAssociadoPorId(associado.getId());

		verify(this.associadosRepository, times(1)).deleteById(associado.getId());

	}

	@Test
	@DisplayName(" Deleta vinculo de um Associado ao Partido ")
	public void deveriaDeletarAssociadoVinculadoAoPartidoPorId() {

		Associados associado = AssociadosBuilder.getAssociados();
		Partidos partido = PartidosBuilder.getPartidos();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		ResponseEntity<?> deletarAssociadoAUmPartidoPorId = this.associadosServiceImpl
				.deletarAssociadoAUmPartidoPorId(associado.getId(), partido.getId());

		assertNotEquals(deletarAssociadoAUmPartidoPorId.getBody(), associado);

	}

	@Test
	@DisplayName(" Lança Exception quando tentar deletar por Id inexistente ")
	public void deveriaLancarEntityNotFoundSeDeletarAssociadoVinculadoAoPartidoNaoExistirOId() {
		Associados associado = AssociadosBuilder.getAssociados();
		Partidos partido = PartidosBuilder.getPartidos();

		when(this.associadosRepository.findById(anyLong())).thenReturn(Optional.of(associado));
		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		try {
			this.associadosServiceImpl.deletarAssociadoAUmPartidoPorId(idInexistente, idInexistente);
		} catch (Exception ex) {
			assertEquals(EntityNotFound.class, ex.getClass());
		}
	}

	public Associados outroAssociado() {
		Associados associa1 = new Associados();
		associa1.setId(2L);
		associa1.setNome("rara");
		associa1.setCargoPolitico(CargoPolitico.SENADOR);
		associa1.setDataDeNascimento(Date.from(Instant.now()));
		associa1.setSexo(Sexo.FEMININO);
		associa1.setPartidos(null);
		return associa1;
	}

}
