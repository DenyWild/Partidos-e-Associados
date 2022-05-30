package br.com.avaliacao4.Avaliacao4.builders;

import java.sql.Date;
import java.time.Instant;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.AssociadosSemVinculoAoPartidoDto;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosAUmPartidoForm;
import br.com.partidosassociados.PartidosAssociados.controllers.form.AssociadosSemPartidoForm;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import br.com.partidosassociados.PartidosAssociados.models.enums.Sexo;

public class AssociadosBuilder {

	public static Associados getAssociados() {

		Associados associados = new Associados();
		associados.setId(1L);
		associados.setNome("lala");
		associados.setCargoPolitico(CargoPolitico.NENHUM);
		associados.setDataDeNascimento(Date.from(Instant.now()));
		associados.setSexo(Sexo.MASCULINO);
		associados.setPartidos(null);

		return associados;

	}

	public static AssociadosDto getAssociadosDto() {

		Associados associado = new Associados();
		AssociadosDto asso = new AssociadosDto(associado);
		Partidos partido = new Partidos();

		partido.setId(1L);
		partido.setNome("Partido do lala");
		partido.setIdeologia(Ideologia.CENTRO);
		partido.setSigla("PL");
		partido.setDataDeFundacao(Date.from(Instant.now()));

		associado.setId(1L);
		associado.setNome("lele");
		associado.setCargoPolitico(CargoPolitico.VEREADOR);
		associado.setDataDeNascimento(Date.from(Instant.now()));
		associado.setPartidos(partido);

		return asso;

	}

	public static AssociadosSemPartidoForm getAssociadosSemPartidoForm() {

		AssociadosSemPartidoForm associado = new AssociadosSemPartidoForm("lili", CargoPolitico.PREFEITO,
				Date.from(Instant.now()), Sexo.MASCULINO);

		return associado;

	}

	public static AssociadosSemVinculoAoPartidoDto getAssociadosSemVinculoAoPartidoDto() {

		Associados assoc = new Associados();
		assoc.setId(1L);
		assoc.setNome("lele");
		assoc.setCargoPolitico(CargoPolitico.VEREADOR);
		assoc.setDataDeNascimento(Date.from(Instant.now()));

		AssociadosSemVinculoAoPartidoDto associado = new AssociadosSemVinculoAoPartidoDto(assoc);

		return associado;

	}

	public static AssociadosAUmPartidoForm getAssociadosAUmPartidoForm() {

		AssociadosAUmPartidoForm associado = new AssociadosAUmPartidoForm();
		associado.setIdAssociado(1L);
		associado.setIdPartido(1L);

		return associado;

	}

}
