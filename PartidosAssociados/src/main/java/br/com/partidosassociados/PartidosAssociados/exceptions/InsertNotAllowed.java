package br.com.partidosassociados.PartidosAssociados.exceptions;

public class InsertNotAllowed extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InsertNotAllowed(String message) {
		super(message);
	}

}
