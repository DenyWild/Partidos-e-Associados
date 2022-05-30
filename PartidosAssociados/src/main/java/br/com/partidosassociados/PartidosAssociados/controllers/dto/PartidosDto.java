package br.com.partidosassociados.PartidosAssociados.controllers.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import lombok.Data;

@Data
public class PartidosDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Ideologia ideologia;
	private String sigla;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataDeFundacao;

	public PartidosDto() {

	}

	public PartidosDto(Partidos obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.ideologia = obj.getIdeologia();
		this.sigla = obj.getSigla();
		this.dataDeFundacao = obj.getDataDeFundacao();

	}

}
