package games.negative.framework.discord.config.json.model.embed;

import lombok.Data;

@Data
public class JsonEmbedField {

    private final String name;
    private final String value;
    private final boolean inline;

    public JsonEmbedField(String name, String value, boolean inline) {
        this.name = name;
        this.value = value;
        this.inline = inline;
    }

}
