package com.excilys.cdb.console.cli;

import org.slf4j.LoggerFactory;

public class MenuPage extends Page {

	public MenuPage() {
		this.logger = LoggerFactory.getLogger(MenuPage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		String value;

		logger.info("Choisissez une option parmi les choix possibles suivants depuis la BDD:");
		logger.info("1. Afficher une liste de tous les manufactureurs.");
		logger.info("2. Afficher une liste de tous les ordinateurs.");
		logger.info("3. Afficher les détails d'un ordinateur.");
		logger.info("4. Créer un ordinateur.");
		logger.info("5. Mettre à jour un ordinateur.");
		logger.info("6. Supprimer un ordinateur.");
		logger.info("7. Supprimer un manufactureur.");
		logger.info("8. Sortir du programme.");
		logger.info("Rentrer le numéro de l'option voulue.");

		value = ControllerUtils.scan.next();

		return MenuChoiceEnum.stringToEnum(value);
	}

}
