package librarysystem.main;


import java.io.IOException;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import librarysystem.util.Constants;

public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Library Management System");
		stage.setScene(createScene(loadUserLogin()));
		stage.setResizable(false);
		stage.setWidth(400);
		stage.setHeight(270);
		Platform.setImplicitExit(false);
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        Platform.exit();
		    }
		});
	}

	private Pane loadUserLogin() throws IOException{
//		Pane userLogin = FXMLLoader.load(getClass().getResource("/view/UserLogin.fxml"));
		Pane userLogin = FXMLLoader.load(getClass().getResource(Constants.LOGIN));
		return userLogin;
	}

	private Scene createScene(Pane mainPane) {
		Scene scene = new Scene(mainPane);
		return scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}