package Controll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndScreenTest {

    @Test
    void getWinner() {
        EndScreen test = new EndScreen();
        test.getWinner("TEST");
        assertEquals(test.endPhrase, "TEST a nyetes!");
    }
}