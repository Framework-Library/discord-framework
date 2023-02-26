package games.negative.framework.discord.command;

import games.negative.framework.discord.util.Validate;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

import java.util.function.Consumer;

@Getter @Setter
public abstract class ContextCommand {

    private final String name;
    private final Command.Type type;
    private Consumer<CommandData> data;
    public ContextCommand() {
        Validate.isTrue(this.getClass().isAnnotationPresent(ContextInfo.class), "ContextCommand '" + this.getClass().getSimpleName() + "'must have a ContextInfo annotation");

        ContextInfo info = getClass().getAnnotation(ContextInfo.class);
        this.name = info.name();
        this.type = info.type();
    }

    public abstract void onInteractMessage(MessageContextInteractionEvent event);

    public abstract void onInteractUser(UserContextInteractionEvent event);

}
