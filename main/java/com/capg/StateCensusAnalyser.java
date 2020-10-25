package com.capg;

import java.io.IOException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

import com.capg.CensusAnalyserException.ExceptionType;
import com.google.gson.Gson;

public class StateCensusAnalyser<E> {
	List<CSVStateCensus> csvCensusFileList= null;
	List<CSVStateCodes> csvStateCodeFileList= null;
	public int loadCensusData(String csvFilePath) throws CensusAnalyserException {
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
			csvCensusFileList = csvBuilder.getCSVList(reader,CSVStateCensus.class);
			return this.csvCensusFileList.size();
		}
		catch (IOException e) {
			throw new CensusAnalyserException("Census File Problem", ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (NullPointerException e) {
			throw new CensusAnalyserException("Null Data", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		catch(RuntimeException e) {
			throw new CensusAnalyserException("Invalid Delimiter", ExceptionType.ERROR_IN_FILE);
		}
		catch(CSVException e) {
			throw new CensusAnalyserException("Unable to parse", ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException {
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
			csvStateCodeFileList=csvBuilder.getCSVList(reader, CSVStateCodes.class);
			return this.csvStateCodeFileList.size();
		}
		catch (IOException e) {
			throw new CensusAnalyserException("Code File Problem", ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (NullPointerException e) {
			throw new CensusAnalyserException("Null Data", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		catch(RuntimeException e) {
			throw new CensusAnalyserException("Invalid Delimiter", ExceptionType.ERROR_IN_FILE);
		}
		catch(CSVException e) {
			throw new CensusAnalyserException("Unable to parse", ExceptionType.UNABLE_TO_PARSE);
		}
	}
	
	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable=()->iterator;
		int numOfEntries=(int)StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
	
	private void sortCensus(Comparator<CSVStateCensus> comparator){
		for(int j=0;j<csvCensusFileList.size();j++) {
			for(int i=0;i<csvCensusFileList.size()-1;i++) {
				CSVStateCensus state1 = csvCensusFileList.get(i);
				CSVStateCensus state2 = csvCensusFileList.get(i+1);
				if(comparator.compare(state1, state2)>0) {
					csvCensusFileList.set(i, state2);
					csvCensusFileList.set(i+1, state1);
				}
			}
		}
	}
	
	public String getSortedCensusData() throws CensusAnalyserException{
		
		if(csvCensusFileList.size()==0 || csvCensusFileList==null) {
			throw new CensusAnalyserException("Null Values", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		Comparator<CSVStateCensus> comparator = Comparator.comparing(census->census.state);
		sortCensus(comparator);
		String sortedCensusData= new Gson().toJson(csvCensusFileList);
		return sortedCensusData;
	}
	
	public void sortStateCodes(Comparator<CSVStateCodes> comparator) {
		for(int j=0;j<csvCensusFileList.size();j++) {
			for(int i=0;i<csvCensusFileList.size()-1;i++) {
				CSVStateCodes state1 = csvStateCodeFileList.get(i);
				CSVStateCodes state2 = csvStateCodeFileList.get(i+1);
				if(comparator.compare(state1, state2)>0) {
					csvStateCodeFileList.set(i, state2);
					csvStateCodeFileList.set(i+1, state1);
				}
			}
		}
	}
	
	public String getSortedStateCodeData() throws CensusAnalyserException{
		if(csvStateCodeFileList.size()==0 || csvStateCodeFileList==null) {
			throw new CensusAnalyserException("Null Values",ExceptionType.NULL_VALUES_ENCOUNTERED );
		}
		Comparator<CSVStateCodes> comparator = Comparator.comparing(census->census.stateCode);
		sortStateCodes(comparator);
		String sortedCensusData= new Gson().toJson(csvStateCodeFileList);
		return sortedCensusData;
	}
	
	public String getStateCensusSortedByPopulation() throws CensusAnalyserException{
		if(csvCensusFileList.size()==0 || csvCensusFileList==null) {
			throw new CensusAnalyserException("Null Values", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		Comparator<CSVStateCensus> comparator = Comparator.comparing(census->census.population);
		sortCensus(comparator);
		String sortedCensusData= new Gson().toJson(csvCensusFileList);
		return sortedCensusData;
	}
}
