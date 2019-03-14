package com.excilys.cdb.controller;

import java.util.Arrays;
import java.util.Optional;

public enum MenuChoiceEnum {

	FETCH_ALL_COMPANY("1"), FETCH_ALL_COMPUTER("2"), FETCH_COMPUTER("3"), CREATE_COMPUTER("4"), UPDATE_COMPUTER("5"),
	DELETE_COMPUTER("6"), EXIT_PROGRAM("7"), DEFAULT("8");

	private String choice;

	MenuChoiceEnum(String choiceNumber) {
		this.choice = choiceNumber;
	}

	private String getChoice() {
		return this.choice;
	}

	public static MenuChoiceEnum stringToEnum(String input) {
		Optional<MenuChoiceEnum> match = Arrays.stream(MenuChoiceEnum.values()).filter(e -> e.getChoice().equals(input))
				.findAny();
		return match.orElse(DEFAULT);
	}
}
