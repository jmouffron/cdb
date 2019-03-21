/**
 * 
 */
package com.excilys.cdb.view;

import java.util.List;

import com.excilys.cdb.model.Entity;

/**
 * @author excilys
 *
 *         An encapsulator box to receive List of entities or individual
 *         entities
 *
 */
public class Data<Entity> {
	private List<Entity> dataList;
	private Entity data;

	/**
	 * 
	 */
	public Data(List<Entity> dataList) {
		this.dataList = dataList;
	}

	public Data(Entity data) {
		this.data = data;
	}

	public List<Entity> getDataList() {
		return dataList;
	}

	public Entity getData() {
		return data;
	}

	public void setData(List<Entity> data) {
		this.dataList = data;
	}

	public void setData(Entity data) {
		this.data = data;
	}

	/**
	 * Displays a single Entity
	 */
	public void show() {
		System.out.println(this.data);
	}

	/**
	 * Displays a definite number of items from the list of entities in the Data
	 * object strating from a start index
	 * 
	 * @param startIndex
	 * @param numberToShow
	 */
	public void list(long startIndex, long numberToShow) {
		this.dataList.stream().skip(startIndex).limit(numberToShow)
				.forEach(entity -> System.out.println("\t" + entity));
	}
}
