package dumper;

import Exceptions.RequestBadStatusException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dumper.AccountInfo.AccountInfo;
import org.json.JSONException;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by onotole on 10.06.16.
 */
public class DBManager {
    Connection connection;
    Statement statement;
    private final String TABLENAME = "WOTB";

    public void connect(String dbName) {
        if (connection != null) return;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String databaseUrl = "jdbc:sqlite:" + dbName;
        try {
            connection = DriverManager.getConnection(databaseUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAccountInfoDB(String dbName) {
        String req = String.format("CREATE TABLE IF NOT EXISTS %s (ACCOUNT_ID INTEGER, NICKNAME TEXT, CREATED_AT INTEGER, " +
                "UPDATED_AT INTEGER, LAST_BATTLE_TIME INTEGER, SPOTTED INTEGER, MAX_FRAGS_TANK_ID INTEGER," +
                "HITS INTEGER, FRAGS INTEGER, MAX_XP INTEGER, MAX_XP_TANK_ID INTEGER, WINS INTEGER, LOSSES INTEGER," +
                "CAPTURE_POINT INTEGER, BATTLES INTEGER, DAMAGE_DEALT INTEGER, DAMAGE_RECEIVED INTEGER, MAX_FRAGS INTEGER, " +
                "SHOTS INTEGER, FRAGS8P INTEGER, XP INTEGER, WIN_AND_SURVIVED INTEGER, SURVIVED_BATTLES INTEGER, " +
                "DROPPED_CAPTURE_POINTS INTEGER);", TABLENAME);

        doUpdateRequest(req, dbName);
    }

    public void saveAccountInfo(AccountInfo ai, String dbName) {
        String req = String.format("INSERT INTO %s (ACCOUNT_ID, NICKNAME, CREATED_AT, UPDATED_AT, LAST_BATTLE_TIME, " +
                " SPOTTED, MAX_FRAGS_TANK_ID, HITS, FRAGS, MAX_XP, MAX_XP_TANK_ID, WINS, LOSSES, " +
                "CAPTURE_POINT, BATTLES, DAMAGE_DEALT, DAMAGE_RECEIVED, MAX_FRAGS, SHOTS, FRAGS8P, XP," +
                " WIN_AND_SURVIVED, SURVIVED_BATTLES, DROPPED_CAPTURE_POINTS) VALUES (\"%d\", \"%s\", \"%d\", \"%d\", " +
                "\"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", " +
                "\"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\", \"%d\");",
                TABLENAME,
                ai.accountId,
                ai.nickname,
                ai.createdAt,
                ai.updatedAt,
                ai.lastBattleTime,
                ai.statistics.all.spotted,
                ai.statistics.all.maxFragsTankId,
                ai.statistics.all.hits,
                ai.statistics.all.frags,
                ai.statistics.all.maxXp,
                ai.statistics.all.maxXpTankId,
                ai.statistics.all.wins,
                ai.statistics.all.losses,
                ai.statistics.all.capturePoints,
                ai.statistics.all.battles,
                ai.statistics.all.damageDealt,
                ai.statistics.all.damageReceived,
                ai.statistics.all.maxFrags,
                ai.statistics.all.shots,
                ai.statistics.all.frags8p,
                ai.statistics.all.xp,
                ai.statistics.all.winAndSurvived,
                ai.statistics.all.survivedBattles,
                ai.statistics.all.droppedCapturePoints);
        doUpdateRequest(req, dbName);
    }

    public void clearDB(String dbName) {
        doUpdateRequest(String.format("DELETE FROM %s;", TABLENAME), dbName);
    }

    public void dropDB(String dbName) {
        doUpdateRequest(String.format("DELETE FROM %s;", TABLENAME), dbName);
    }

    public void mergeWithDB(String dbName, int number) {
        try {
            Statement statement = connection.createStatement();

            statement.execute("attach '" + dbName + "' as toMerge" + number + "; BEGIN; INSERT INTO " + TABLENAME
                    + " SELECT * FROM toMerge" + number + "." + TABLENAME + "; COMMIT;");

//            statement.closeOnCompletion();
//            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private int doUpdateRequest(String request, String dbName) {
        if (connection == null) connect(dbName);
        int  result = 0;
        try(Statement updateStmt = connection.createStatement()) {
            result = updateStmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }




    public static void main(String[] args) throws IOException, RequestBadStatusException, JSONException {
        DBManager dbManager = new DBManager();
        String dbName = "blah.db";
        dbManager.createAccountInfoDB(dbName);

        int start_id = 31424800;
        int count = 100;
        int[] ids = new int[count];
        for (int i = 0; i < count; i++) {
            ids[i] = start_id + i;
        }
        Reader reader = GetJson.getJsonByURL(Config.getUrlAccountInfo(ids));

        JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        System.out.println(jsonObject);
        ParseJson parseJson = new ParseJson();

        List<AccountInfo> list = parseJson.parseBigJson(jsonObject, ids);

        for (AccountInfo ai : list) {
            dbManager.saveAccountInfo(ai, dbName);
        }


    }
}
