package UI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Ez az osztály felel a kezdő Scene felépítéséért és megjelenítéséért.
 */
public class App extends javafx.application.Application {

    /**
     * Ez az metódus felel a kezdő Scene felépítéséért és megjelenítéséért.
     * @param stage {@code Stage}: fxml ből származó stage
     * @throws Exception Dobhat ilyen hibát
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/kupac.fxml")));
        stage.setTitle("Kö játék");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
