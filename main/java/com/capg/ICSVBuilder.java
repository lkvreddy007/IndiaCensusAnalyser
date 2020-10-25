package com.capg;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {
	public  Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVException; 
	public  List<E> getCSVList(Reader reader,Class csvClass) throws CSVException;
}
