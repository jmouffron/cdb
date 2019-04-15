/**
 * 
 */
package com.excilys.cdb.cli;

import java.util.List;

import com.excilys.cdb.dto.EntityDTO;

/**
 * @author excilys
 *
 *         An encapsulator box to receive List of entities or individual
 *         entities
 *
 */
public class Data {
	protected List<EntityDTO> dataList;
	protected EntityDTO data;

	/**
	 * 
	 */
	public Data(List<EntityDTO> dataList) {
		this.dataList = dataList;
	}

	public Data(EntityDTO data) {
		this.data = data;
	}

	public List<EntityDTO> getDataList() {
		return dataList;
	}

	public EntityDTO getData() {
		return data;
	}

	public void setData(List<EntityDTO> data) {
		this.dataList = data;
	}

	public void setData(EntityDTO data) {
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
