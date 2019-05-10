package com.excilys.cdb.console.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.EntityDTO;

/**
 * @author excilys
 *
 *         An encapsulator box to receive List of entities or individual
 *         entities
 *
 */
public class Data {
	private static final Logger logger = LoggerFactory.getLogger(Data.class);
	protected List<EntityDTO> dataList;
	protected EntityDTO content;
	protected long id;

	/**
	 * 
	 */
	public Data(List<EntityDTO> dataList) {
		this.dataList = dataList;
	}

	public Data(EntityDTO data) {
		this.content = data;
	}

	public Data(long computerId) {
		this.id = computerId;
	}

	public List<EntityDTO> getDataList() {
		return dataList;
	}

	public EntityDTO getData() {
		return content;
	}

	public void setData(List<EntityDTO> data) {
		this.dataList = data;
	}

	public void setData(EntityDTO data) {
		this.content = data;
	}

	/**
	 * Displays a single Entity
	 */
	public void show() {
		logger.info("{}", this.content);
	}
	
	public void showId() {
		logger.info("{}", this.id);
	}

	/**
	 * Displays a definite number of items from the list of entities in the Data
	 * object starting from a start index
	 * 
	 * @param startIndex
	 * @param numberToShow
	 */
	public void list(long startIndex, long numberToShow) {
		this.dataList.stream().skip(startIndex).limit(numberToShow)
				.forEach(entity -> logger.info("\t {}", entity));
	}
}
