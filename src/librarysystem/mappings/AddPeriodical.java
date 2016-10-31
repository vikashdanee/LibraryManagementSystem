//package librarysystem.mappings;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.TextField;
//import librarysystem.controller.PublicationController;
//import librarysystem.controller.UINavigator;
//import librarysystem.models.Periodical;
//import librarysystem.util.Messages;
//import librarysystem.util.Constants;
//import librarysystem.util.ServiceResponse;
//
//public class AddPeriodical {
//	@FXML
//	private TextField issue_number;
//
//	@FXML
//	private TextField title;
//
//	@FXML
//	private TextField maxcheckoutlength;
//
//	@FXML
//	protected void cancelAdding() {
//		issue_number.getScene().getWindow().hide();
//	}
//
//	@FXML
//	protected void addNewPeriodical() {
//		Periodical p = new Periodical();
//		p.setIsssueNo(issue_number.getText());
//		p.setTitle(title.getText());
//		p.setMaxCheckoutLength(Integer.parseInt(maxcheckoutlength.getText()));
//		ServiceResponse response;
//		try {
//			response = (new PublicationController()).addNewPeriodical(p);
//			Messages.showServiceReseponeMessage(response);
//			if (response.getSuccess()) {
////				issue_number.getScene().getWindow().hide();
//				back();
//			}
//		} catch (Exception e) {
//			Messages.showExceptionDialog(e.getMessage());
//		}
//	}
//
//	public void back(){
//		UINavigator.loadUI(Constants.DASHBOARD);
//	}
//}
