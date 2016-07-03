package dumper;

/**
 * Created by onotole on 25.06.16.
 */
public class Init {

    private static DBManager dbManager = new DBManager();


    public static void init() {
        String name="wotb.db";
        dbManager.createAccountInfoDB(name);
        dbManager.clearDB("WOTB");
        dbManager.close();
    }


}
