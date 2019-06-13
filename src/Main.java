import Model.TestModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("view.fxml").openStream());
        primaryStage.setTitle("EmerAgency");
        primaryStage.setScene(new Scene(root, 600, 400));

        Controller controller = fxmlLoader.getController();
        controller.setModel(new TestModel()); //todo: change to real model
        controller.initialize();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
