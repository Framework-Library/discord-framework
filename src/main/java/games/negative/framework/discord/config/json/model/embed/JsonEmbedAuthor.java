package games.negative.framework.discord.config.json.model.embed;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JsonEmbedAuthor {

    private String name;
    private String url;
    private String iconUrl;

}
