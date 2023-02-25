package games.negative.framework.discord.selectmenu;

import com.google.common.collect.Lists;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class DiscordStringSelectMenu implements DiscordSelectMenu {

    private final String id;
    private final List<SelectOption> options;
    private StringSelectMenu menu;
    private String placeholder;
    private int min, max = 1;
    private BiConsumer<StringSelectMenu, StringSelectInteractionEvent> event;

    public DiscordStringSelectMenu(@NotNull String id) {
        this.id = id;
        this.options = Lists.newArrayList();
        DiscordSelectMenuRegistry.getInstance().registerMenu(this);
    }

    public DiscordStringSelectMenu setRange(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public DiscordStringSelectMenu setPlaceholder(@NotNull String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable String description, @Nullable Emoji emoji) {
        SelectOption option = SelectOption.of(label, value);
        if (description != null)
            option = option.withDescription(description);

        if (emoji != null)
            option = option.withEmoji(emoji);

        options.add(option);
        return this;
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable String description) {
        return addOption(label, value, description, null);
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable Emoji emoji) {
        return addOption(label, value, null, emoji);
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value) {
        return addOption(label, value, null, null);
    }

    public DiscordStringSelectMenu setEvent(@NotNull BiConsumer<StringSelectMenu, StringSelectInteractionEvent> event) {
        this.event = event;
        return this;
    }

    public StringSelectMenu build() {
        StringSelectMenu.Builder builder = StringSelectMenu.create(id);
        builder.addOptions(options);
        builder.setPlaceholder(placeholder);
        builder.setRequiredRange(min, max);

        menu = builder.build();
        return menu;
    }

    @Override
    public SelectMenu getMenu() {
        return menu;
    }

    public BiConsumer<StringSelectMenu, StringSelectInteractionEvent> getEvent() {
        return event;
    }
}
