package games.negative.framework.discord.selectmenu.provider;

import com.google.common.collect.Lists;
import games.negative.framework.discord.selectmenu.DiscordSelectMenu;
import games.negative.framework.discord.selectmenu.DiscordSelectMenuRegistry;

import java.util.List;

public class DiscordSelectMenuRegistryProvider extends DiscordSelectMenuRegistry {

    private final List<DiscordSelectMenu> menus;

    public DiscordSelectMenuRegistryProvider() {
        setInstance(this);
        this.menus = Lists.newArrayList();
    }

    @Override
    public void registerMenu(DiscordSelectMenu menu) {
        this.menus.add(menu);
    }

    @Override
    public List<DiscordSelectMenu> getMenus() {
        return menus;
    }
}
