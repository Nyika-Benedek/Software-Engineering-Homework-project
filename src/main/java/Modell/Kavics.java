package Modell;

import org.tinylog.Logger;

import java.util.Random;

/**
 * Ez az osztály szimbolizálja a 2 kavics kupacot és implementálja a minimális szükséges logikát.
 */
public class Kavics {

    /**
     * Ez a 2 int szimbolizálja a 2 kupacunkat.
     */
    public int kupacA, kupacB;

    /**
     * Ez a kupacok max értékét tárolja.
     */
    static final int MAX_KUPAC_SIZE = 10;

    /**
     * Visszaadja a {@link Kavics#kupacA} méretét.
     * @return {@code int}
     */
    public int getKupacA() {
        return kupacA;}

    /**
     * Visszaadja a {@link Kavics#kupacB} méretét.
     * @return {@code int}
     */
    public int getKupacB() {
        return kupacB;}

    /**
     * Konstriktor: feltölti a kupacokat 1-tól {@link Kavics#MAX_KUPAC_SIZE}-ig egymástól függetlenül.
     */
    public Kavics(){
        Random r = new Random();
        //generálok kopacokméretet 1 és 10 között
        kupacA = r.nextInt(MAX_KUPAC_SIZE-1) + 1;
        kupacB = r.nextInt(MAX_KUPAC_SIZE-1) + 1;
        Logger.info("kupacA tartalma: " + this.getKupacA());
        Logger.info("kupacB tartalma: " + this.getKupacB());
    }

    /**
     * Elvesz A vagy B kupavból egy adott mennyiséget.
     * @param kupac {@code char}: 'A' vagy 'B' kupacból
     * @param x {@code int}: ennyit vegyen el
     */
    public void elvesz(char kupac, int x){
        if (kupac=='A')
            this.kupacA -= x;
        else
            this.kupacB -= x;
    }

    /**
     * Mind a 2 kupacból elvesz.
     * @param x {@code int}: ennyit vesz el
     */
    public void elveszMind(int x){
        this.kupacA -= x;
        this.kupacB -= x;
    }
}
