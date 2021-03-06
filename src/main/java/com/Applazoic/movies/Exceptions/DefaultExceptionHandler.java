package com.Applazoic.movies.Exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Applazoic.movies.Constants.MovieConstants;

@ControllerAdvice
@RestController
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@ExceptionHandler(InvalidRequestFormat.class)
	public final ResponseEntity<Object> handleInvalidDateException(Exception ex, WebRequest request) {

		String detailsAboutCorrectFormat;
		System.out.println(ex.getMessage());
		if (ex.getMessage().startsWith(MovieConstants.MOVIE_NAME_CANT_BE_EMPTY)) {
			detailsAboutCorrectFormat = request.getDescription(false) + " " + MovieConstants.MOVIE_NAME_CANT_BE_EMPTY;
		} else if (ex.getMessage().startsWith(MovieConstants.PARAMETER_KEY_NOT_APPROPRIAITE)) {

			detailsAboutCorrectFormat = request.getDescription(false)
					+ "   The parameters are movieName,startdate and EndDate";
		} else {
			detailsAboutCorrectFormat = request.getDescription(false)
					+ "   Correct Format Allowed is Valid date and first three characters of month  example 3 Dec";
		}

		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				detailsAboutCorrectFormat);

		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MovieAlreadyPresent.class)
	public final ResponseEntity<Object> handleMovieAreadyPresentException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
	}
}
