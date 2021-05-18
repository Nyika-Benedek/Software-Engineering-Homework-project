package Main;

import UI.App;

/**
 * Ez az osztály indítja el a játékot.
 */
public class Main {

    /**
     * Ez a metódus indítja el a játékot.
     * @param args {@code String[]} a program bemeneti paramétere
     */
    public static void main(String[] args) {
        javafx.application.Application.launch(App.class, args);
    }
}
