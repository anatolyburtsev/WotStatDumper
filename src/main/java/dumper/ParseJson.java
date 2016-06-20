package dumper;

import Exceptions.RequestBadStatusException;
import com.google.gson.*;
import dumper.AccountInfo.AccountInfo;
import org.json.JSONException;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onotole on 10.06.16.
 */
public class ParseJson {
    Gson gson = null;

    public ParseJson() {
        gson = new GsonBuilder().create();
    }

    public <T extends JsonElement> AccountInfo parseJson(T jsonObject) {
        // уже обрезанный, json[id]

        AccountInfo accountInfo = gson.fromJson(jsonObject, AccountInfo.class);

        return accountInfo;
    }

    public AccountInfo parseJson(Reader reader) {
        return parseJson(new JsonParser().parse(reader));
    }

    public List<AccountInfo> parseBigJson(Reader reader) {
        return (List<AccountInfo>) parseJson(new JsonParser().parse(reader));
    }

    public List<AccountInfo> parseBigJson(JsonObject jsonObject, int[] ids) throws RequestBadStatusException, JSONException {
        if (! jsonObject.get("status").getAsString().equals("ok")) {
            System.err.println(jsonObject);
            Main.LOG.error("Failed with id: " + ids[0]);
            Main.queue2Repeat.offer(ids[0]);
            Main.failedCounter++;
            throw new RequestBadStatusException();
        }

        JsonObject jsonMap = jsonObject.get("data").getAsJsonObject();
        JsonObject oneUserData = null;
        List<AccountInfo> list = new ArrayList<>();

        for (int i: ids) {

            try {
                oneUserData = jsonMap.get(String.valueOf(i)).getAsJsonObject();
            } catch (IllegalStateException e) {
                // it's not existing user
                oneUserData = null;
            } catch (NullPointerException e) {
                // is's not existing user
            }
            if (oneUserData != null) {
                AccountInfo ai = gson.fromJson(oneUserData, AccountInfo.class);
                list.add(ai);
            }
        }
        return list;
    }

    public List<AccountInfo> parseBigJsonStartFromId(JsonObject jsonObject, int startId, int count) throws RequestBadStatusException, JSONException {
        int[] ids = new int[count];
        for (int i = 0; i < count; i++) {
            ids[i] = startId + i;
        }
        return parseBigJson(jsonObject, ids);
    }

    public static void main(String[] args) throws IOException, RequestBadStatusException, JSONException {

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

        parseJson.parseBigJson(jsonObject, ids);
    }
}
