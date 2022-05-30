package br.com.partidosassociados.PartidosAssociados.controllers.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import lombok.Data;

@Data
public class PartidosAssociadosDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	private Ideologia ideologia;
	private String sigla;
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataDeFundacao;
	private List<Associados> associados;

	public PartidosAssociadosDto(Partidos obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.ideologia = obj.getIdeologia();
		this.sigla = obj.getSigla();
		this.dataDeFundacao = obj.getDataDeFundacao();
		this.associados = obj.getAssociados();
	}

}
