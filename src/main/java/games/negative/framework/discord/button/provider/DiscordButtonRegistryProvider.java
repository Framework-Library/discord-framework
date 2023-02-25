package games.negative.framework.discord.button.provider;

import com.google.common.collect.Lists;
import games.negative.framework.discord.button.DiscordButton;
import games.negative.framework.discord.button.DiscordButtonRegistry;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class DiscordButtonRegistryProvider extends DiscordButtonRegistry {

    private final List<DiscordButton> buttons;

    public DiscordButtonRegistryProvider() {
        setInstance(this);
        this.buttons = Lists.newArrayList();
    }

    @Override
    public void registerButton(DiscordButton button) {
        this.buttons.add(button);
    }

    @Override
    public List<DiscordButton> getButtons() {
        return buttons;
    }
}
