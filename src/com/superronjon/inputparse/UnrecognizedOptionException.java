package com.superronjon.inputparse;

public class UnrecognizedOptionException extends Exception
{
	public UnrecognizedOptionException() {}

	public UnrecognizedOptionException(String message) {
		super(message);
	}
}
