import Model.*;
import Model.Enum;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sun.util.calendar.CalendarDate;

import java.time.LocalDate;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("view.fxml").openStream());
        primaryStage.setTitle("EmerAgency");
        primaryStage.setScene(new Scene(root, 600, 400));

        Model model = Model.getInstance();
        Controller controller = fxmlLoader.getController();
        controller.setModel(model);
        controller.initialize();

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
