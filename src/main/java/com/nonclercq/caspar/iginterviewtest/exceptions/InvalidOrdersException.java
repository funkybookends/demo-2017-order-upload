package com.nonclercq.caspar.iginterviewtest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Orders were not valid")
public class InvalidOrdersException extends Exception
{
	public InvalidOrdersException(final Throwable cause)
	{
		super(cause);
	}
}
