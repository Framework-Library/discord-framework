package games.negative.framework.discord.modal;

import games.negative.framework.discord.button.DiscordButton;
import games.negative.framework.discord.button.DiscordButtonRegistry;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class DiscordModalRegistry {


    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static DiscordModalRegistry instance;

    public abstract void registerModal(DiscordModal modal);

    public abstract List<DiscordModal> getModals();

}
