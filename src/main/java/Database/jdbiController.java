package Database;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.h2.H2DatabasePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.tinylog.Logger;

/**
 * Ez az osztály összekapcsolja a controll-t a databas-el.
 */
public class jdbiController
{
    /**
     * Visszaadja a rendszeridőt.
     * @return {@code long}: SQL csomagból generált aktuális idő
     */
    static private long getCurrentTime(){
        return System.currentTimeMillis();
    }

    /**
     * Ez a (SINGLETON)példány lesz a ami összekapcsolja a jdbicontroll-t a database-el.
     */
    private static Jdbi jdbi;

    /**
     * Egy private konstruktor, hogy létrehozzuk/betöltsük a database-t.
     */
    private jdbiController()
    {
        jdbi=Jdbi.create("jdbc:h2:file:~/.LeaderBoard/leaderboard","felhasznalonev","");
        jdbi.installPlugin(new SqlObjectPlugin());
        jdbi.installPlugin(new H2DatabasePlugin());
        try
        {
            jdbi.withExtension(LeaderboardDAO.class, dao -> {dao.createTable(); return true;});
        }
        catch (Exception e)
        {
            Logger.info("Database már létezik");
        }
    }

    /**
     * Egy publikus konstruktor, ha már létezik akkor ne hozzon létre egy újabbat, hanem azt használja inkább (SINGLETON).
     * @return {@link jdbiController#jdbiController() Jdbi}: database controller objektum
     */
    public static Jdbi getInstance()
    {
        if(jdbi==null)
        {
            new jdbiController();
        }
        return jdbi;
    }

    /**
     * Beszúr egy új sort az adatbázisba a megadott névvel és az aktuális idővel.
     * @param winnerName {@code String}: győztes neve
     */
    public static void insertWinner(String winnerName)
    {
        new jdbiController();
        jdbi.withExtension(LeaderboardDAO.class, dao -> {dao.insert(winnerName, getCurrentTime());return true;});
        Logger.info(winnerName + " Győzelme el lett tárolva az adatbázisunkban.");
    }

}
