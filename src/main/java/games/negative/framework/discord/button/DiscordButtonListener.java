package games.negative.framework.discord.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

public class DiscordButtonListener extends ListenerAdapter {

    private final DiscordButtonRegistry registry;

    public DiscordButtonListener() {
        this.registry = DiscordButtonRegistry.getInstance();
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        Button button = event.getButton();
        String id = button.getId();
        if (id == null)
            return;

        List<DiscordButton> buttons = registry.getButtons();
        DiscordButton customButton = buttons.stream().filter(discordButton -> {
            String buttonID = discordButton.getButton().getId();
            if (buttonID == null || button.isDisabled())
                return false;

            return buttonID.equalsIgnoreCase(id);
        }).findFirst().orElse(null);

        if (customButton == null)
            return;

        BiConsumer<Button, ButtonInteractionEvent> function = customButton.getEvent();
        function.accept(button, event);
    }
}
