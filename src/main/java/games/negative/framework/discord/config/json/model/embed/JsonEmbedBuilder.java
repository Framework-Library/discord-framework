package games.negative.framework.discord.config.json.model.embed;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import games.negative.framework.discord.config.json.model.JsonRGB;
import lombok.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@Data
public class JsonEmbedBuilder {

    @Nullable
    @SerializedName("title")
    private JsonEmbedTitle title = new JsonEmbedTitle(
            "My Title",
            "https://google.com"
    );

    @Nullable
    @SerializedName("description")
    private JsonEmbedDescription description = new JsonEmbedDescription(
            Collections.singletonList("My Description")
    );

    @Nullable
    @SerializedName("color")
    private JsonRGB color = new JsonRGB(
            255,
            255,
            255
    );

    @Nullable
    @SerializedName("footer")
    private JsonEmbedFooter footer = new JsonEmbedFooter(
            "My Footer",
            "https://google.com"
    );

    @Nullable
    @SerializedName("thumbnail")
    private String thumbnailUrl;

    @Nullable
    @SerializedName("image")
    private String imageUrl;

    @Nullable
    @SerializedName("author")
    private JsonEmbedAuthor author = new JsonEmbedAuthor(
            "My Author",
            "https://google.com",
            "https://google.com"
    );

    private List<JsonEmbedField> fields = Lists.newArrayList(new JsonEmbedField(
            "My Field",
            "My Field Value",
            false
    ));

    public EmbedBuilder toEmbedBuilder() {
        EmbedBuilder builder = new EmbedBuilder();

        if (title != null)
            builder.setTitle(title.getText(), title.getUrl());

        if (description != null)
            builder.setDescription(description.toText());

        if (color != null)
            builder.setColor(color.toColor());

        if (footer != null)
            builder.setFooter(footer.getText(), footer.getIconUrl());

        if (thumbnailUrl != null)
            builder.setThumbnail(thumbnailUrl);

        if (imageUrl != null)
            builder.setImage(imageUrl);

        if (author != null)
            builder.setAuthor(author.getName(), author.getUrl(), author.getIconUrl());

        for (JsonEmbedField field : fields) {
            builder.addField(field.getName(), field.getValue(), field.isInline());
        }

        return builder;
    }

    public void addField(JsonEmbedField field) {
        fields.add(field);
    }

    public static JsonEmbedBuilder fromEmbedBuilder(EmbedBuilder builder) {
        MessageEmbed embed = builder.build();
        JsonEmbedBuilder jsonBuilder = new JsonEmbedBuilder();

        jsonBuilder.setTitle(new JsonEmbedTitle(
                embed.getTitle(),
                embed.getUrl()
        ));

        if (embed.getDescription() != null)
            jsonBuilder.setDescription(new JsonEmbedDescription(embed.getDescription()));

        jsonBuilder.setColor(JsonRGB.fromColor(embed.getColor()));

        MessageEmbed.Footer embedFooter = embed.getFooter();
        if (embedFooter != null) {
            jsonBuilder.setFooter(new JsonEmbedFooter(
                    embedFooter.getText(),
                    embedFooter.getIconUrl()
            ));
        }

        MessageEmbed.Thumbnail thumbnail = embed.getThumbnail();
        if (thumbnail != null)
            jsonBuilder.setThumbnailUrl(thumbnail.getUrl());

        MessageEmbed.ImageInfo image = embed.getImage();
        if (image != null)
            jsonBuilder.setImageUrl(image.getUrl());

        MessageEmbed.AuthorInfo author = embed.getAuthor();
        if (author != null) {
            jsonBuilder.setAuthor(new JsonEmbedAuthor(
                    author.getName(),
                    author.getUrl(),
                    author.getIconUrl()
            ));
        }

        for (MessageEmbed.Field field : embed.getFields()) {
            jsonBuilder.addField(new JsonEmbedField(
                    field.getName(),
                    field.getValue(),
                    field.isInline()
            ));
        }

        return jsonBuilder;
    }

}
