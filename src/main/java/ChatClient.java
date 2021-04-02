import com.dawist_o.client.controllers.StageController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ChatClient extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new StageController(primaryStage);
    }
}
