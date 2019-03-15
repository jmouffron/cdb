package com.excilys.cdb.view;

import com.excilys.cdb.exception.ServiceException;
import com.excilys.cdb.model.Entity;
import org.slf4j.LoggerFactory;
import com.excilys.cdb.controller.ControllerUtils;
import com.excilys.cdb.controller.MenuChoiceEnum;

public class EntityListPage extends Page<Entity> {
	private long dataPerPage;
	private long totalPageNumber;

	private long startIndex;
	private long pageIndex;

	public EntityListPage(Data<Entity> payload, long totalPages, long dataPerPage) throws ServiceException {
		this.data = payload;
		this.pageIndex = 0;
		this.logger = LoggerFactory.getLogger(EntityListPage.class);
		this.dataPerPage = dataPerPage;
		this.totalPageNumber = totalPages;
	}

	private void paginate() {
		pageIndex = 0;
		String input;

		while (pageIndex >= 0 && pageIndex < totalPageNumber) {
			data.list(startIndex, dataPerPage);

			System.out.println("P " + (pageIndex + 1) + "/" + totalPageNumber + " N ");

			input = ControllerUtils.getStringInput("P pour page précédente, N pour la suivante, E pour le menu:",
					valInputted -> valInputted != "P" || valInputted != "N" || valInputted != "E");

			if (input.equals("P")) {
				pageIndex--;
				startIndex -= dataPerPage;
			} else if (input.equals("N")) {
				pageIndex++;
				startIndex += dataPerPage;
			} else {
				break;
			}
		}
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.println(" |          Liste           | ");
		System.out.println("==============================");
		paginate();
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}

}
