package Database;


import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Date;


/**
 * Egy DAO interface az SQL futtatásához.
 */
public interface LeaderboardDAO
{
    /**
     * Létrehoz egy új táblát amiben (nev, datum) van tárolva.
     */
    @SqlUpdate("CREATE TABLE Eredmenyek (nev VARCHAR , datum BIGINT PRIMARY KEY)")
    void createTable();

    /**
     * Rakjuk bele az aktuális győztest és mellé egy időt.
     * @param name {@code String}: győztes neve
     * @param datum {@code long}: aktuális idő
     */
    @SqlUpdate("INSERT INTO Eredmenyek (nev,datum) VALUES (:nev,:datum)")
    void insert(@Bind("nev") String name, @Bind("datum") long datum);

    /**
     * Törlünk egy bejegyzést a databese-ből datum alapján.
     * @param datum {@code long}: aktuális idő
     */
    @SqlUpdate("DELETE FROM Eredmenyek WHERE datum =(:datum)")
    void delete(@Bind("datum") long datum);

    /**
     * Visszaadja a játékoshoz tartozó győzelmek számát.
     * @param nev {@code String}: Játékos neve
     * @return {@code int}: Mennyi győzelme volt eddig elmentve
     */
    @SqlQuery("SELECT COUNT(*) FROM Eredmenyek WHERE nev IN(:nev)")
    int wins(@Bind("nev") String nev );
}
