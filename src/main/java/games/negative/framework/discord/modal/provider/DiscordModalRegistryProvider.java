package games.negative.framework.discord.modal.provider;

import com.google.common.collect.Lists;
import games.negative.framework.discord.modal.DiscordModal;
import games.negative.framework.discord.modal.DiscordModalRegistry;

import java.util.List;

public class DiscordModalRegistryProvider extends DiscordModalRegistry {

    private final List<DiscordModal> modals;

    public DiscordModalRegistryProvider() {
        this.modals = Lists.newArrayList();
        setInstance(this);
    }


    @Override
    public void registerModal(DiscordModal modal) {
        this.modals.add(modal);
    }

    @Override
    public List<DiscordModal> getModals() {
        return modals;
    }
}
