package com.capg;

import org.junit.Assert;
import org.junit.Test;

import com.capg.CensusAnalyserException.ExceptionType;
import com.google.gson.Gson;

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
	
	@Test 
	public void givenCensusData_WhenSortBasedOnStatesName_ShouldReturnSortedList() {
		try {
			censusAnalyser.loadCensusData(INDIA_CENSUS_CSVFILE_PATH);
			CSVStateCensus[] census = new Gson().fromJson(censusAnalyser.getSortedCensusData(), CSVStateCensus[].class);
			Assert.assertEquals("Andhra Pradesh West Bengal", census[0].state+" "+census[28].state);
		} 
		catch (CensusAnalyserException e) {
			
		}
	}
	
	@Test 
	public void givenCensusData_WhenSortBasedOnStatePopulation_ShouldReturnSortedList() {
		try {
			censusAnalyser.loadCensusData(INDIA_CENSUS_CSVFILE_PATH);
			CSVStateCensus[] census = new Gson().fromJson(censusAnalyser.getStateCensusSortedByPopulation(), CSVStateCensus[].class);
			Assert.assertEquals("Sikkim Uttar Pradesh", census[0].state+" "+census[28].state);
		} 
		catch (CensusAnalyserException e) {
			
		}
	}
	
	@Test 
	public void givenCensusData_WhenSortBasedOnStatePopulationDensity_ShouldReturnSortedList() {
		try {
			censusAnalyser.loadCensusData(INDIA_CENSUS_CSVFILE_PATH);
			CSVStateCensus[] census = new Gson().fromJson(censusAnalyser.getStateCensusSortedByPopulationDensity(), CSVStateCensus[].class);
			Assert.assertEquals("Arunachal Pradesh Bihar", census[0].state+" "+census[28].state);
		} 
		catch (CensusAnalyserException e) {
			
		}
	}
	
	@Test 
	public void givenCensusData_WhenSortBasedOnStateArea_ShouldReturnSortedList() {
		try {
			censusAnalyser.loadCensusData(INDIA_CENSUS_CSVFILE_PATH);
			CSVStateCensus[] census = new Gson().fromJson(censusAnalyser.getStateCensusSortedByStateArea(), CSVStateCensus[].class);
			System.out.println(census[0].state+" "+census[28].state);
			Assert.assertEquals("Goa Rajasthan", census[0].state+" "+census[28].state);
		} 
		catch (CensusAnalyserException e) {
			
		}
	}
}
