package ku.cs.lab;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class HelloApplication extends Application {
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Hello World", 1024, 768);
        configRoute();
        FXRouter.goTo("students-table");
    }

    private void configRoute() {
        String viewPath = "ku/cs/views/";
        FXRouter.when("hello", viewPath + "hello-view.fxml");
        FXRouter.when("student-profile", viewPath + "student.fxml");
        FXRouter.when("student-list", viewPath + "student-list.fxml");
        FXRouter.when("students-table", viewPath + "students-table.fxml");
        FXRouter.when("student-score", viewPath + "student-score.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}