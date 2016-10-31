package librarysystem.mappings;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import librarysystem.controller.PublicationController;
import librarysystem.controller.UINavigator;
import librarysystem.models.Address;
import librarysystem.models.Author;
import librarysystem.models.Book;
import librarysystem.util.Messages;
import librarysystem.util.Constants;
import librarysystem.util.ServiceResponse;

public class AddBook {

	@FXML
	private TextField isbn;

	@FXML
	private TextField title;	

	@FXML
	private TextField maxcheckoutlength;
	
	@FXML
	private TableView<Author> authorstable;
	
	@FXML
	private TableColumn<Author, String> column_firstname;
	
	@FXML
	private TableColumn<Author, String> column_lastname;
	
	@FXML
	private TableColumn<Author, String> column_credentials;
	
	@FXML 
	private TableColumn<Author, String> column_phonenumber;
	
	@FXML
	private TableColumn<Author, String> column_shortBio;
	
	@FXML
	protected void addNewMember() {
		Book book = new Book();
		book.setISBN(isbn.getText());
		book.setTitle(title.getText());
		book.setMaxCheckoutLength(Integer.parseInt(maxcheckoutlength.getText()));
		
		List<Author> authors = new ArrayList<Author>();
		for(Author e: authorstable.getItems()){
			authors.add(e);
		}
		book.setAuthorList(authors);

		try {
			ServiceResponse response = (new PublicationController()).addNewBook(book);
			if(response.getSuccess()){
				Messages.showInformationDialog(response.getMessage());
				back();
			}else{
				Messages.showWarningDialog(response.getMessage());
			}
			
		} catch (Exception e1) {
			Messages.showExceptionDialog(e1.getMessage());
		}
		
	}
	
	@FXML
	protected void cancelAdding() {
		isbn.getScene().getWindow().hide();
	}
	
	@FXML
	protected void addNewAuthor() {
		Author a = new Author();
		a.setFirstname("First Name");
		a.setLastName("Last Name");
		a.setCredentials("Credentials");
		a.setPhone("Phone Number");
		a.setShortBio("Short Bio");
		Address address = new Address("Street","City", "State", "Zip");
		a.setAddress(address);
		
		column_firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
		column_firstname.setCellFactory(TextFieldTableCell.forTableColumn());		
		column_firstname.setOnEditCommit(
				(CellEditEvent<Author, String> t)->{
				 Author athr=	t.getTableView().getItems().get(t.getTablePosition().getRow());
				 athr.setFirstname(t.getNewValue());
		});
		
		column_lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		column_lastname.setCellFactory(TextFieldTableCell.forTableColumn());		
		column_lastname.setOnEditCommit(
				(CellEditEvent<Author, String> t)->{
				 Author athr=	t.getTableView().getItems().get(t.getTablePosition().getRow());
				 athr.setLastName(t.getNewValue());
		});
		
		column_credentials.setCellValueFactory(new PropertyValueFactory<>("credentials"));
		column_credentials.setCellFactory(TextFieldTableCell.forTableColumn());		
		column_credentials.setOnEditCommit(
				(CellEditEvent<Author, String> t)->{
				 Author athr=	t.getTableView().getItems().get(t.getTablePosition().getRow());
				 athr.setCredentials(t.getNewValue());
		});
		
		column_phonenumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
		column_phonenumber.setCellFactory(TextFieldTableCell.forTableColumn());		
		column_phonenumber.setOnEditCommit(
				(CellEditEvent<Author, String> t)->{
				 Author athr=	t.getTableView().getItems().get(t.getTablePosition().getRow());
				 athr.setPhone(t.getNewValue());
		});
		
		
		
		column_shortBio.setCellValueFactory(new PropertyValueFactory<>("shortBio"));
		column_shortBio.setCellFactory(TextFieldTableCell.forTableColumn());		
		column_shortBio.setOnEditCommit(
				(CellEditEvent<Author, String> t)->{
				 Author athr=	t.getTableView().getItems().get(t.getTablePosition().getRow());
				 athr.setPhone(t.getNewValue());
		});
		
		
		authorstable.getItems().add(a);
	}
	
	public void back(){
		UINavigator.loadUI(Constants.DASHBOARD);
	}	
}
