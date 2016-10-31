package librarysystem.mappings;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import librarysystem.controller.PublicationController;
import librarysystem.models.Publication;
import librarysystem.util.Messages;
import librarysystem.util.Functors;
import librarysystem.util.ServiceResponse;

public class PublicationLookUp implements Initializable{

	private List<Publication> allPublications;
	
	@FXML
	private TextField searchPublicationId;
	@FXML
	private TextField searchPublicationTitle;
	@FXML 
	private TableView<Publication> tableView;
	public TableView<Publication> getTableView() {
		return tableView;
	}

	@FXML
	private TableColumn<Publication, String> collPublicationId;
	@FXML
	private TableColumn<Publication, String> colTitle;
	@FXML
	private TableColumn<Publication, Integer> colAvailableCopies;
	@FXML
	private TableColumn<Publication, Integer> colTotalCopies;
	@FXML
	private TableColumn<Publication, Integer> colMaxCheckoutLength;
	@FXML
	private TableColumn<Publication,String> colPublicationType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		showCompleteList();
		addSearchCapabilities();
	}
	
	private void addSearchCapabilities() {
		searchPublicationId.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				filterTableData();
			}
		});
		
		searchPublicationTitle.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				filterTableData();
			}		
		});	
	}

	@SuppressWarnings("unchecked")
	public void showCompleteList() {
		try {
			PublicationController controller = new PublicationController();
			ServiceResponse serviceResponse = controller.getAllPublications();
			
			colPublicationType.setCellValueFactory(new Callback<CellDataFeatures<Publication, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Publication, String> data){
					return new ReadOnlyObjectWrapper<>(data.getValue().getClass().getSimpleName());
				}
			});
			
			
			collPublicationId.setCellValueFactory(new Callback<CellDataFeatures<Publication, String>, ObservableValue<String>>(){
				@Override
				public ObservableValue<String> call(CellDataFeatures<Publication, String> data){
					return new ReadOnlyObjectWrapper<>(data.getValue().getPublicationId());
				}
			});
			colTitle.setCellValueFactory(new PropertyValueFactory<Publication, String>("title"));
			colAvailableCopies.setCellValueFactory(new Callback<CellDataFeatures<Publication, Integer>, ObservableValue<Integer>>(){
				@Override
				public ObservableValue<Integer> call(CellDataFeatures<Publication, Integer> data){
					int numOfAvailableCopies = Functors.AVAILABLE_COPIES_COUNTER.apply(data.getValue());
					return new ReadOnlyObjectWrapper<>(numOfAvailableCopies);
				}
			});
			colTotalCopies.setCellValueFactory(new Callback<CellDataFeatures<Publication, Integer>, ObservableValue<Integer>>(){
				@Override
				public ObservableValue<Integer> call(CellDataFeatures<Publication, Integer> data){
					int total = 0;
					if(data.getValue().getCopies() != null){
						total = data.getValue().getCopies().size();
					}
					return new ReadOnlyObjectWrapper<>(total);
				}
			});
			colMaxCheckoutLength.setCellValueFactory(new PropertyValueFactory<Publication, Integer>("maxCheckoutLength"));
			
			allPublications = (List<Publication>) serviceResponse.getData();
			searchPublicationId.setText("");
			searchPublicationTitle.setText("");
			tableView.getItems().clear();
			allPublications.forEach(p -> tableView.getItems().add(p));

		} catch (Exception e) {
			e.printStackTrace();
			Messages.showServiceReseponeMessage();
		}

	}

	private void filterTableData() {
		String newId = searchPublicationId.getText() != null? searchPublicationId.getText().toLowerCase(): "";
		String newTitle = searchPublicationTitle.getText() != null? searchPublicationTitle.getText().toLowerCase() :"";
		List<Publication> result = Functors.PUBLICATION_FILTER.apply(allPublications, newId, newTitle);	
		tableView.getItems().clear();
		result.forEach(p -> tableView.getItems().add(p));
	}
}
