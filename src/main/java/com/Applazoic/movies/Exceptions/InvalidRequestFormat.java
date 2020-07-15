package com.Applazoic.movies.Exceptions;

public class InvalidRequestFormat extends RuntimeException {

	public InvalidRequestFormat(String message, String movieName) {
		super(message + " for " + movieName);
	}

}
