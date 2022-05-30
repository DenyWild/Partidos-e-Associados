package br.com.partidosassociados.PartidosAssociados.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.models.enums.Sexo;
import lombok.Data;

@Entity
@Data
public class Associados {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "campo nome é obrigatório, é preciso preenche-lo!")
	private String nome;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "campo cargoPolitico é obrigatório, é preciso preenche-lo!")
	private CargoPolitico cargoPolitico;
	@NotNull(message = "campo dataDeNascimento é obrigatório, é preciso preenche-lo!")
	@JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dataDeNascimento;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "campo sexo é obrigatório, é preciso preenche-lo!")
	private Sexo sexo;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "partidos_id", nullable = true)
	@JsonIgnore
	private Partidos partidos;

	public Associados() {

	}

	public Associados(@NotBlank(message = "campo nome é obrigatório, é preciso preenche-lo!") String nome,
			@NotNull(message = "campo cargoPolitico é obrigatório, é preciso preenche-lo!") CargoPolitico cargoPolitico,
			@NotBlank(message = "campo dataDeNascimento é obrigatório, é preciso preenche-lo!") Date dataDeNascimento,
			@NotNull(message = "campo sexo é obrigatório, é preciso preenche-lo!") Sexo sexo, Partidos partidos) {
		super();
		this.nome = nome;
		this.cargoPolitico = cargoPolitico;
		this.dataDeNascimento = dataDeNascimento;
		this.sexo = sexo;
		this.partidos = partidos;
	}

}
