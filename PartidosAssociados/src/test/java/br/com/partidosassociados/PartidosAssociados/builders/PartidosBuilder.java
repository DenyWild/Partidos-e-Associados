package br.com.partidosassociados.PartidosAssociados.builders;

import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosAssociadosDto;
import br.com.partidosassociados.PartidosAssociados.controllers.dto.PartidosDto;
import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;

public class PartidosBuilder {

	public static Partidos getPartidos() {

		Partidos partido = new Partidos();
		partido.setId(1L);
		partido.setNome("lala");
		partido.setIdeologia(Ideologia.CENTRO);
		partido.setSigla("lili");
		partido.setDataDeFundacao(Date.from(Instant.now()));
		partido.setAssociados(null);

		return partido;

	}

	public static PartidosDto getPartidosDto() {

		PartidosDto partido = new PartidosDto();
		partido.setId(1L);
		partido.setNome("lala");
		partido.setIdeologia(Ideologia.CENTRO);
		partido.setSigla("lili");
		partido.setDataDeFundacao(Date.from(Instant.now()));

		return partido;
	}

	public static PartidosAssociadosDto getPartidosAssociadosDto() {

		Associados assoc = AssociadosBuilder.getAssociados();
		Associados assoc2 = AssociadosBuilder.getAssociados();
		List<Associados> assocs = new ArrayList<>();
		assocs.add(assoc);
		assocs.add(assoc2);

		Partidos partido = new Partidos();
		partido.setId(1L);
		partido.setNome("lala");
		partido.setIdeologia(Ideologia.CENTRO);
		partido.setSigla("lili");
		partido.setDataDeFundacao(Date.from(Instant.now()));
		partido.setAssociados(assocs);

		PartidosAssociadosDto partidoAssoc = new PartidosAssociadosDto(partido);

		return partidoAssoc;

	}

}
