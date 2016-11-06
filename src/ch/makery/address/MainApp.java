package ch.makery.address;

import ch.makery.address.model.Person;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;

    private ObservableList<Person> people = FXCollections.observableArrayList();

    public MainApp() {
        people.add(new Person("Hans", "Muster"));
        people.add(new Person("Ruth", "Mueller"));
        people.add(new Person("Heinz", "Kurz"));
        people.add(new Person("Cornelia", "Meier"));
        people.add(new Person("Werner", "Meyer"));
        people.add(new Person("Lydia", "Kunz"));
        people.add(new Person("Anna", "Best"));
        people.add(new Person("Stefan", "Meier"));
        people.add(new Person("Martin", "Mueller"));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("AddressApp");


        initRootLayout();

        showPersonOverview();
    }

    private void showPersonOverview() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
        AnchorPane personOverview = null;
        try {
            personOverview = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        rootLayout.setCenter(personOverview);
        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);
    }

    private void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean showPersonEditDialog(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = null;
            page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<Person> getPeople() {
        return people;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
