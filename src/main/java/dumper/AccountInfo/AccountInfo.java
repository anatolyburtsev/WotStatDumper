package dumper.AccountInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AccountInfo {

    @SerializedName("statistics")
    @Expose
    public Statistics statistics;
    @SerializedName("account_id")
    @Expose
    public Long accountId;
    @SerializedName("created_at")
    @Expose
    public Long createdAt;
    @SerializedName("updated_at")
    @Expose
    public Long updatedAt;
    @SerializedName("private")
    @Expose
    public Object _private;
    @SerializedName("last_battle_time")
    @Expose
    public Long lastBattleTime;
    @SerializedName("nickname")
    @Expose
    public String nickname;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
