package games.negative.framework.discord.command.map;

import com.google.common.collect.Multimap;
import games.negative.framework.discord.command.ContextCommand;
import games.negative.framework.discord.command.SlashCommand;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface CommandMap {

    /**
     * Register a Global Command
     * @param name {@link SlashCommand} Name
     * @param command {@link SlashCommand} instance
     */
    void registerGlobalCommand(@NotNull String name, @NotNull SlashCommand command);

    /**
     * Register a {@link SlashCommand} to a {@link Guild}
     * @param serverID ID ({@link String}) of the {@link Guild}
     * @param name {@link SlashCommand} name
     * @param command {@link SlashCommand} instance
     */
    void registerServerCommand(@NotNull String serverID, @NotNull String name, @NotNull SlashCommand command);

    @NotNull
    Collection<SlashCommand> getGlobalCommands();

    @NotNull
    Collection<SlashCommand> getServerCommands(@NotNull String serverID);

    HashMap<String, Collection<SlashCommand>> getAllServerCommands();

    void registerServerContextCommand(@NotNull String serverID, @NotNull ContextCommand command);

    void registerGlobalContextCommand(@NotNull ContextCommand command);

    @NotNull
    Collection<ContextCommand> getGlobalContextCommands();

    @NotNull
    Collection<ContextCommand> getServerContextCommands(@NotNull String serverID);

    @NotNull
    Multimap<String, ContextCommand> getAllServerContextCommands();
}
