
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        var ui = new LibraryUI();

        Scene scene = new Scene(ui.getView(), 600, 400);

        stage.setScene(scene);
        stage.setTitle("Library System");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
