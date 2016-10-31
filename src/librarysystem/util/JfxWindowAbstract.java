package librarysystem.util;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import librarysystem.controller.MainController;
import librarysystem.controller.UINavigator;

public abstract class JfxWindowAbstract {

	public void openDashboardWindow() {
		try {
			Stage stage = new Stage();
			stage.setTitle("Library Management System");
			stage.setResizable(false);
			stage.setScene(createScene(loadMainPane()));
			Platform.setImplicitExit(false);
			stage.show();

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					Platform.exit();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void openNewMember() {
		try {
			UINavigator.loadUI(Constants.MEMBER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openMembersTable() {
		try {
			UINavigator.loadUI(Constants.MEMBER_TABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openNewBookWindow() {
		try {
			UINavigator.loadUI(Constants.BOOK);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void openNewPeriodicalWindow() { try {
	 * UINavigator.loadUI(Constants.PERIODICAL); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	public void openCheckout() {
		try {
			UINavigator.loadUI(Constants.CHECKOUT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openAddCopy() {
		try {
			UINavigator.loadUI(Constants.ADD_COPY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void openViewOverdueCopies() {
		try {
			UINavigator.loadUI(Constants.OVERDUE_COPIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Pane loadMainPane() throws IOException {
		FXMLLoader loader = new FXMLLoader();

		Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(Constants.MAIN));

		MainController mainController = loader.getController();

		UINavigator.setMainController(mainController);
		UINavigator.loadUI(Constants.DASHBOARD);

		return mainPane;
	}

	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);
		return scene;
	}
}
