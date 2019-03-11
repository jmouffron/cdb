package com.excilys.cdb.view;

import java.util.Scanner;

import org.slf4j.LoggerFactory;

public class MenuPage extends Page {
	
	public MenuPage() {
		this.logger = LoggerFactory.getLogger(MenuPage.class);
		this.input = new Scanner("System.in");
	}
	
	@Override
	public String show() {
		String value;
			
		logger.info("Choisissez une option parmi les choix possibles suivants depuis la BDD:");
		logger.info("1. Afficher une liste de tous les manufactureurs.");
		logger.info("2. Afficher une liste de tous les ordinateurs.");
		logger.info("3. Afficher les détails d'un ordinateur.");
		logger.info("4. Créer un ordinateur.");
		logger.info("5. Mettre à jour un ordinateur.");
		logger.info("6. Supprimer un ordinateur.");
		logger.info("Rentrer le numéro de l'option voulue, ou exit pour sortir du programme");
		
		value = input.next();
		
		return value;
	}

}
