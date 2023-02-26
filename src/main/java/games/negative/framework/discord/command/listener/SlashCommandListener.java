package games.negative.framework.discord.command.listener;

import games.negative.framework.discord.command.ContextCommand;
import games.negative.framework.discord.command.SlashCommand;
import games.negative.framework.discord.command.map.CommandMap;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {

    private final CommandMap commandMap;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild != null) {
            // Attempt to execute server commands
            Collection<SlashCommand> serverCommands = commandMap.getServerCommands(guild.getId());
            if (!serverCommands.isEmpty()) {
                SlashCommand command = serverCommands.stream()
                        .filter(slashCommand -> slashCommand.getName().equalsIgnoreCase(event.getName()))
                        .findFirst().orElse(null);

                if (command != null) {
                    command.runCommand(event);
                    return;
                }
            }
        }

        // Attempt to execute global commands
        Collection<SlashCommand> globalCommands = commandMap.getGlobalCommands();
        globalCommands.stream()
                .filter(slashCommand -> slashCommand.getName().equalsIgnoreCase(event.getName()))
                .findFirst().ifPresent(slashCommand -> slashCommand.runCommand(event));
    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild != null) {
            Collection<ContextCommand> commands = commandMap.getServerContextCommands(guild.getId());
            ContextCommand command = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(event.getName())).findFirst().orElse(null);
            if (command != null) {
                command.onInteractMessage(event);
                return;
            }
        }

        Collection<ContextCommand> commands = commandMap.getGlobalContextCommands();
        ContextCommand command = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(event.getName())).findFirst().orElse(null);
        if (command == null)
            return;

        command.onInteractMessage(event);
    }

    @Override
    public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
        Guild guild = event.getGuild();
        if (guild != null) {
            Collection<ContextCommand> commands = commandMap.getServerContextCommands(guild.getId());
            ContextCommand command = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(event.getName())).findFirst().orElse(null);
            if (command != null) {
                command.onInteractUser(event);
                return;
            }
        }

        Collection<ContextCommand> commands = commandMap.getGlobalContextCommands();
        ContextCommand command = commands.stream().filter(cmd -> cmd.getName().equalsIgnoreCase(event.getName())).findFirst().orElse(null);
        if (command == null)
            return;

        command.onInteractUser(event);
    }
}
