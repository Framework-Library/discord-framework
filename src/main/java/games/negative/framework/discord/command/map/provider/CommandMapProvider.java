package games.negative.framework.discord.command.map.provider;

import com.google.common.collect.Maps;
import games.negative.framework.discord.command.SlashCommand;
import games.negative.framework.discord.command.map.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandMapProvider implements CommandMap {

    private final HashMap<String, SlashCommand> globalCommands;
    private final HashMap<String, HashMap<String, SlashCommand>> serverCommands;

    public CommandMapProvider() {
        this.globalCommands = Maps.newHashMap();
        this.serverCommands = Maps.newHashMap();
    }

    @Override
    public void registerGlobalCommand(@NotNull String name, @NotNull SlashCommand command) {
        globalCommands.putIfAbsent(name, command);
    }

    @Override
    public void registerServerCommand(@NotNull String serverID, @NotNull String name, @NotNull SlashCommand command) {
        HashMap<String, SlashCommand> serverCommandMap = serverCommands.get(serverID);
        if (serverCommandMap == null)
            serverCommandMap = Maps.newHashMap();

        serverCommandMap.putIfAbsent(name, command);

        if (serverCommands.containsKey(serverID))
            serverCommands.replace(serverID, serverCommandMap);
        else
            serverCommands.put(serverID, serverCommandMap);
    }

    @Override
    public @NotNull Collection<SlashCommand> getGlobalCommands() {
        return globalCommands.values();
    }

    @Override
    public @NotNull Collection<SlashCommand> getServerCommands(@NotNull String serverID) {
        Optional<Map.Entry<String, HashMap<String, SlashCommand>>> first = serverCommands.entrySet().stream()
                .filter(commandEntry -> commandEntry.getKey().equalsIgnoreCase(serverID)).findFirst();

        if (!first.isPresent())
            return Collections.emptyList();

        Map.Entry<String, HashMap<String, SlashCommand>> commandEntries = first.get();
        return commandEntries.getValue().values();
    }

    @Override
    public HashMap<String, Collection<SlashCommand>> getAllServerCommands() {
        HashMap<String, Collection<SlashCommand>> allCommands = new HashMap<>();
        serverCommands.forEach((label, commands) -> allCommands.putIfAbsent(label, commands.values()));
        return allCommands;
    }
}
