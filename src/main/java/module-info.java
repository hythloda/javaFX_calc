module com.tool.calculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tool.calculator to javafx.fxml;
    exports com.tool.calculator;
}
