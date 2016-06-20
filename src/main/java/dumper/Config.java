package dumper;

import java.util.List;

/**
 * Created by onotole on 10.06.16.
 */
public class Config {
    private final static String application_id = "77c7c785ff539689b53b8757553c852";
    private final static String host = "http://api.wotblitz.ru/wotb/";
    private final static String accountInfo = "account/info/";
    private final static String finalAccountInfo = new StringBuilder().append(host).append(accountInfo)
            .append("?application_id=").append(application_id).append("&account_id=").toString();

    public static String getUrlAccountInfo() {
        return finalAccountInfo;
    }

    public static String getUrlAccountInfo(String id) {
        return String.format("%s%s", finalAccountInfo, id);
    }

    public static String getUrlAccountInfo(List<String> id) {
        return String.format("%s%s", finalAccountInfo, String.join(",", id));
    }

    public static String getUrlAccountInfo(Integer startId, Integer size) {
        StringBuilder result = new StringBuilder();
        result.append(finalAccountInfo);
        for (int i = 0; i < size - 1; i++) {
            result.append( startId + i );
            result.append(",");
        }
        result.append(startId + size);

        return result.toString();
    }

    public static String getUrlAccountInfo(int[] ids) {
        StringBuilder result = new StringBuilder();
        result.append(finalAccountInfo);
        for (int i = 0; i < ids.length - 1; i++) {
            result.append( String.valueOf(ids[i]) );
            result.append(",");
        }
        result.append(ids[ids.length-1]);

        return result.toString();
    }
}
