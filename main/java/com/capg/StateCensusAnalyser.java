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
	List<CSVStateCensus> csvFileList= null;
	public int loadCensusData(String csvFilePath) throws CensusAnalyserException {
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
			csvFileList = csvBuilder.getCSVList(reader,CSVStateCensus.class);
			return this.csvFileList.size();
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
			csvFileList=csvBuilder.getCSVList(reader, CSVStateCodes.class);
			return this.csvFileList.size();
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
	
	private void sort(Comparator<CSVStateCensus> comparator){
		for(int j=0;j<csvFileList.size();j++) {
			for(int i=0;i<csvFileList.size()-1;i++) {
				CSVStateCensus state1 = csvFileList.get(i);
				CSVStateCensus state2 = csvFileList.get(i+1);
				if(comparator.compare(state1, state2)>0) {
					csvFileList.set(i, state2);
					csvFileList.set(i+1, state1);
				}
			}
		}
	}
	
	public String getSortedCensusData() throws CensusAnalyserException{
		
		if(csvFileList.size()==0 || csvFileList==null) {
			throw new CensusAnalyserException("Null Values", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		Comparator<CSVStateCensus> comparator = Comparator.comparing(census->census.state);
		sort(comparator);
		String sortedCensusData= new Gson().toJson(csvFileList);
		return sortedCensusData;
	}
}
