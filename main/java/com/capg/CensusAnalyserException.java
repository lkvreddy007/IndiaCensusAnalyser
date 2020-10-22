package com.capg;

public class CensusAnalyserException extends Exception {
	enum ExceptionType{
		INVALID_FILE_FORMAT,CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,NULL_VALUES_ENCOUNTERED,ERROR_IN_FILE;
	}
	
	ExceptionType exceptionType;
	
	public CensusAnalyserException(String message,ExceptionType exceptionType) {
		super(message);
		this.exceptionType=exceptionType;
	}
}
