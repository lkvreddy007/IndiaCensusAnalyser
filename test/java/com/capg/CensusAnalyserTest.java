package com.capg;

import org.junit.Assert;
import org.junit.Test;

import com.capg.CensusAnalyserException.ExceptionType;

public class CensusAnalyserTest {
	StateCensusAnalyser censusAnalyser = new StateCensusAnalyser();
	private static final String INDIA_CENSUS_CSVFILE_PATH="C:\\Users\\lkvre\\eclipse-workspace\\IndianCensusAnalyser\\src\\test\\resources\\IndiaStateCensusData.csv";
	private static final String WRONG_CSV_FILE_PATH="./src/main/resources/IndiaStateCensusData.csv";
	private static final String WRONG_DELIMITER="C:\\Users\\lkvre\\eclipse-workspace\\IndianCensusAnalyser\\src\\test\\resources\\WrongDelimiter.csv";
	
	@Test
	public void givenCensusData_WithWrongFileFormat_ShouldThrowException() {
		try {
			censusAnalyser.loadCensusData("CSVFile.txt");
		}
		catch(CensusAnalyserException e) {
			System.out.println(e);
			Assert.assertEquals(ExceptionType.INVALID_FILE_FORMAT, e.exceptionType);
		}
	}
	
	@Test
	public void givenCensusData_WithWrongFilePath_ShouldThrowException() {
		try {
			censusAnalyser.loadCensusData(WRONG_CSV_FILE_PATH);
		}
		catch(CensusAnalyserException e) {
			System.out.println(e);
			Assert.assertEquals(ExceptionType.CENSUS_FILE_PROBLEM, e.exceptionType);
		}
	}
	
	@Test
	public void givenCensusData_WhenCorrect_ShouldReturnNumberOfRecords() {
		int numOfEntries;
		try {
			numOfEntries = censusAnalyser.loadCensusData(INDIA_CENSUS_CSVFILE_PATH);
			Assert.assertEquals(29,numOfEntries);
		} 
		catch (CensusAnalyserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void givenCensusData_WithWrongDelimiter_ShouldThrowException() {
		try {
			censusAnalyser.loadCensusData(WRONG_DELIMITER);
		}
		catch(CensusAnalyserException e) {
			System.out.println(e);
			Assert.assertEquals(ExceptionType.ERROR_IN_FILE, e.exceptionType);
		}
	}
}
