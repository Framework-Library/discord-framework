package games.negative.framework.discord.selectmenu;

import com.google.common.collect.Lists;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;

import java.util.List;
import java.util.function.BiConsumer;

public class DiscordEntitySelectMenu implements DiscordSelectMenu {

    private final String id;
    private final List<EntitySelectMenu.SelectTarget> targets;
    private final List<ChannelType> channelTypes;
    private String placeholder;
    private int min, max = 1;
    private EntitySelectMenu menu;
    private BiConsumer<EntitySelectMenu, EntitySelectInteractionEvent> event;

    public DiscordEntitySelectMenu(String id) {
        this.id = id;
        this.targets = Lists.newArrayList();
        this.channelTypes = Lists.newArrayList();

        DiscordSelectMenuRegistry.getInstance().registerMenu(this);
    }

    public DiscordEntitySelectMenu setTargets(EntitySelectMenu.SelectTarget... targets) {
        this.targets.clear();
        this.targets.addAll(Lists.newArrayList(targets));
        return this;
    }

    public DiscordEntitySelectMenu setChannelTypes(ChannelType... types) {
        this.channelTypes.clear();
        this.channelTypes.addAll(Lists.newArrayList(types));
        return this;
    }

    public DiscordEntitySelectMenu setRange(int min, int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public DiscordEntitySelectMenu setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    public EntitySelectMenu build() {
        EntitySelectMenu.Builder builder = EntitySelectMenu.create(id, targets);

        if (!channelTypes.isEmpty())
            builder.setChannelTypes(channelTypes);

        builder.setRequiredRange(min, max);
        builder.setPlaceholder(placeholder);

        menu = builder.build();
        return menu;
    }

    @Override
    public SelectMenu getMenu() {
        return menu;
    }

    public BiConsumer<EntitySelectMenu, EntitySelectInteractionEvent> getEvent() {
        return event;
    }
}
