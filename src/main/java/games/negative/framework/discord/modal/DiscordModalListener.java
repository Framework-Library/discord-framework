package games.negative.framework.discord.modal;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

public class DiscordModalListener extends ListenerAdapter {

    private final DiscordModalRegistry registry;

    public DiscordModalListener() {
        this.registry = DiscordModalRegistry.getInstance();
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        String id = event.getModalId();

        DiscordModal modal = registry.getModals().stream().filter(discordModal -> {
            Modal entry = discordModal.getModal();
            if (entry == null)
                return false;

            return entry.getId().equalsIgnoreCase(id);
        }).findFirst().orElse(null);

        if (modal == null)
            return;

        modal.getEvent().accept(modal.getModal(), event);
    }
}
