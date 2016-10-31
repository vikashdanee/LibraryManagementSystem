package librarysystem.mappings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import librarysystem.controller.CheckoutController;
import librarysystem.controller.UINavigator;
import librarysystem.models.CheckedoutCopies;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.models.Copy;
import librarysystem.models.Publication;
import librarysystem.util.Messages;
import librarysystem.util.Constants;
import librarysystem.util.ServiceResponse;

public class OverdueCopies implements Initializable {
	@FXML
	private PublicationLookUp publicationViewController;
	@FXML
	private TableView<Copy> copiesInfo;
	@FXML
	private TableColumn<Copy, Integer> copyno;
	@FXML
	private TableColumn<Copy, String> checkoutdate;
	@FXML
	private TableColumn<Copy, String> duedate;
	@FXML
	private TableColumn<Copy, String> remarks;
	@FXML
	private TableColumn<Copy, String> firstname;
	@FXML
	private TableColumn<Copy, String> lastname;
	@FXML
	private TableColumn<Copy, String> memberid;

	private CheckedoutCopies checkedoutCopies;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		List<CheckoutRecordEntry> checkoutEntries;
		try {
			checkoutEntries = (new CheckoutController())
					.getAllCheckoutRecordEntries();
			checkedoutCopies = new CheckedoutCopies(checkoutEntries);
			publicationViewController.getTableView().getSelectionModel()
					.selectedItemProperty()
					.addListener((obs, oldSelection, newSelection) -> {
						copiesInfo.setItems(null);
						if (newSelection != null) {
							showCopiesInfo();
						}
					});
		} catch (ServiceResponse e) {
			Messages.showExceptionDialog(e.getMessage());
			e.printStackTrace();
		}
	}

	private void showCopiesInfo() {
		ObservableList<Publication> list = publicationViewController
				.getTableView().getSelectionModel().getSelectedItems();
		Publication pub = list.get(0);
		ObservableList<Copy> copies = FXCollections.observableArrayList();
		pub.getCopies().forEach(c -> copies.add(c));
		addCopiesColumnValueFactories();
		copiesInfo.setItems(copies);
	}

	private void addCopiesColumnValueFactories() {
		copyno.setCellValueFactory(new Callback<CellDataFeatures<Copy, Integer>, ObservableValue<Integer>>() {
			@Override
			public ObservableValue<Integer> call(
					CellDataFeatures<Copy, Integer> data) {
				return new ReadOnlyObjectWrapper<>(data.getValue().getCopyNum());
			}
		});
		checkoutdate
				.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Copy, String> data) {
						return new ReadOnlyObjectWrapper<>(checkedoutCopies
								.getCheckoutDate(data.getValue()));
					}
				});

		duedate.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Copy, String> data) {
				return new ReadOnlyObjectWrapper<>(checkedoutCopies
						.getDueDate(data.getValue()));
			}
		});

		remarks.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Copy, String> data) {
				try {
					return new ReadOnlyObjectWrapper<>(checkedoutCopies
							.getStatus(data.getValue()));
				} catch (ServiceResponse e) {
					Messages.showExceptionDialog(e.getMessage());
					e.printStackTrace();
					return new ReadOnlyObjectWrapper<>("");
				}
			}
		});

		firstname
				.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<Copy, String> data) {
						return new ReadOnlyObjectWrapper<>(checkedoutCopies
								.getFirstNameOfMember(data.getValue()));
					}
				});

		lastname.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Copy, String> data) {
				return new ReadOnlyObjectWrapper<>(checkedoutCopies
						.getLastNameOfMember(data.getValue()));
			}
		});

		memberid.setCellValueFactory(new Callback<CellDataFeatures<Copy, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(
					CellDataFeatures<Copy, String> data) {
				return new ReadOnlyObjectWrapper<>(checkedoutCopies
						.getCheckingMemberId(data.getValue()));
			}
		});
	}

	public void back(){
		UINavigator.loadUI(Constants.DASHBOARD);
	}
}
