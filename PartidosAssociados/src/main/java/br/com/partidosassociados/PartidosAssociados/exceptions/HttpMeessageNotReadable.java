package br.com.partidosassociados.PartidosAssociados.exceptions;

public class HttpMeessageNotReadable extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpMeessageNotReadable(String message) {
		super(message);
	}
}
