package games.negative.framework.discord.selectmenu;

import games.negative.framework.discord.button.DiscordButton;
import games.negative.framework.discord.button.DiscordButtonRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class DiscordSelectMenuRegistry {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static DiscordSelectMenuRegistry instance;

    public abstract void registerMenu(DiscordSelectMenu menu);

    public abstract List<DiscordSelectMenu> getMenus();

}
