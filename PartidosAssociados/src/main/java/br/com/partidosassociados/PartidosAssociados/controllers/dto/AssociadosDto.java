package br.com.partidosassociados.PartidosAssociados.controllers.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.partidosassociados.PartidosAssociados.models.Associados;
import br.com.partidosassociados.PartidosAssociados.models.Partidos;
import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.models.enums.Sexo;
import lombok.Data;

@Data
public class AssociadosDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nome;
	@Enumerated(EnumType.STRING)
	private CargoPolitico cargoPolitico;
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataDeNascimento;
	@Enumerated(EnumType.STRING)
	private Sexo sexo;
	private Partidos partidos;

	public AssociadosDto(Associados obj) {
		super();
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.cargoPolitico = obj.getCargoPolitico();
		this.dataDeNascimento = obj.getDataDeNascimento();
		this.sexo = obj.getSexo();
		this.partidos = obj.getPartidos();
	}

}
