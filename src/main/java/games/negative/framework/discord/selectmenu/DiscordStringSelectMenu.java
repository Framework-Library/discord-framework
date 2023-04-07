package games.negative.framework.discord.selectmenu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DiscordStringSelectMenu implements DiscordSelectMenu {

    private final String id;
    @Deprecated
    private final List<SelectOption> options;
    private StringSelectMenu menu;
    private String placeholder;
    private int min, max = 1;
    @Deprecated
    private BiConsumer<StringSelectMenu, StringSelectInteractionEvent> event;
    private final Map<SelectOption, Consumer<StringSelectInteractionEvent>> selectOptions;
    public DiscordStringSelectMenu(@NotNull String id) {
        this.id = id;
        this.options = Lists.newArrayList();
        this.selectOptions = Maps.newHashMap();
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

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable String description, @Nullable Emoji emoji, @Nullable Consumer<StringSelectInteractionEvent> event) {
        SelectOption option = SelectOption.of(label, value);
        if (description != null)
            option = option.withDescription(description);

        if (emoji != null)
            option = option.withEmoji(emoji);

        options.add(option);
        this.selectOptions.put(option, event);
        return this;
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable String description, @Nullable Consumer<StringSelectInteractionEvent> event) {
        return addOption(label, value, description, null, event);
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable Emoji emoji, @Nullable Consumer<StringSelectInteractionEvent> event) {
        return addOption(label, value, null, emoji, event);
    }

    public DiscordStringSelectMenu addOption(@NotNull String label, @NotNull String value, @Nullable Consumer<StringSelectInteractionEvent> event) {
        return addOption(label, value, null, null, event);
    }

    @Deprecated
    public DiscordStringSelectMenu setEvent(@NotNull BiConsumer<StringSelectMenu, StringSelectInteractionEvent> event) {
        this.event = event;
        return this;
    }

    public StringSelectMenu build() {
        StringSelectMenu.Builder builder = StringSelectMenu.create(id);
        builder.addOptions(selectOptions.keySet());
        builder.setPlaceholder(placeholder);
        builder.setRequiredRange(min, max);

        menu = builder.build();
        return menu;
    }

    public Map<SelectOption, Consumer<StringSelectInteractionEvent>> getSelectOptions() {
        return selectOptions;
    }

    @Override
    public SelectMenu getMenu() {
        return menu;
    }

    @Deprecated
    public BiConsumer<StringSelectMenu, StringSelectInteractionEvent> getEvent() {
        return event;
    }
}
