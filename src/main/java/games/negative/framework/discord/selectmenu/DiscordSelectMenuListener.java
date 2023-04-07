package games.negative.framework.discord.selectmenu;

import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.EntitySelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DiscordSelectMenuListener extends ListenerAdapter {

    private final DiscordSelectMenuRegistry registry;

    public DiscordSelectMenuListener() {
        this.registry = DiscordSelectMenuRegistry.getInstance();
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        StringSelectMenu menu = event.getSelectMenu();
        String id = menu.getId();
        if (id == null)
            return;

        DiscordSelectMenu selectMenu = registry.getMenus().stream().filter(entry -> {
            SelectMenu entryMenu = entry.getMenu();
            if (entryMenu == null || entryMenu.getId() == null)
                return false;

            return entryMenu.getId().equalsIgnoreCase(id);
        }).findFirst().orElse(null);

        if (selectMenu == null)
            return;

        if (!(selectMenu instanceof DiscordStringSelectMenu stringMenu))
            return;

        Map<SelectOption, Consumer<StringSelectInteractionEvent>> options = stringMenu.getSelectOptions();
        List<SelectOption> selected = event.getSelectedOptions();

        for (SelectOption option : selected) {
            Consumer<StringSelectInteractionEvent> consumer = options.get(option);
            if (consumer != null) consumer.accept(event);
        }
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        EntitySelectMenu menu = event.getSelectMenu();
        String id = menu.getId();
        if (id == null)
            return;

        DiscordSelectMenu selectMenu = registry.getMenus().stream().filter(entry -> {
            SelectMenu entryMenu = entry.getMenu();
            if (entryMenu == null || entryMenu.getId() == null)
                return false;

            return entryMenu.getId().equalsIgnoreCase(id);
        }).findFirst().orElse(null);

        if (selectMenu == null)
            return;

        if (!(selectMenu instanceof DiscordEntitySelectMenu entityMenu))
            return;

        entityMenu.getEvent().accept(menu, event);
    }
}
