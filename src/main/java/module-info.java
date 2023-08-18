module ku.cs.lab {
    requires javafx.controls;
    requires javafx.fxml;


    opens ku.cs.lab to javafx.fxml;
    exports ku.cs.lab;
}