package com.capg;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.capg.CensusAnalyserException.ExceptionType;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class StateCensusAnalyser {
	public int loadCensusData(String csvFilePath) throws CensusAnalyserException{
		if(!csvFilePath.contains(".csv")) {
			throw new CensusAnalyserException("Invalid File Type(.csv required)", ExceptionType.INVALID_FILE_FORMAT);
		}
		try(Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
			Iterator<CSVStateCensus> csvIterable = this.getCSVFileIterator(reader, CSVStateCensus.class);
			List<CSVStateCensus> statesInfo = new ArrayList<>(); 
			while(csvIterable.hasNext()) {
				statesInfo.add(csvIterable.next());
			}
			int numOfEntries=statesInfo.size();
			reader.close();
			return numOfEntries;
		}
		catch (IOException e) {
			throw new CensusAnalyserException("Census File Problem", ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (NullPointerException e) {
			throw new CensusAnalyserException("Null Data", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		catch(IllegalStateException e) {
			throw new CensusAnalyserException("Unable to Parse", ExceptionType.UNABLE_TO_PARSE);
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
			Iterator<CSVStateCodes> csvIterable = this.getCSVFileIterator(reader, CSVStateCodes.class);
			List<CSVStateCodes> statesInfo = new ArrayList<>(); 
			while(csvIterable.hasNext()) {
				statesInfo.add(csvIterable.next());
			}
			int numOfEntries=statesInfo.size();
			reader.close();
			return numOfEntries;
		}
		catch (IOException e) {
			throw new CensusAnalyserException("Code File Problem", ExceptionType.CENSUS_FILE_PROBLEM);
		}
		catch (NullPointerException e) {
			throw new CensusAnalyserException("Null Data", ExceptionType.NULL_VALUES_ENCOUNTERED);
		}
		catch(IllegalStateException e) {
			throw new CensusAnalyserException("Unable to Parse", ExceptionType.UNABLE_TO_PARSE);
		}
		catch(RuntimeException e) {
			throw new CensusAnalyserException("Invalid Delimiter", ExceptionType.ERROR_IN_FILE);
		}
	}
	
	private <E> Iterator<E> getCSVFileIterator(Reader reader,Class<E> csvClass) throws CensusAnalyserException{
		CsvToBeanBuilder<E> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
		csvToBeanBuilder.withType(csvClass);
		csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
		CsvToBean<E> csvToBean = csvToBeanBuilder.build();
		return csvToBean.iterator();
	}
}
