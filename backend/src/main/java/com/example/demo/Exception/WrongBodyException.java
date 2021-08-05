package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WrongBodyException extends RuntimeException {

	public WrongBodyException(String message) {
		super(message);
	}

}