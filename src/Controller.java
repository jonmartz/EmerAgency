import Model.*;
import Model.Enum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Controller {

    @FXML
    public GridPane createEventPane;
    public Button createEventButton;
    public TextField eventTitleTextfield;
    public ChoiceBox eventOrganizationChoicebox;
    public ChoiceBox eventCategoryChoicebox;
    public TableView table;
    public TableColumn idColumn;
    public TableColumn titleColumn;
    public TableColumn buttonColumn;
    public Text findEventEventIDText;
    public Text findEventEventTitleText;
    public Text findEventEventDateText;
    public Text findEventEventStatusText;
    public Text findEventEventOrgText;
    public Text findEventEventPubText;
    public GridPane findEventsPane;
    private IModel model;
    public Button findEventsScreenButton;
    public Button createCategoryScreenButton;
    public Button createEventScreenButton;
    public Button loginScreenButton;
    public BorderPane mainPane;
    public GridPane loginPane;
    public GridPane createCategoryPane;
    public TextField usernameTextfield;
    public TextField passwordTextfield;
    public Button loginButton;
    public Text loginErrorText;
    public Button createCategoryButton;
    public TextField categoryNameTextfield;

    public void initialize(){
        ObservableList<Enum.Organization> secForces = FXCollections.observableArrayList();
        secForces.addAll(Enum.Organization.FIREMEN, Enum.Organization.POLICE, Enum.Organization.REDCROSS);
        eventOrganizationChoicebox.setItems(secForces);
        // Set columns of search results table
        idColumn.setCellValueFactory(new PropertyValueFactory<EventEntry, String>("ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<EventEntry, String>("title"));
        buttonColumn.setCellValueFactory(new PropertyValueFactory<>("null")); // just for setting up buttons
        buttonColumn.setCellFactory(getButtonCallback());
        loginScreen();
    }

    public void findEventsScreen() {
        ObservableList<EventEntry> items = FXCollections.observableArrayList();
        ArrayList<Event> events = model.getAllEvents();
        for (Event event : events){
            items.add(new EventEntry(event.id, event.title));
        }
        table.setItems(items);
        changeScreen(findEventsPane);
    }

    public void createCategoryScreen() {
        changeScreen(createCategoryPane);
    }

    public void createEventScreen() {
        ObservableList items = eventCategoryChoicebox.getItems();
        items.clear();
        items.addAll(model.getAllCategories());
        eventOrganizationChoicebox.setValue(eventOrganizationChoicebox.getItems().get(0));
        try {
            eventCategoryChoicebox.setValue(eventCategoryChoicebox.getItems().get(0));
        }catch (IndexOutOfBoundsException ignored) {}
        changeScreen(createEventPane);
    }


    public void loginScreen() {
        changeScreen(loginPane);
    }

    public void login() {
        User user = model.getUser(usernameTextfield.getText());
        if (user == null){
            loginErrorText.setText("wrong username");
            loginErrorText.setVisible(true);
            return;
        }
        if (!passwordTextfield.getText().equals(user.password)){
            loginErrorText.setText("wrong password");
            loginErrorText.setVisible(true);
            return;
        }
        model.setCurrentUser(user);
        loginErrorText.setVisible(false);
        showPopUp("Welcome, "+user.username);
        findEventsScreenButton.setDisable(false);
        createEventScreenButton.setDisable(false);
        createCategoryScreenButton.setDisable(false);
        findEventsScreen();
    }

    private void changeScreen(GridPane pane){
        Node currentPane = mainPane.getCenter();
        if (currentPane != null) currentPane.setVisible(false);
        pane.setVisible(true);
        mainPane.setCenter(pane);
    }

    public void createCategory() {
        Category category = CategoryFactory.createCategory(categoryNameTextfield.getText());
        model.addCategory(category);
        showPopUp("Category '"+category.name+"' created");
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void createEvent() {
        Event event = EventFactory.createEvent(eventTitleTextfield.getText(),
                (Enum.Organization) eventOrganizationChoicebox.getValue(),
                model.getCurrentUser().username,
                (Category) eventCategoryChoicebox.getValue());
        model.addEvent(event);
        showPopUp("Event '"+event.title+"' created");
    }

    public void showPopUp(String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text);
        alert.showAndWait();
    }

    /**
     * For table
     */
    public static class EventEntry {

        public String ID;
        public String Title;

        public EventEntry(int ID, String title) {
            this.ID = String.valueOf(ID);
            Title = title;
        }

        public String getTitle() {
            return Title;
        }

        public String getID() {
            return ID;
        }
    }

    /**
     * Function used to set the button column correctly (taken from stackoverflow)
     * @return Callback
     */
    private Callback<TableColumn<EventEntry, String>, TableCell<EventEntry, String>> getButtonCallback() {

        return new Callback<TableColumn<EventEntry, String>, TableCell<EventEntry, String>>() {
            @Override
            public TableCell call(final TableColumn<EventEntry, String> param) {
                final TableCell<EventEntry, String> cell = new TableCell<EventEntry, String>() {

                    final Button button = new Button("View");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            button.setOnAction(event -> {
                                EventEntry eventEntry = getTableView().getItems().get(getIndex());
                                checkEvent(model.getEvent(Integer.parseInt(eventEntry.ID)));
                            });
                            setGraphic(button);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }

    public void checkEvent(Event event){
        findEventEventIDText.setText(String.valueOf(event.id));
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        findEventEventDateText.setText(formatter.format(event.date));
        findEventEventDateText.setText(event.date.toString());
        String secForcesString = "";
        for (Enum.Organization securityForce : event.securityForces){
            secForcesString += securityForce+" ";
        }
        findEventEventOrgText.setText(secForcesString);
        findEventEventTitleText.setText(event.title);
        findEventEventStatusText.setText(event.status.name());
        findEventEventPubText.setText(event.publisherID);
    }
}
