/**
 * 
 */
package com.excilys.cdb.view;

import java.util.List;

import com.excilys.cdb.model.Entity;

/**
 * @author excilys
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

	public void show() {
		System.out.println(this.data);
	}

	public void list(long startIndex, long numberToShow) {
		this.dataList.stream().limit(numberToShow).forEach(entity -> System.out.println("\t" + entity));
	}
}
