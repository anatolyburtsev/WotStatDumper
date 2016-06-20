package dumper.AccountInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class All {

    @SerializedName("spotted")
    @Expose
    public Long spotted;
    @SerializedName("max_frags_tank_id")
    @Expose
    public Long maxFragsTankId;
    @SerializedName("hits")
    @Expose
    public Long hits;
    @SerializedName("frags")
    @Expose
    public Long frags;
    @SerializedName("max_xp")
    @Expose
    public Long maxXp;
    @SerializedName("max_xp_tank_id")
    @Expose
    public Long maxXpTankId;
    @SerializedName("wins")
    @Expose
    public Long wins;
    @SerializedName("losses")
    @Expose
    public Long losses;
    @SerializedName("capture_points")
    @Expose
    public Long capturePoints;
    @SerializedName("battles")
    @Expose
    public Long battles;
    @SerializedName("damage_dealt")
    @Expose
    public Long damageDealt;
    @SerializedName("damage_received")
    @Expose
    public Long damageReceived;
    @SerializedName("max_frags")
    @Expose
    public Long maxFrags;
    @SerializedName("shots")
    @Expose
    public Long shots;
    @SerializedName("frags8p")
    @Expose
    public Long frags8p;
    @SerializedName("xp")
    @Expose
    public Long xp;
    @SerializedName("win_and_survived")
    @Expose
    public Long winAndSurvived;
    @SerializedName("survived_battles")
    @Expose
    public Long survivedBattles;
    @SerializedName("dropped_capture_points")
    @Expose
    public Long droppedCapturePoints;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
