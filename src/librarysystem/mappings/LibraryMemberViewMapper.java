package librarysystem.mappings;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import librarysystem.controller.CheckoutController;
import librarysystem.controller.LibraryMemberController;
import librarysystem.controller.UINavigator;
import librarysystem.controller.UserController;
import librarysystem.enums.Role;
import librarysystem.models.CheckoutRecordEntry;
import librarysystem.models.LibraryMember;
import librarysystem.util.Constants;
import librarysystem.util.Functors;
import librarysystem.util.Messages;
import librarysystem.util.ServiceResponse;

public class LibraryMemberViewMapper implements Initializable {

	@FXML
	private TableView<LibraryMember> tableView;
	@FXML
	private TableColumn<LibraryMember, String> colId;
	@FXML
	private TableColumn<LibraryMember, String> colFirstName;
	@FXML
	private TableColumn<LibraryMember, String> colLastName;
	@FXML
	private TableColumn<LibraryMember, String> colStreet;
	@FXML
	private TableColumn<LibraryMember, String> colCity;
	@FXML
	private TableColumn<LibraryMember, String> colState;
	@FXML
	private TableColumn<LibraryMember, String> colZip;
	@FXML
	private TableColumn<LibraryMember, String> colPhone;
	@FXML
	private TextField searchMemberId;
	@FXML
	private TextField searchMemberName;
	@FXML private Button editBTN; 
	@FXML private Button deleteBTN;

	private static LibraryMember libraryMember = null;

	private LibraryMemberController libraryMemberController = new LibraryMemberController();

	private ObservableList<LibraryMember> masterData = FXCollections
			.observableArrayList();
	private ObservableList<LibraryMember> filteredData = FXCollections
			.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		populateGrid();
		
