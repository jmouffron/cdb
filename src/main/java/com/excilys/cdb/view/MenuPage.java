package com.excilys.cdb.view;

import java.util.Scanner;

import com.excilys.cdb.controller.MenuChoiceEnum;
import com.excilys.cdb.model.Entity;
import org.slf4j.LoggerFactory;

public class MenuPage extends Page<Entity> {

	public MenuPage() {
		this.logger = LoggerFactory.getLogger(MenuPage.class);
	}

	@Override
	public MenuChoiceEnum show() {
		String value;
		Scanner input = new Scanner(System.in);

		System.out.println("Choisissez une option parmi les choix possibles suivants depuis la BDD:");
		System.out.println("1. Afficher une liste de tous les manufactureurs.");
		System.out.println("2. Afficher une liste de tous les ordinateurs.");
		System.out.println("3. Afficher les détails d'un ordinateur.");
		System.out.println("4. Créer un ordinateur.");
		System.out.println("5. Mettre à jour un ordinateur.");
		System.out.println("6. Supprimer un ordinateur.");
		System.out.println("7. Sortir du programme.");
		System.out.println("Rentrer le numéro de l'option voulue.");

		value = input.next();

		return MenuChoiceEnum.stringToEnum(value);
	}

}
