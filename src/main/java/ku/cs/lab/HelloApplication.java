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
        FXRouter.goTo("hello");
    }

    private void configRoute() {
        String viewPath = "ku/cs/views/";
        FXRouter.when("hello", viewPath + "hello-view.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}