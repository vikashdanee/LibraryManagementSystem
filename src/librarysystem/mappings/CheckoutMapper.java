package librarysystem.mappings;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import librarysystem.controller.CheckoutController;
import librarysystem.controller.LibraryMemberController;
import librarysystem.controller.UINavigator;
import librarysystem.models.CheckoutRecord;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.models.Copy;
import librarysystem.models.LibraryMember;
import librarysystem.models.Publication;
import librarysystem.util.Messages;
import librarysystem.util.Constants;
import librarysystem.util.Functors;
import librarysystem.util.ServiceResponse;

public class CheckoutMapper implements Initializable {

	@FXML
	private PublicationLookUp publicationViewController;
	@FXML
	private TextField firstName;
	@FXML
	private TextField memberId;
	@FXML
	private TextField lastName;
	@FXML
	private TextField checkoutDate;
	@FXML
	private TextField dueDate;
	@FXML
	private Button checkMemberBtn;
	@FXML
	private Button checkoutBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		checkoutBtn.setDisable(true);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		setCheckoutDate(sdf.format(date));

		// Listener
		publicationViewController.getTableView().getSelectionModel()
				.selectedItemProperty()
				.addListener((obs, oldSelection, newSelection) -> {
					if (newSelection != null) {
						rowListenerValidate();
					}
				});

	}

	private void rowListenerValidate() {
		ObservableList<Publication> list = publicationViewController
				.getTableView().getSelectionModel().getSelectedItems();
		Publication pub = list.get(0);

		int available = Functors.AVAILABLE_COPIES_COUNTER.apply(pub);

		if (available > 0 && !getMemberId().isEmpty()
				&& !getFirstName().isEmpty() && !getLastName().isEmpty()) {
			setDueDate(addDayToCurrentDate(pub.getMaxCheckoutLength()));
			checkoutBtn.setDisable(false);
		} else {
			checkoutBtn.setDisable(true);
		}
	}

	@FXML
	protected void checkout() {
		ObservableList<Publication> list = publicationViewController
				.getTableView().getSelectionModel().getSelectedItems();
		if (list.size() == 0) {
			Messages.showInformationDialog("Select publication first");
		} else {
			if (Messages.showConfirmDialog("Are you sure to checkout?")) {
				// Get Data
				String member = getMemberId();
				String checkoutDate = getCheckoutDate();
				String dueDate = getDueDate();
				Publication publication = list.get(0);

				CheckoutRecord checkoutRecord;
				LibraryMember libraryMember;
				try {
					libraryMember = (LibraryMember) (new LibraryMemberController())
							.getMember(member).getData();
					Copy copy = Functors.AVAILABLE_COPIES_FINDER.apply(
							publication).get(0);
					//copy.setCheckedout(true);
					checkoutRecord = (new CheckoutController())
							.getCheckoutRecord(libraryMember);
					List<CheckoutRecordEntry> checkoutEntries = checkoutRecord
							.getCheckoutEntries();
					checkoutEntries.add(new CheckoutRecordEntry(checkoutDate,
							dueDate, copy, checkoutRecord));
					ServiceResponse serviceResponse = new CheckoutController()
							.save(checkoutRecord);

					Messages
							.showServiceReseponeMessage(serviceResponse);
					if (serviceResponse.getSuccess())
						back();
				} catch (Exception e) {
					e.printStackTrace();
					Messages.showServiceReseponeMessage();
				}
			}
		}

	}

	@FXML
	protected void cancelWindow() {
		back();
	}

	@FXML
	protected void checkMember() {
		if (getMemberId().isEmpty()) {
			Messages.showExceptionDialog("Please input member id");
		} else {
			try {
				ServiceResponse serviceResponse = new LibraryMemberController()
						.getMember(getMemberId());
				if (serviceResponse.getSuccess()) {
					LibraryMember libraryMember = (LibraryMember) serviceResponse
							.getData();
					setFirstName(libraryMember.getFirstname());
					setLastName(libraryMember.getLastName());
					memberId.setDisable(true);
					checkMemberBtn.setDisable(true);

				} else {
					Messages
							.showServiceReseponeMessage(serviceResponse);
					memberId.requestFocus();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messages.showServiceReseponeMessage();
			}

		}
	}

	private String getFirstName() {
		return firstName.textProperty().get();
	}

	private void setFirstName(String firstName) {
		this.firstName.textProperty().set(firstName);
	}

	private void setLastName(String lastName) {
		this.lastName.textProperty().set(lastName);
	}

	private String getMemberId() {
		return memberId.textProperty().get();
	}

	private String getLastName() {
		return lastName.textProperty().get();
	}

	private String getCheckoutDate() {
		return checkoutDate.textProperty().get();
	}

	private void setCheckoutDate(String date) {
		this.checkoutDate.textProperty().set(date);
	}

	private String getDueDate() {
		return dueDate.textProperty().get();
	}

	private void setDueDate(String date) {
		this.dueDate.textProperty().set(date);
	}

	private String addDayToCurrentDate(int day) {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);

		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		String formatted = format1.format(c.getTime());
		return formatted;
	}

	public void back() {
		UINavigator.loadUI(Constants.DASHBOARD);
	}

}
