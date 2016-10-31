package librarysystem.mappings;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import librarysystem.controller.LibraryMemberController;
import librarysystem.controller.UINavigator;
import librarysystem.models.Address;
import librarysystem.models.LibraryMember;
import librarysystem.util.Messages;
import librarysystem.util.Constants;
import librarysystem.util.ServiceResponse;

public class Member implements Initializable {
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField street;
	@FXML
	private TextField city;
	@FXML
	private TextField state;
	@FXML
	private TextField zip;
	@FXML
	private TextField phone;

	@FXML
	private TextField memberIdHdn; // Hidden Field
	@FXML
	private TextField actionHdn; // Hidden Field

	@FXML
	protected void addNewMember() {

		LibraryMember libraryMember = new LibraryMember();
		libraryMember.setFirstname(getFirstName());
		libraryMember.setLastName(getLastName());
		libraryMember.setPhone(getPhone());
		Address address = new Address();
		address.setCity(getCity());
		address.setState(getState());
		address.setStreet(getStreet());
		address.setZip(getZip());
		libraryMember.setAddress(address);
		String msg = getActionHdn();

		if (validateForm()) {
			try {
				switch (msg) {
				case Constants.ACTION_CREATE:
					if (Messages
							.showConfirmDialog("Are you sure to add?")) {
						ServiceResponse serviceResponse = new LibraryMemberController()
								.addNewMember(libraryMember);
						Messages
								.showServiceReseponeMessage(serviceResponse);
						back();
					}
					break;
				case Constants.ACTION_UPDATE:
					if (Messages
							.showConfirmDialog("Are you sure to update?")) {
						libraryMember.setMemberId(getMemberIdHdn());
						ServiceResponse serviceResponse = new LibraryMemberController()
								.updateMember(libraryMember);
						Messages
								.showServiceReseponeMessage(serviceResponse);
						back();
					}
					break;
				default:
					// Nothing to do

				}

			} catch (Exception e) {
				e.printStackTrace();
				Messages.showServiceReseponeMessage();
			}
		}

	}

//	private void hideWindow() {
//		firstName.getScene().getWindow().hide();
//	}
//
//	private void showMemberTableView() throws Exception {
//		UINavigator.loadUI(Constants.MEMBER_TABLE);
//	}

	public void setRecordAndShow(LibraryMember librabryMember) throws Exception {
		UINavigator.loadUI(Constants.MEMBER, librabryMember);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Purpose To Update Form Load Initially
		if (LibraryMemberViewMapper.getSelectedMember() != null) {
			LibraryMember libraryMember = LibraryMemberViewMapper
					.getSelectedMember();
			firstName.textProperty().set(libraryMember.getFirstname());
			lastName.textProperty().set(libraryMember.getLastName());
			phone.textProperty().set(libraryMember.getPhone());
			city.textProperty().set(libraryMember.getAddress().getCity());
			state.textProperty().set(libraryMember.getAddress().getState());
			street.textProperty().set(libraryMember.getAddress().getStreet());
			zip.textProperty().set(libraryMember.getAddress().getZip());

			memberIdHdn.textProperty().set(libraryMember.getMemberId());
			actionHdn.textProperty().set(Constants.ACTION_UPDATE);
		} else {
			actionHdn.textProperty().set(Constants.ACTION_CREATE);
		}

	}

	private Boolean validateForm() {
		if (getFirstName().isEmpty() || getLastName().isEmpty()
				|| getPhone().isEmpty() || getCity().isEmpty()
				|| getState().isEmpty() || getStreet().isEmpty()
				|| getZip().isEmpty()) {
			Messages.showExceptionDialog("Please input all field");
			return false;
		}

		try {
			Integer.parseInt(getZip());
		} catch (Exception e) {
			Messages.showExceptionDialog("Zip contains only digit");
			return false;
		}
		return true;

	}

	private String getZip() {
		return zip.textProperty().get();
	}

	private String getStreet() {
		return street.textProperty().get();
	}

	private String getState() {
		return state.textProperty().get();
	}

	private String getCity() {
		return city.textProperty().get();
	}

	private String getPhone() {
		return phone.textProperty().get();
	}

	private String getLastName() {
		return lastName.textProperty().get();
	}

	private String getFirstName() {
		return firstName.textProperty().get();
	}

	private String getMemberIdHdn() {
		return memberIdHdn.textProperty().get();
	}

	private String getActionHdn() {
		return actionHdn.textProperty().get();
	}
	
	public void back(){
		String actionParent = getActionHdn();
		if (actionParent.equals(Constants.ACTION_CREATE))
			UINavigator.loadUI(Constants.DASHBOARD);
		else if(actionParent.equals(Constants.ACTION_UPDATE))
			UINavigator.loadUI(Constants.MEMBER_TABLE);
		
	}

}
