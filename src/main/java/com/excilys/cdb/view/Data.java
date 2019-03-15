/**
 * 
 */
package com.excilys.cdb.view;

import java.util.List;

import com.excilys.cdb.model.Entity;

/**
 * @author excilys
 *
 *	An encapsulator box to receive List of entities or individual entities
 *
 */
public class Data<T extends Entity> {
	private List<T> dataList;
	private T data;

	/**
	 * 
	 */
	public Data(List<T> dataList) {
		this.dataList = dataList;
	}

	public Data(T data) {
		this.data = data;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public T getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.dataList = data;
	}

	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Displays a single Entity
	 */
	public void show() {
		System.out.println(this.data);
	}

	/**
	 * 
	 * Displays a definite number of items 
	 * from the list of entities
	 * in the Data object strating from a start index
	 *  
	 * @param startIndex
	 * @param numberToShow
	 */
	public void list(long startIndex, long numberToShow) {
		this.dataList.stream().skip(startIndex).limit(numberToShow).forEach(entity -> System.out.println("\t" + entity));
	}
}
