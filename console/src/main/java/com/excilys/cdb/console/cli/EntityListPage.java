package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.exception.PageException;
import com.excilys.cdb.binding.exception.ServiceException;

public class EntityListPage extends Page {
	private long dataPerPage;
	private long totalPageNumber;

	private long startIndex;
	private long pageIndex;

	public EntityListPage(Data payload, long totalPages, long dataPerPage) throws ServiceException {
		this.data = payload;
		this.pageIndex = 0;
		this.logger = LoggerFactory.getLogger(EntityListPage.class);
		this.dataPerPage = dataPerPage;
		this.totalPageNumber = totalPages;
	}

	private void paginate() throws PageException {
		pageIndex = 0;
		String input;

		while (pageIndex >= 0 && pageIndex < totalPageNumber) {
			data.list(startIndex, dataPerPage);

			System.out.println("P " + (pageIndex + 1) + "/" + totalPageNumber + " N ");

			input = ControllerUtils.getStringInput("P pour page précédente, N pour la suivante, E pour le menu:", ControllerUtils.isPorAorE );
			
			logger.error("Input value:" + input);
			
			if (input.equals("P")) {
				pageIndex--;
				startIndex -= dataPerPage;
			} else if (input.equals("N")) {
				pageIndex++;
				startIndex += dataPerPage;
			} else {
				throw new PageException("Problème de page!");
			}
		}
	}

	@Override
	public MenuChoiceEnum show() {
		System.out.println("==============================");
		System.out.println(" |          Liste           | ");
		System.out.println("==============================");
		try {
			paginate();
		} catch (PageException e) {
			logger.error(e.getMessage());
		}
		System.out.println("==============================");
		return MenuChoiceEnum.DEFAULT;
	}

}
