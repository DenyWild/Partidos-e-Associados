package br.com.partidosassociados.PartidosAssociados.controllers.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssociadosAUmPartidoForm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	private Long idAssociado;
	@NotNull
	private Long idPartido;

}
