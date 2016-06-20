package dumper;

import dumper.AccountInfo.AccountInfo;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by onotole on 10.06.16.
 */
public class ParseJsonTest {
    ParseJson parseJson = null;
    @Before
    public void setup() {
        parseJson = new ParseJson();
    }

    @Test
    public void parseJson() throws Exception {
        Reader reader = new FileReader("/Users/onotole/IdeaProjects/WotStatDumper/" +
                "src/main/resources/testJson/accountInfo1.json");
        AccountInfo accountInfo = parseJson.parseJson(reader);
        assertThat(accountInfo.statistics.all.damageReceived, is(18037L));
    }

}