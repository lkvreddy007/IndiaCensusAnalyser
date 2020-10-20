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
			CsvToBeanBuilder<CSVStateCensus> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
			csvToBeanBuilder.withType(CSVStateCensus.class);
			csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
			CsvToBean<CSVStateCensus> csvToBean = csvToBeanBuilder.build();
			Iterator<CSVStateCensus> csvIterable = csvToBean.iterator();
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
}
