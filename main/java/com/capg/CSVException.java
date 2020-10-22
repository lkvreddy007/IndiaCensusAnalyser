package com.capg;

import com.capg.CensusAnalyserException.ExceptionType;

public class CSVException extends Exception {
	enum ExceptionType{
		UNABLE_TO_PARSE;
	}
	ExceptionType type;
	public CSVException(String message, ExceptionType exceptiontype) {
		super(message);
		this.type=exceptiontype;
	}
}