		if(UserController.role.toString().equals(Role.LIBRARIAN.toString())){
			editBTN.setDisable(true);
			deleteBTN.setDisable(true);
			
		} 


	}

	public void populateGrid() {
		showList();

		filteredData.addAll(masterData);
		tableView.setItems(filteredData);

		// Listen for text changes in the filter text field
		searchMemberName.textProperty().addListener(
				new ChangeListener<String>() {
					public void changed(
							javafx.beans.value.ObservableValue<? extends String> observable,
							String oldValue, String newValue) {
						updateFilteredData();
					};
				});

		searchMemberId.textProperty().addListener(new ChangeListener<String>() {
			public void changed(
					javafx.beans.value.ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateFilteredData();
			};
		});
	}

	private void showList() {
		try {
			ServiceResponse serviceResponse = libraryMemberController
					.getMembers();
			@SuppressWarnings("unchecked")
			List<LibraryMember> memberList = (List<LibraryMember>) serviceResponse
					.getData();
			for (LibraryMember libraryMember : memberList) {

				colId.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
						"memberId"));
				colFirstName
						.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
								"firstname"));
				colLastName
						.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
								"lastName"));
				colPhone.setCellValueFactory(new PropertyValueFactory<LibraryMember, String>(
						"phone"));

				/*colCity.setCellValueFactory(new PropertyValueFactory<Address, String>(
						"city"));*/
				colCity.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<LibraryMember, String> data) {
						return new ReadOnlyObjectWrapper<>(data.getValue().getAddress().getCity());
					}
					
				});

				colStreet
						.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
							@Override
							public ObservableValue<String> call(
									CellDataFeatures<LibraryMember, String> data) {
								return new ReadOnlyObjectWrapper<>(data.getValue().getAddress().getStreet());
							}
						});

				colState.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<LibraryMember, String> data) {
						return new ReadOnlyObjectWrapper<>(data.getValue().getAddress().getState());
					}
				});

				colZip.setCellValueFactory(new Callback<CellDataFeatures<LibraryMember, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(
							CellDataFeatures<LibraryMember, String> data) {
						return new ReadOnlyObjectWrapper<>(data.getValue().getAddress().getZip());
					}
				});

				masterData.add(libraryMember);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messages.showServiceReseponeMessage();
		}

	}

	public void addEditAction() {
		try {
			ObservableList<LibraryMember> list = tableView.getSelectionModel()
					.getSelectedItems();

			if (list.size() == 0) {
				Messages.showInformationDialog("Select Member First");
			} else {
				libraryMember = list.get(0);
				new Member().setRecordAndShow(list.get(0));
				// hideWindow();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteRecord() {
		try {
			ObservableList<LibraryMember> list = tableView.getSelectionModel()
					.getSelectedItems();
			if (list.size() == 0) {
				Messages.showInformationDialog("Select Member First");
			} else {
				if (Messages
						.showConfirmDialog("Are you sure to delete?")) {
					libraryMember = list.get(0);
					ServiceResponse serviceResponse = libraryMemberController
							.deleteMember(libraryMember.getMemberId());
					Messages
							.showServiceReseponeMessage(serviceResponse);

					filteredData.clear();
					masterData.clear();
					populateGrid();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			Messages.showServiceReseponeMessage();
		}
	}

	@FXML
	protected void printCheckoutInfo() {
		try {
			ObservableList<LibraryMember> list = tableView.getSelectionModel()
					.getSelectedItems();
			if (list.size() == 0) {
				Messages.showInformationDialog("Select Member First");
			} else {
				libraryMember = list.get(0);
				ServiceResponse serviceResponse = new CheckoutController()
						.getCheckoutDetail(libraryMember.getMemberId());
				@SuppressWarnings("unchecked")
				List<CheckoutRecordEntry> checkoutEntries = (List<CheckoutRecordEntry>) serviceResponse
						.getData();
				if(checkoutEntries.size()> 0){
					StringBuffer stringBuffer = new StringBuffer();
					stringBuffer.append(String.format("%-5s", "SNO")
							+ String.format("%-15s", "ISBN/Issue")
							+ String.format("%-30s", "Title")
							+ String.format("%-12s", "Publication")
							+ String.format("%-15s", "Checkout Date")
							+ String.format("%-15s", "Due Date"));
					
					stringBuffer
							.append("\n========================================================================================");
					int count = 0;
					for (CheckoutRecordEntry checkoutRecordEntry : checkoutEntries) {
						stringBuffer.append("\n");
						stringBuffer.append(String.format("%-5s", ++count)
								+ String.format("%-15s", checkoutRecordEntry
										.getCopy().getPublication()
										.getPublicationId())
								+ String.format("%-30s", checkoutRecordEntry
										.getCopy().getPublication().getTitle())
								+ String.format("%-12s", checkoutRecordEntry
										.getCopy().getPublication().getClass()
										.getSimpleName())
								+ String.format("%-15s",
										checkoutRecordEntry.getCheckoutDate())
								+ String.format("%-15s",
										checkoutRecordEntry.getDueDate()));
					}
					stringBuffer
					.append("\n========================================================================================");
					
					System.out.println(stringBuffer.toString());
					Messages.showInformationDialog("Look in console");
				} else {
					Messages.showInformationDialog("User doesn't have any checkout records");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messages.showServiceReseponeMessage();
		}

	}

	public static LibraryMember getSelectedMember() {
		return libraryMember;
	}

	private void updateFilteredData() {
		filteredData.clear();
		String idSubString = searchMemberId.getText() == null ? ""
				: searchMemberId.getText();
		String nameSubString = searchMemberName.getText() == null ? ""
				: searchMemberName.getText();
		List<LibraryMember> members = Functors.MEMBER_FILTER.apply(masterData,
				idSubString, nameSubString);
		members.forEach(m -> filteredData.add(m));
		// Must re-sort table after items changed
		reapplyTableSortOrder();
	}

	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<LibraryMember, ?>> sortOrder = new ArrayList<>(
				tableView.getSortOrder());
		tableView.getSortOrder().clear();
		tableView.getSortOrder().addAll(sortOrder);
	}

	public void back() {
		libraryMember = null;
		UINavigator.loadUI(Constants.DASHBOARD);
	}
}
