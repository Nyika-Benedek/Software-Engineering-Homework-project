package Controll;

import Database.jdbiController;
import Modell.Kavics;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;
import java.io.IOException;

/**
 * Ez az osztály tartalmazza az ülzeti logikát és interaktál a UI felületével.
 */
public class Controll {

    /**
     * Ez a kavics tárolja az adatmodellünket.
     */
    public Kavics kavics = new Kavics();

    /**
     * Kinek a köre van jelenleg.
     */
    public boolean isPlayer1 = true;

    /**
     * Ennyi értéket vegyen majd ki.
     */
    public int amount = 0;

    /**
     * Endscreen-en van-e vagy sem.
     */
    public boolean isEndscreen = false;

    /**
     * Átadja a kört.
     */
    public void nextPlayer(){
        isPlayer1 = !isPlayer1;
    }


    /**
     * Megvizsgálja hogy vége van-e a játékank.
     * @return {@code boolean}
     */
    public boolean isEndOfGame(){
        return kavics.getKupacA() == 0 && kavics.getKupacB() == 0;
    }


    /**
     * Végrehajtja a kört.
     * @param kupac {@link char}: A vagy B kupac-ból vegyen el
     * @param x {@link int}: mennyit vegyen el
     */
    public void Turn(char kupac, int x){
        if ( kupac=='A'){
            if (kavics.getKupacA() >= x) {
                kavics.elvesz(kupac, x);
                nextPlayer();
            }
        }
        else {
            if (kavics.getKupacB() >= x) {
                kavics.elvesz(kupac, x);
                nextPlayer();
            }
        }

    }

    /**
     * Végrehajtja a kört.
     * @param x {@link int}: ennyit vesz el mind a kettő kupacból
     */
    public void Turn( int x){
        if (kavics.getKupacA() >= x && kavics.getKupacB() >= x){
            kavics.elveszMind(x);
            nextPlayer();
        }
    }

    /**
     * Visszaadja hogy ki nyerte meg a játékot.
     * @return {@link String}: visszatér "Player1" vagy "Player2" szöveggel
     */
    public String getWinner(){
        if(!isPlayer1)
            return "Player1";
        else
            return "Player2";
    }


    //                                   FXML STUFF


    /**
     * Ez kapcsolja össze az {@code kupacA}-t és a hozzá tartozó text fieldet.
     */
    @FXML
    public TextField Akupac;

    /**
     * Ez kapcsolja össze az {@code kupacB}-t és a hozzá tartozó text fieldet.
     */
    @FXML
    public TextField Bkupac;

    /**
     * Ez kapcsolja össze a {@code Amount}-t és a hozzá tartozó text fieldet.
     */
    @FXML
    public TextField AmountValue;

    /**
     * Ez adja meg, hogy mind a 2 kupacból vegyen el.
     */
    @FXML
    public RadioButton radio1;

    /**
     * Ez addja meg, hogy csak a {@code kupacA}-ból vegyen el.
     */
    @FXML
    public RadioButton radio2;

    /**
     * Ez addja meg, hogy csak a {@code kupacB}-ből vegyen el.
     */
    @FXML
    public RadioButton radio3;

    /**
     * Beállítja hogy a UI-on is a megfelelő értékek jelenjenek meg.
     */
    @FXML
    public void setKupacUIValues(){
        Akupac.setText(String.valueOf(kavics.getKupacA()));
        Bkupac.setText(String.valueOf(kavics.getKupacB()));
    }

    /**
     * Inicializálja a szükséges elemeket a UI-t.
     */
    @FXML
    public void initialize(){
        setKupacUIValues();
        radio1.setSelected(true);
    }

