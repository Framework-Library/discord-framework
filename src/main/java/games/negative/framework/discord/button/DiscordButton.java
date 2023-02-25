package games.negative.framework.discord.button;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.function.BiConsumer;

public class DiscordButton {

    private final Button button;
    private final BiConsumer<Button, ButtonInteractionEvent> event;

    public DiscordButton(Button button, BiConsumer<Button, ButtonInteractionEvent> event) {
        this.button = button;
        this.event = event;

        DiscordButtonRegistry.getInstance().registerButton(this);
    }

    public Button getButton() {
        return button;
    }

    public BiConsumer<Button, ButtonInteractionEvent> getEvent() {
        return event;
    }
}
