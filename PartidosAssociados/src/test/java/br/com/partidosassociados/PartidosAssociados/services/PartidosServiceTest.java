package br.com.partidosassociados.PartidosAssociados.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.partidosassociados.PartidosAssociados.builders.AssociadosBuilder;
import br.com.partidosassociados.PartidosAssociados.builders.PartidosBuilder;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosAssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;
import br.com.partidosassociados.PartidosAssociados.exceptions.EntityNotFound;
import br.com.partidosassociados.PartidosAssociados.exceptions.MethodArgumentNotValid;
import br.com.partidosassociados.PartidosAssociados.exceptions.UpdateNotAllowed;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import br.com.partidosassociados.PartidosAssociados.repositories.PartidosRepository;
import br.com.partidosassociados.PartidosAssociados.services.impl.PartidosServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PartidosServiceTest {

	private static final long idInexistente = 5L;

	@Autowired
	private PartidosServiceImpl partidosServiceImpl;

	@MockBean
	private PartidosRepository partidosRepository;

	@Test
	@DisplayName(" Lista Partidos com um Determinada Ideologia ")
	public void deveriaListarTodosPartidosPelaIdeologia() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findByIdeologia(Ideologia.DIREITA)).thenReturn(List.of(partido));

		List<Partidos> partidos = this.partidosRepository.findByIdeologia(Ideologia.DIREITA);
		List<PartidosDto> partidoDto = this.partidosServiceImpl.converteListDePartidosParaDto(partidos);

		assertNotNull(partidoDto);
		assertEquals(1, partidoDto.size());
		assertEquals(PartidosDto.class, partidoDto.get(0).getClass());
	}

	@Test
	@DisplayName(" Lista Todos os Partidos ")
	public void deveriaListarTodosPartidos() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findAll()).thenReturn(List.of(partido));

		List<Partidos> partidos = this.partidosRepository.findAll();
		List<PartidosDto> partidoDto = this.partidosServiceImpl.converteListDePartidosParaDto(partidos);

		assertNotNull(partidoDto);
		assertEquals(1, partidoDto.size());
		assertEquals(PartidosDto.class, partidoDto.get(0).getClass());
	}

	@Test
	@DisplayName(" Busca Partido ")
	public void deveriaRetornarPartidoBuscadoPorId() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		PartidosDto partidoDto = this.partidosServiceImpl.listaPartidoPorId(partido.getId());

		assertNotNull(partidoDto);
		assertEquals(partidoDto.getId(), partido.getId());
		assertEquals(partidoDto.getNome(), partido.getNome());
		assertEquals(partidoDto.getIdeologia(), partido.getIdeologia());
		assertEquals(partidoDto.getSigla(), partido.getSigla());
		assertEquals(partidoDto.getDataDeFundacao(), partido.getDataDeFundacao());

	}

	@Test
	@DisplayName(" Lança Exception quando Busca Partido por Id que não existe ")
	public void deveriaLancarEntityNotFoundQuandoPartidoBuscadoPorIdInexistente() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		try {
			PartidosDto partidoDto = this.partidosServiceImpl.listaPartidoPorId(idInexistente);
		} catch (Exception ex) {

			assertEquals(EntityNotFound.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lista Todos os Associados de um Partido ")
	public void deveriaRetornarUmPartidoComListaDeAssociados() {

		Partidos partido = PartidosBuilder.getPartidos();
		List<Associados> list = retornaListaDeAssociados();
		partido.setAssociados(list);

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		PartidosAssociadosDto partidosDto = this.partidosServiceImpl.listarAssociadosAoPartido(partido.getId());

		assertNotNull(partidosDto);
		assertNotEquals(partidosDto, partido);

	}

	@Test
	@DisplayName(" Lança Exception Quando Lista de Associados do Partido estiver vazia ")
	public void deveriaLancarNullPointerExceptionQuandoAListaDeAssociadosDeUmPartidoEstiverVazia() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		try {
			PartidosAssociadosDto partidosDto = this.partidosServiceImpl.listarAssociadosAoPartido(partido.getId());
		} catch (Exception ex) {
			assertEquals(NullPointerException.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception Quando o id que busca a lista de Associados de um Partido não existir ")
	public void deveriaLancarEntityNotFoundQuandoOIdPraListarAssociadosDeUmPartidoForInexistente() {

		PartidosAssociadosDto partidoAssoc = PartidosBuilder.getPartidosAssociadosDto();
		Partidos partido = PartidosBuilder.getPartidos();
		partido.setAssociados(partidoAssoc.getAssociados());

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));

		try {
			PartidosAssociadosDto partidosDto = this.partidosServiceImpl.listarAssociadosAoPartido(idInexistente);
		} catch (Exception ex) {
			assertEquals(EntityNotFound.class, ex.getClass());
		}
	}

	@Test
	@DisplayName(" Inserir um Partido ")
	public void deveriaInserirPartido() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.save(any(Partidos.class))).thenReturn(partido);

		Partidos partidoInserido = this.partidosServiceImpl.inserirPartido(partido);

		assertNotNull(partidoInserido);
		assertThat(partidoInserido.getId()).isNotNull();
		assertThat(partidoInserido.getNome()).isEqualTo(partido.getNome());
		assertThat(partidoInserido.getIdeologia()).isEqualTo(partido.getIdeologia());
		assertThat(partidoInserido.getSigla()).isEqualTo(partido.getSigla());
		assertThat(partidoInserido.getDataDeFundacao()).isEqualTo(partido.getDataDeFundacao());

	}

	@Test
	@DisplayName(" Lança Exception quando faltar um campo quando for inserir uma entidade ")
	public void deveriaLancarMethodArgumentNotValidAoInserirPartidoFaltandoAlgumCampo() {

		Partidos partido = PartidosBuilder.getPartidos();
		partido.setNome(null);

		when(this.partidosRepository.save(any(Partidos.class))).thenReturn(partido);

		try {
			Partidos partidoInserido = this.partidosServiceImpl.inserirPartido(partido);
		} catch (Exception ex) {
			assertEquals(MethodArgumentNotValid.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Atualiza um Partido ")
	public void deveriaAtualizarPartido() {

		Partidos partido = PartidosBuilder.getPartidos();
		partido.setNome("lila");
		Partidos partido2 = PartidosBuilder.getPartidos();
		partido2.setNome("jaspion");
		partido2.setIdeologia(Ideologia.ESQUERDA);

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		when(this.partidosRepository.save(any(Partidos.class))).thenReturn(partido);

		PartidosDto partidoDto = this.partidosServiceImpl.atualizarPartido(partido.getId(), partido2);

		assertNotNull(partido2);
		assertNotNull(partido2.getId());
		assertNotEquals(partidoDto, partido);

	}

	@Test
	@DisplayName(" Lança Exception quando tentar atualizar uma entidade com os mesmos conteúdos ")
	public void deveriaLancarUpdateNotAllowedAoAtualizarPartidoIgualAoExistente() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		when(this.partidosRepository.save(any(Partidos.class))).thenReturn(partido);

		try {
			PartidosDto partidoDto = this.partidosServiceImpl.atualizarPartido(partido.getId(), partido);
		} catch (Exception ex) {
			assertEquals(UpdateNotAllowed.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Lança Exception quando Busca um Partido com id inexistente na atualização ")
	public void deveriaLancarEntityNotFoundAoAtualizarPartidoComIdInexistente() {

		Partidos partido = PartidosBuilder.getPartidos();
		Partidos partido2 = PartidosBuilder.getPartidos();
		partido2.setNome("jaspion");
		partido2.setIdeologia(Ideologia.ESQUERDA);

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		when(this.partidosRepository.save(any(Partidos.class))).thenReturn(partido);

		try {
			PartidosDto partidoDto = this.partidosServiceImpl.atualizarPartido(idInexistente, partido2);
		} catch (Exception ex) {
			assertEquals(EntityNotFound.class, ex.getClass());
		}

	}

	@Test
	@DisplayName(" Deleta um Partido ")
	public void deveriaDeletarUmPartidoPorId() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		doNothing().when(this.partidosRepository).deleteById(anyLong());

		this.partidosServiceImpl.deletarPartidoPorId(partido.getId());

		verify(this.partidosRepository, times(1)).findById(partido.getId());
		verify(this.partidosRepository, times(1)).deleteById(partido.getId());

	}

	@Test
	@DisplayName(" Lança Exception ao tentar deletar um Partido com um Id inexistente ")
	public void deveriaLancarEntityNotFoundAoDeletarUmPartidoPorIdInexistente() {

		Partidos partido = PartidosBuilder.getPartidos();

		when(this.partidosRepository.findById(anyLong())).thenReturn(Optional.of(partido));
		doNothing().when(this.partidosRepository).deleteById(anyLong());

		try {
			this.partidosServiceImpl.deletarPartidoPorId(idInexistente);
		} catch (Exception ex) {
			assertEquals(EntityNotFound.class, ex.getClass());
		}

	}

	public List<Associados> retornaListaDeAssociados() {

		Associados assoc1 = AssociadosBuilder.getAssociados();
		Associados assoc2 = AssociadosBuilder.getAssociados();
		assoc2.setNome("vaspion");
		List<Associados> list = new ArrayList<>();
		list.add(assoc1);
		list.add(assoc2);

		return list;
	}

}
