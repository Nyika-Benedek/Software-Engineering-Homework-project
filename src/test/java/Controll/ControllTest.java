package Controll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllTest {

    @Test
    void nextPlayer() {
        Controll controll = new Controll();
        controll.nextPlayer();
        assertFalse(controll.isPlayer1);
    }


    @Test
    void turn() {
        Controll controll = new Controll();
        int volt = controll.kavics.getKupacA();
        controll.Turn('A', 1);
        assertEquals(controll.kavics.getKupacA() + 1, volt);
    }

    @Test
    void turnMindelvesz() {
        Controll controll = new Controll();
        int voltA = controll.kavics.getKupacA();
        int voltB = controll.kavics.getKupacB();
        controll.Turn(1);
        assertTrue((controll.kavics.getKupacA() + 1 == voltA && controll.kavics.getKupacB() + 1 == voltB));
    }

    @Test
    void Winner() {
        Controll controll = new Controll();
        //feltételezzük h ez a győztes lépése player1-nek
        controll.Turn(1);
        assertEquals(controll.getWinner(), "Player1");
    }

    @Test
    void Winner2() {
        Controll controll = new Controll();
        //feltételezzük, hogy 2lépésen belül a player2 nyer
        controll.Turn('A', 1);
        controll.Turn('B', 1);
        assertEquals(controll.getWinner(), "Player2");
    }

    @Test
    void isEndOfGame(){
        Controll controll = new Controll();
        assertTrue(!controll.isEndOfGame());
    }
}