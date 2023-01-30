package com.tool.calculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CalculatorApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(CalculatorApplication.class.getResource("calculator.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        CalculatorController controller = loader.getController();
        TextField reader = controller.getInputField();

        reader.textProperty().addListener((observable, oldValue, newValue) -> {
            int location = reader.getText().length();
            Platform.runLater(() -> reader.positionCaret(location));
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
