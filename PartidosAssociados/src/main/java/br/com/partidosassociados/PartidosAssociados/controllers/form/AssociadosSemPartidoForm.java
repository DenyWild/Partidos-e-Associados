package br.com.partidosassociados.PartidosAssociados.controllers.form;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.partidosassociados.PartidosAssociados.models.enums.CargoPolitico;
import br.com.partidosassociados.PartidosAssociados.models.enums.Sexo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociadosSemPartidoForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "campo nome é obrigatório, é preciso preenche-lo!")
	private String nome;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "campo cargoPolitico é obrigatório, é preciso preenche-lo!")
	private CargoPolitico cargoPolitico;
	@NotNull(message = "campo dataDeNascimento é obrigatório, é preciso preenche-lo!")
	private Date dataDeNascimento;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "campo sexo é obrigatório, é preciso preenche-lo!")
	private Sexo sexo;

}
