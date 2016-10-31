package librarysystem.mappings;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import librarysystem.controller.PublicationController;
import librarysystem.controller.UINavigator;
import librarysystem.models.Book;
import librarysystem.models.Publication;
import librarysystem.util.Constants;
import librarysystem.util.Messages;

public class AddNewCopy {

	@FXML
	private PublicationLookUp publicationViewController;
	@FXML
	private TextField txtNumOfCopies;

	@FXML
	private void addNewCopy() {
		ObservableList<Publication> selected = publicationViewController.getTableView()
				.getSelectionModel().getSelectedItems();
		if (selected.size() == 0) {
			Messages
					.showInformationDialog("Select a publication first");
		} else if (selected.size() > 1) {
			Messages
					.showInformationDialog("Selecting multiple items is not allowed!!");
		} else {
			try {
				Integer noOfCopies = Integer.parseInt(txtNumOfCopies.getText());
				Publication p = selected.get(0);
				for (int i = 0; i < noOfCopies; i++) {
					p.addCopy();
				}

				/*if (p instanceof Periodical) {
					new PublicationController()
							.addNewPeriodical((Periodical) p);
				} else */if (p instanceof Book) {
					new PublicationController().addNewBook((Book) p);
				}
				publicationViewController.showCompleteList();
				Messages
						.showInformationDialog(noOfCopies + " copies added to the selected publication.");
			} catch (NumberFormatException e) {
				Messages
						.showExceptionDialog("Number of copies to be added should be a valid number");
			} catch (Exception e) {
				Messages.showExceptionDialog(e.getMessage());
			}
		}
	}
	
	@FXML
	protected void cancelWindow() {
		UINavigator.loadUI(Constants.DASHBOARD);
	}
}
