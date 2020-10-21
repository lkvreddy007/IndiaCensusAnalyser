package com.capg;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import com.capg.CensusAnalyserException.ExceptionType;

public class StateCensusAnalyser {
	public int loadCensusData(String csvFilePath) throws CensusAnalyserException{
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
			Iterator<CSVStateCensus> csvIterable = csvBuilder.getCSVFileIterator(reader, CSVStateCensus.class);
			return this.getCount(csvIterable);
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
	}
	
	public int loadStateCodeData(String csvFilePath) throws CensusAnalyserException{
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			ICSVBuilder csvBuilder=CSVBuilderFactory.createCSVBuilder();
			Iterator<CSVStateCodes> csvIterable = csvBuilder.getCSVFileIterator(reader, CSVStateCodes.class);
			return this.getCount(csvIterable);
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
	}
	
	private <E> int getCount(Iterator<E> iterator) {
		Iterable<E> csvIterable=()->iterator;
		int numOfEntries=(int)StreamSupport.stream(csvIterable.spliterator(), false).count();
		return numOfEntries;
	}
}
