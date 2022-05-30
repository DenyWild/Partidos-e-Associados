package br.com.partidosassociados.PartidosAssociados.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.partidosassociados.PartidosAssociados.models.enums.Ideologia;
import lombok.Data;

@Entity
@Data
public class Partidos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "campo nome é obrigatório")
	private String nome;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "campo ideologia é obrigatório")
	private Ideologia ideologia;
	@NotBlank(message = "campo sigla é obrigatório")
	@Size(min = 2, max = 5)
	private String sigla;
	@NotNull(message = "campo dataDeFundação é obrigatório")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataDeFundacao;

	@OneToMany(mappedBy = "partidos")
	@JsonIgnore
	private List<Associados> associados;

	public Partidos() {

	}

	public Partidos(@NotBlank(message = "campo nome é obrigatório") String nome,
			@NotNull(message = "campo ideologia é obrigatório") Ideologia ideologia,
			@NotBlank(message = "campo sigla é obrigatório") @Size(min = 2, max = 5) String sigla,
			@NotNull(message = "campo dataDeFundação é obrigatório") Date dataDeFundacao, List<Associados> associados) {
		super();
		this.nome = nome;
		this.ideologia = ideologia;
		this.sigla = sigla;
		this.dataDeFundacao = dataDeFundacao;
		this.associados = associados;
	}

}
