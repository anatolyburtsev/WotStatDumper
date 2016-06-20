package dumper.MultiProcessing;

import Exceptions.RequestBadStatusException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dumper.AccountInfo.AccountInfo;
import dumper.Config;
import dumper.DBManager;
import dumper.GetJson;
import dumper.ParseJson;
import org.json.JSONException;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by onotole on 10.06.16.
 */
public class Consumer implements Runnable {
    private BlockingQueue<Integer>  queue;
    private String                  dbName;
    private Integer startId;
    private Integer                 step;
    private ParseJson               parseJson;
    private DBManager               dbManager;

    public Consumer(BlockingQueue<Integer> queue, String dbName, Integer step) {
        this.queue  = queue;
        this.dbName = dbName;
        this.step   = step;
        parseJson = new ParseJson();
        dbManager = new DBManager();
        dbManager.createAccountInfoDB(dbName);
        dbManager.clearDB(dbName);
    }

    @Override
    public void run() {
        try {
            while ( ! Thread.currentThread().isInterrupted() &&  (startId = queue.take()) != null) {
//                System.out.println(Thread.currentThread().getName() + " startId = " + startId);

                Reader reader = GetJson.getJsonByURL(Config.getUrlAccountInfo(startId, step));

                JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
                List<AccountInfo> list = parseJson.parseBigJsonStartFromId(jsonObject, startId, step);
                for (AccountInfo ai : list) {
                    dbManager.saveAccountInfo(ai, dbName);
                }
            }
        } catch (InterruptedException e) {
            // finish
            dbManager.close();
            e.printStackTrace();
        } catch (IOException e) {
            dbManager.close();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (RequestBadStatusException e) {
            e.printStackTrace();
        }
    }
}
