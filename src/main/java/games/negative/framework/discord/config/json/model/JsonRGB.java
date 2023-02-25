package games.negative.framework.discord.config.json.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

@Data
public class JsonRGB {

    @SerializedName("red")
    private final int red;

    @SerializedName("green")
    private final int green;

    @SerializedName("blue")
    private final int blue;

    public Color toColor() {
        return new Color(red, green, blue);
    }

    public static JsonRGB fromColor(@Nullable Color color) {
        return (color == null ? new JsonRGB(0, 0, 0) : new JsonRGB(color.getRed(), color.getGreen(), color.getBlue()));
    }
}
