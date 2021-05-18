package Controll;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.tinylog.Logger;

/**
 * Ez az osztály hozza létre a mecs utáni képernyőt.
 */
public class EndScreen {

    /**
     * Ez tárolja el hogy mit írjunk ki az Endscreen-re.
     */
    public String endPhrase;

    /**
     * Ez kapcsolja össze a UI-on lévő text felirattal.
     */
    @FXML
    public Text out;

    /**
     * Beállítja a {@link EndScreen#endPhrase}-t.
     * @param winner {@code String}: Addja meg, ohgy ki nyert Player1 vagy Player2
     */
    public void getWinner(String winner){
        this.endPhrase = winner + " a nyetes!";
        Logger.info("A nyertes:  " + winner);
    }

    /**
     * Késleltetve lefuttatja az inicializálást, hogy legyen ideje beállítani megfelelő értékeket a megjelenítés előtt.
     */
    @FXML
    private void initialize(){
        Platform.runLater(() -> out.setText(endPhrase));

    }

    /**
     *
     * Az exit gomb lenyomásánál hívódik meg, bezárja a programot.
     */
    @FXML
    public void QuitGame() {
        Platform.exit();
        Logger.info("A felhasználó kilépett.");
    }
}
