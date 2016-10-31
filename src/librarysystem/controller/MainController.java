package librarysystem.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane uiHolder;

    public void setUI(Node node, Object userData) {
        uiHolder.getChildren().setAll(node);
        uiHolder.setUserData(userData);
    }

}