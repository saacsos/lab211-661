module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;

    opens ku.cs.lab to javafx.fxml;
    exports ku.cs.lab;
    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
}