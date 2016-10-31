package librarysystem.controller;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

public class UINavigator {

	private static MainController mainController;

	public static void setMainController(MainController mainController) {
		UINavigator.mainController = mainController;
	}

	public static void loadUI(String fxml, Object userData) {
		try {
			mainController.setUI(FXMLLoader.load(UINavigator.class.getResource(fxml)), userData);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadUI(String fxml) {
		loadUI(fxml, null);
	}
}