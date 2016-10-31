package librarysystem.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class Messages {
	private static final String INFORMATION_TITLE = "Information";
	private static final String WARNING_TITLE = "Warning";
	private static final String EXCEPTION_TITLE = "Error";
	private static final String EXCEPTION_CONFIRMATION = "Confirmation";

	public static void showInformationDialog(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(INFORMATION_TITLE);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showWarningDialog(String message) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(WARNING_TITLE);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void showExceptionDialog(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(EXCEPTION_TITLE);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static Boolean showConfirmDialog(String confirmMsg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(EXCEPTION_CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText(confirmMsg);

		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK ? true : false;

	}

	public static void showServiceReseponeMessage(
			ServiceResponse serviceResponse) {
		if (serviceResponse.getSuccess())
			showInformationDialog(serviceResponse.getMessage());
		else
			showExceptionDialog(serviceResponse.getMessage());
	}

	public static void showServiceReseponeMessage() {
		showExceptionDialog(ServiceResponse.getRuntimeException());
	}

}
