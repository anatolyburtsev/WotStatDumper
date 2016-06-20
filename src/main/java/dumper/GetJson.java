package dumper;

import java.io.*;
import java.net.URL;

/**
 * Created by onotole on 10.06.16.
 */
public class GetJson {
    private static URL url = null;

    public static Reader getJsonByURL(String URL) throws IOException {
        url = new URL(URL);
        InputStream in = url.openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        return reader;
    }

    public static void main(String[] args) {

    }
}
