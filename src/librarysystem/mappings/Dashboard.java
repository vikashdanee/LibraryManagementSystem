package librarysystem.mappings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import librarysystem.controller.UserController;
import librarysystem.enums.Role;
import librarysystem.main.Main;
import librarysystem.util.Messages;
import librarysystem.util.JfxWindowAbstract;

public class Dashboard extends JfxWindowAbstract implements Initializable {
	@FXML
	private Button memberView;
	@FXML
	private Button checkout;
	@FXML
	private Button openBook;
	/*@FXML
	private Button openPeriodical;*/
	@FXML
	private Button addMember;
	@FXML
	private Button addCopy;
	@FXML
	private Button overdueCopy;
	@FXML
	private Button logout;
	@FXML private Label welcomeLBL;

	@FXML
	protected void openMemberView(ActionEvent event) throws IOException {
		openNewMember();
	}

	@FXML
	protected void openMembersTable(ActionEvent event) throws IOException {
		openMembersTable();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (UserController.role.toString().equals(Role.ADMIN.toString())) {
			checkout.setDisable(true);
			welcomeLBL.setText("Welcome, Access Level ["+ Role.ADMIN.toString() +"]");
		} else if (UserController.role.toString().equals(
				Role.LIBRARIAN.toString())) {
			addCopy.setDisable(true);
			openBook.setDisable(true);
			/*openPeriodical.setDisable(true);*/
			addMember.setDisable(true);
			welcomeLBL.setText("Welcome, Access Level ["+ Role.LIBRARIAN.toString() +"]");
			
		} else {
			welcomeLBL.setText("Welcome, Access Level ["+ Role.BOTH.toString() +"]");
		}

	}

	@FXML
	protected void openCheckout(ActionEvent event) {
		openCheckout();
	}

	@FXML
	protected void openBook(ActionEvent event) {
		openNewBookWindow();
	}

	/*@FXML
	protected void openPeriodical(ActionEvent event) {
		openNewPeriodicalWindow();
	}*/

	@FXML
	protected void addCopy(ActionEvent event) {
		openAddCopy();
	}

	@FXML
	protected void viewOverdueCopies(ActionEvent event) {
		openViewOverdueCopies();
	}

	@FXML
	protected void logout(ActionEvent event) {
		if (Messages.showConfirmDialog("Are you sure to logout?")) {
			try {
				logout.getScene().getWindow().hide();
				Main main = new Main();
				Stage stage = new Stage();
				main.start(stage);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
