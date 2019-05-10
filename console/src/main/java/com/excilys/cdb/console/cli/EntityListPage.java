package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.exception.PageException;

public class EntityListPage extends Page {
	
	private long dataPerPage;
	private long totalPageNumber;

	private long startIndex;
	private long pageIndex;

	public EntityListPage(Data payload, long totalPages, long dataPerPage) {
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

			logger.info("P {} / {} N ", (pageIndex + 1), totalPageNumber  );

			input = ControllerUtils.getStringInput("P pour page précédente, N pour la suivante, E pour le menu:", ControllerUtils.isPorAorE );
			
			logger.error("Input value: {}", input);
			
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
		logger.info(DELIMITER);
		logger.info(" |          Liste           | ");
		logger.info(DELIMITER);
		try {
			paginate();
		} catch (PageException e) {
			logger.error(e.getMessage());
		}
		logger.info(DELIMITER);
		return MenuChoiceEnum.DEFAULT;
	}

}
