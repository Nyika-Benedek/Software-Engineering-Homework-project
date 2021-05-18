package Modell;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KavicsTest {

    @Test
    void getKupacA() {
        Kavics kupac = new Kavics();
        assertEquals(kupac.getKupacA(), kupac.kupacA);
    }

    @Test
    void getKupacB() {
        Kavics kupac = new Kavics();
        assertEquals(kupac.getKupacB(), kupac.kupacB);
    }

    @Test
    void elveszA() {
        Kavics kupac = new Kavics();
        int volt = kupac.getKupacA();
        kupac.elvesz('A', 1);
        assertEquals(kupac.getKupacA() + 1, volt );
    }

    @Test
    void elveszB() {
        Kavics kupac = new Kavics();
        int volt = kupac.getKupacB();
        kupac.elvesz('B', 1);
        assertEquals(kupac.getKupacB() + 1, volt);
    }

    @Test
    void elveszMind() {
        Kavics kupac = new Kavics();
        int voltA = kupac.getKupacA();
        int voltB = kupac.getKupacB();
        kupac.elveszMind(1);
        assertTrue((kupac.getKupacA() + 1 == voltA && kupac.getKupacB() + 1 == voltB));
    }
}