package br.com.partidosassociados.PartidosAssociados.exceptions;

public class PartyHasNoMembers extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PartyHasNoMembers(String message) {
		super(message);
	}

}
