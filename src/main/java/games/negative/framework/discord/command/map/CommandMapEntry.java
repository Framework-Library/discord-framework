package games.negative.framework.discord.command.map;

import games.negative.framework.discord.command.SlashCommand;
import lombok.Data;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Collection;

@Data
public class CommandMapEntry {

    private final String id;
    private final Guild guild;
    private final Collection<SlashCommand> commands;
}
