package games.negative.framework.discord.config.json.model.embed;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class JsonEmbedDescription {

    private final List<String> text;


    public JsonEmbedDescription(String text) {
        this(Lists.newArrayList(text.split("\n")));
    }

    public JsonEmbedDescription(List<String> text) {
        this.text = text;
    }

    public String toText() {
        StringBuilder builder = new StringBuilder();
        for (String s : text) {
            builder.append(s).append("\n");
        }
        return builder.toString();
    }

}
