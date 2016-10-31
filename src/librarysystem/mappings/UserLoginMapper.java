package librarysystem.mappings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import librarysystem.controller.UserController;
import librarysystem.models.User;
import librarysystem.util.Messages;
import librarysystem.util.JfxWindowAbstract;
import librarysystem.util.ServiceResponse;

public class UserLoginMapper extends JfxWindowAbstract implements Initializable {
	@FXML
	private TextField userName;
	@FXML
	private PasswordField userPassword;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	protected void doCancel() throws Exception {
		if (Messages.showConfirmDialog("Are you want to exit?"))
			Platform.exit();
	}

	@FXML
	protected void doLogin() throws Exception {
		try {
			if (validateForm()) {
				User user = new User(getUserName(), getUserPassword());
				ServiceResponse serviceResponse = new UserController()
						.checkUser(user);
				if (serviceResponse.getSuccess()) {
					userPassword.getScene().getWindow().hide();
					openDashboardWindow();
					// doCancel();
				} else {
					Messages
							.showWarningDialog("Login couldn't be completed. Please be sure to enter correct username and password.");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Messages.showServiceReseponeMessage();
		}

	}

	private String getUserName() {
		return userName.textProperty().get();
	}

	private String getUserPassword() {
		return userPassword.textProperty().get();
	}

	private Boolean validateForm() {
		if (getUserName().isEmpty() || getUserPassword().isEmpty()) {
			Messages.showExceptionDialog("Please input all field");
			return false;
		}
		return true;
	}

}
