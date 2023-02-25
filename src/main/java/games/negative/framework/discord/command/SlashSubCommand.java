package games.negative.framework.discord.command;

import com.google.common.collect.Lists;
import games.negative.framework.discord.util.Validate;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;

import java.util.List;
import java.util.function.Consumer;

@Getter
@Setter
public abstract class SlashSubCommand {

    private final String name;
    private final String description;
    private List<String> aliases;
    private Consumer<SubcommandData> data;

    public SlashSubCommand() {
        Validate.isTrue(this.getClass().isAnnotationPresent(SlashInfo.class), "SlashCommand '" + this.getClass().getSimpleName() + "'must have a SlashInfo annotation");

        this.aliases = Lists.newArrayList();

        SlashInfo info = getClass().getAnnotation(SlashInfo.class);
        this.name = info.name();
        this.description = info.description();
        if (!info.args()[0].isEmpty())
            this.aliases = Lists.newArrayList(info.args());

    }

    public abstract void onCommand(SlashCommandInteractionEvent event);
}
