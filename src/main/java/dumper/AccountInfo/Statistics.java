package dumper.AccountInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Statistics {

    @SerializedName("all")
    @Expose
    public All all;
    @SerializedName("frags")
    @Expose
    public Object frags;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}