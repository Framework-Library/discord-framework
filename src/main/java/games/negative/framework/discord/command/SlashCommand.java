package games.negative.framework.discord.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import games.negative.framework.discord.DiscordBot;
import games.negative.framework.discord.runnable.RepeatingRunnable;
import games.negative.framework.discord.util.Validate;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Setter
public abstract class SlashCommand {

    private final String name;
    private final String description;
    private List<String> aliases;
    private Consumer<SlashCommandData> data;
    private List<SlashSubCommand> subCommands;
    private HashMap<String, Long> cooldowns;
    private Function<SlashCommandInteractionEvent, Long> cooldownFunction;

    public SlashCommand() {
        Validate.isTrue(this.getClass().isAnnotationPresent(SlashInfo.class), "SlashCommand '" + this.getClass().getSimpleName() + "'must have a SlashInfo annotation");

        this.aliases = Lists.newArrayList();

        SlashInfo info = getClass().getAnnotation(SlashInfo.class);
        this.name = info.name();
        this.description = info.description();
        if (!info.args()[0].isEmpty())
            this.aliases = Lists.newArrayList(info.args());

        subCommands = Lists.newArrayList();

        this.cooldowns = Maps.newHashMap();
        DiscordBot.get().getScheduler().runAsync(new SlashCommandCooldownRunnable(), 0, 1000L * 30);
    }

    public void runCommand(SlashCommandInteractionEvent event) {
        if (cooldownFunction != null) {
            String id = event.getUser().getId();
            if (cooldowns.containsKey(id) && cooldowns.get(id) > System.currentTimeMillis()) {
                event.reply("This command is currently on cooldown").setEphemeral(true).queue();
                // Maybe make this a configurable / add a time until cooldown ends message
                return;
            }
            long duration = (System.currentTimeMillis() + cooldownFunction.apply(event));
            cooldowns.put(id, duration);
        }

        if (event.getSubcommandName() != null) {
            for (SlashSubCommand cmd : subCommands) {
                if (cmd.getName().equalsIgnoreCase(event.getSubcommandName())) {
                    cmd.onCommand(event);
                    return;
                }
            }
        }
        onCommand(event);
    }

    public abstract void onCommand(SlashCommandInteractionEvent event);

    public void addSubCommand(SlashSubCommand subCommand) {
        subCommands.add(subCommand);
    }

    public void addSubCommands(SlashSubCommand... commands) {
        for (SlashSubCommand command : commands) {
            addSubCommand(command);
        }
    }
    private class SlashCommandCooldownRunnable implements RepeatingRunnable {

        @Override
        public void execute() {
            List<String> toRemove = Lists.newArrayList();
            for (Map.Entry<String, Long> entry : cooldowns.entrySet()) {
                if (System.currentTimeMillis() >= entry.getValue()) {
                    toRemove.add(entry.getKey());
                }
            }

            toRemove.forEach(cooldowns::remove);
        }
    }
}
