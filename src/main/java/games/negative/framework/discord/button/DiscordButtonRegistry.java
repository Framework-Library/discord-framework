package games.negative.framework.discord.button;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public abstract class DiscordButtonRegistry {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static DiscordButtonRegistry instance;

    public abstract void registerButton(DiscordButton button);

    public abstract List<DiscordButton> getButtons();
}
