package uo.ri.cws.application.ui.foreman;

import uo.ri.util.menu.BaseMenu;
import uo.ri.util.menu.NotYetImplementedAction;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Foreman", null },
			{ "Client reception ", 		ClientReceptionMenu.class }, 
			{ "Client management", 		ClientMenu.class },
			{ "Vehicle management", 	VehicleMenu.class },
			{ "Client history review", 	NotYetImplementedAction.class }, 
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