    /**
     * Visszajelzés a felhasználó számára, gyakorlatilag egy felugró párbeszédablak.
     * @param title {@code String}: ez lesz a felugró ablak neve
     * @param headerMessage {@code String}: ez lesz az alcíme
     * @param contentMessage {@code String}: ez lesz a ablak részletes üzenete
     */
    @FXML
    private static void Yamete(String title , String headerMessage , String contentMessage)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerMessage);
        alert.setContentText(contentMessage);
        alert.showAndWait();
    }

    /**
     * Ha még nem vagyunk az endscreenen {@code !isEndscreen} akkor hozza létre.
     * @param event {@code ActionEvent}: egy fxml UI-ról származó event
     * @throws IOException can be thrown
     */
    @FXML
    public void WinnerScene(ActionEvent event) throws IOException {
        if (!isEndscreen){
            isEndscreen = true;
            FXMLLoader fxmlLoader = new FXMLLoader(Controll.class.getResource("/kupac2.fxml"));
            Parent root = fxmlLoader.load();
            EndScreen controller = fxmlLoader.getController();
            controller.getWinner(getWinner());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     *  Az elvesz gomb lenyomásánál hívódik meg.
     *  A játék állása alapján és attól függően hogy milyen értékeket adtál meg a meghívja az arra megfelelő executeTo... eseményt.
     * @param event {@code ActionEvent}: egy fxml UI-ról származó event
     * @throws IOException can be thrown
     */
    @FXML
    public void execute(ActionEvent event) throws IOException {
        try {
            amount = Integer.parseInt(AmountValue.getText());
        } catch (NumberFormatException e) {
            Yamete("Nem szám!", "Nem számot adott meg",
                    "Kérem adjon meg egy számot");
            Logger.error("A felhasználó nem számot adott meg");
            return;
        }
        if (amount <= 0) {
            Yamete("Rossz érték!", "Szám intervallumon kívűl",
                    "Kérem adjon meg egy 0-nál nagyobb számot!");
            return;
        }

        if (radio1.isSelected())
            executeToBoth();

        else{
            if (radio2.isSelected())
                executeToA();
            else
                executeToB();

        }
        setKupacUIValues();
        amount = 0;
        if (isEndOfGame()) {
            jdbiController.insertWinner(getWinner());
            WinnerScene(event);
        }
    }

    /**
     * Végrehajtja mind a 2 kupacból való elvételt lekezelve és logolva.
     */
    @FXML
    public void executeToBoth(){
        if (amount > kavics.getKupacA() || amount > kavics.getKupacB()) {
            Yamete("Túl sok!", "Túl nagy értéket adott meg!",
                    "Túl nagy értéket adott meg! Kérem ellenórizze le, hogy mind a két kupacban van ennyi.");
            return;
        }
        Turn(amount);
        Logger.info(amount + "Mennyiség lett elvéve mind a 2-ből");
        Logger.info("Maradt kuapcA-ban: " + kavics.getKupacA());
        Logger.info("Maradt kuapcB-ban: " + kavics.getKupacB());
    }

    /**
     * Végrehajtja az Akupacól elvételt lekezelve és logolva.
     */
    @FXML
    public void executeToA(){
        if (amount > kavics.getKupacA()) {
            Yamete("Túl nagy érték!", "Nincs annyi kő az kupacA-ban",
                    "Kérem ellenőrizze hogy tényleg marad-e ennyi kő a kupacA-ban");
            return;
        }
        Turn('A', amount);
        Logger.info(amount + "Mennyiség lett elvéve kupacA-ból");
        Logger.info("Maradt kuapcA-ban: " + kavics.getKupacA());
    }

    /**
     * Végrehajtja az Bkupacól elvételt lekezelve és logolva.
     */
    @FXML
    public void executeToB(){
        if (amount > kavics.getKupacB()) {
            Yamete("Túl nagy érték!", "Nincs annyi kő az kupacB-ben",
                    "Kérem ellenőrizze hogy tényleg marad-e ennyi kő a kupacB-ben");
            return;
        }
        Turn('B', amount);
        Logger.info(amount + "Mennyiség lett elvéve kupacB-ből");
        Logger.info("Maradt kuapcB-ban: " + kavics.getKupacB());
    }
}
