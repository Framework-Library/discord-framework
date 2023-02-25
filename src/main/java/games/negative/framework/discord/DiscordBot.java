package games.negative.framework.discord;

import games.negative.framework.discord.button.DiscordButtonListener;
import games.negative.framework.discord.button.DiscordButtonRegistry;
import games.negative.framework.discord.button.provider.DiscordButtonRegistryProvider;
import games.negative.framework.discord.command.SlashCommand;
import games.negative.framework.discord.command.SlashSubCommand;
import games.negative.framework.discord.command.listener.SlashCommandListener;
import games.negative.framework.discord.command.map.CommandMap;
import games.negative.framework.discord.command.map.provider.CommandMapProvider;
import games.negative.framework.discord.config.json.JSONConfigManager;
import games.negative.framework.discord.config.json.provider.JSONConfigManagerProvider;
import games.negative.framework.discord.modal.DiscordModalListener;
import games.negative.framework.discord.modal.DiscordModalRegistry;
import games.negative.framework.discord.modal.provider.DiscordModalRegistryProvider;
import games.negative.framework.discord.runnable.Scheduler;
import games.negative.framework.discord.runnable.provider.SchedulerProvider;
import games.negative.framework.discord.selectmenu.DiscordSelectMenuListener;
import games.negative.framework.discord.selectmenu.DiscordSelectMenuRegistry;
import games.negative.framework.discord.selectmenu.provider.DiscordSelectMenuRegistryProvider;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

@Getter
public abstract class DiscordBot {

    private static DiscordBot inst;
    private CommandMap commandMap;
    private Scheduler scheduler;
    private JSONConfigManager jsonConfigManager;
    private DiscordButtonRegistry buttonRegistry;
    private DiscordSelectMenuRegistry menuRegistry;
    private DiscordModalRegistry modalRegistry;

    public JDABuilder create(Collection<GatewayIntent> intents) {
        inst = this;
        JDABuilder builder = JDABuilder.create(intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder create(GatewayIntent intent, GatewayIntent... intents) {
        inst = this;
        JDABuilder builder = JDABuilder.create(intent, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder create(String token, Collection<GatewayIntent> intents) {
        inst = this;
        JDABuilder builder = JDABuilder.create(token, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder create(String token, GatewayIntent intent, GatewayIntent... intents) {
        inst = this;
        JDABuilder builder = JDABuilder.create(token, intent, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder createDefault(String token) {
        inst = this;
        JDABuilder builder = JDABuilder.createDefault(token);
        initialize(builder);
        return builder;
    }

    public JDABuilder createDefault(String token, Collection<GatewayIntent> intents) {
        inst = this;
        JDABuilder builder = JDABuilder.createDefault(token, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder createDefault(String token, GatewayIntent intent, GatewayIntent... intents) {
        inst = this;
        JDABuilder builder = JDABuilder.createDefault(token, intent, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder createLight(String token) {
        inst = this;
        JDABuilder builder = JDABuilder.createLight(token);
        initialize(builder);
        return builder;
    }

    public JDABuilder createLight(String token, Collection<GatewayIntent> intents) {
        inst = this;
        JDABuilder builder = JDABuilder.createLight(token, intents);
        initialize(builder);
        return builder;
    }

    public JDABuilder createLight(String token, GatewayIntent intent, GatewayIntent... intents) {
        inst = this;
        JDABuilder builder = JDABuilder.createLight(token, intent, intents);
        initialize(builder);
        return builder;
    }

    /**
     * Register a {@link SlashCommand} as a global command
     * @param command {@link SlashCommand} instance
     * @apiNote This may take up to an hour for Discord to register it!
     */
    public void registerGlobalCommand(@NotNull SlashCommand command) {
        commandMap.registerGlobalCommand(command.getName(), command);
    }

    /**
     * Register a {@link SlashCommand} as a server command
     * @param key {@link Guild} ID
     * @param command {@link SlashCommand} instance
     * @apiNote This should register almost instantly!
     */
    public void registerServerCommand(@NotNull String key, @NotNull SlashCommand command) {
        commandMap.registerServerCommand(key, command.getName(), command);
    }

    /**
     * Initalize all the commands in the {@link CommandMap} to Discord
     * @apiNote This should be called after {@link JDABuilder#build()#awaitReady()}
     * @param jda {@link JDA} instance
     */
    @SuppressWarnings("all")
    public void initializeCommands(@NotNull JDA jda) {
        // Global Commands
        Collection<SlashCommand> globalCommands = commandMap.getGlobalCommands();
        CommandListUpdateAction commands = jda.updateCommands();

        globalCommands.forEach(command -> {
            if (!command.getAliases().isEmpty()) {
                command.getAliases().forEach(name -> {
                    SlashCommandData commandData = new CommandDataImpl(name, command.getDescription());
                    Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                    for (SlashSubCommand cmd : command.getSubCommands()) {
                        for (String alias : cmd.getAliases()) {
                            SubcommandData subcommandData = new SubcommandData(alias, cmd.getDescription());
                            Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                            commandData.addSubcommands(subcommandData);
                        }

                        SubcommandData subcommandData = new SubcommandData(cmd.getName(), cmd.getDescription());
                        Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                    }
                    System.out.println("[Command Registry] Registered Global Command `" + commandData.getName() +"`");
                    commands.addCommands(commandData);
                });
            }

            SlashCommandData commandData = new CommandDataImpl(command.getName(), command.getDescription());
            Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
            for (SlashSubCommand cmd : command.getSubCommands()) {
                for (String alias : cmd.getAliases()) {
                    SubcommandData subcommandData = new SubcommandData(alias, cmd.getDescription());
                    Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                    commandData.addSubcommands(subcommandData);
                }

                SubcommandData subcommandData = new SubcommandData(cmd.getName(), cmd.getDescription());
                Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
            }
            System.out.println("[Command Registry] Registered Global Command `" + commandData.getName() +"`");
            commands.addCommands(commandData);
        });

        commands.queue();

        // Server Bound Commands
        commandMap.getAllServerCommands().entrySet().stream().filter(serverEntry -> jda.getGuildById(serverEntry.getKey()) != null).forEach(serverEntry -> {
            Guild guild = jda.getGuildById(serverEntry.getKey());
            assert guild != null;
            CommandListUpdateAction guildCommands = guild.updateCommands();

            Collection<SlashCommand> serverCommands = serverEntry.getValue();
            serverCommands.forEach(command -> {
                if (!command.getAliases().isEmpty()) {
                    command.getAliases().forEach(name -> {
                        SlashCommandData commandData = new CommandDataImpl(name, command.getDescription());
                        Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                        for (SlashSubCommand cmd : command.getSubCommands()) {
                            for (String alias : cmd.getAliases()) {
                                SubcommandData subcommandData = new SubcommandData(alias, cmd.getDescription());
                                Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                                commandData.addSubcommands(subcommandData);
                            }

                            SubcommandData subcommandData = new SubcommandData(cmd.getName(), cmd.getDescription());
                            Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                            commandData.addSubcommands(subcommandData);
                        }
                        System.out.println("[Command Registry] Registered Server Command `" + commandData.getName()
                                + "` to Guild `" + guild.getName() + "`");
                        guildCommands.addCommands(commandData);
                    });
                }

                SlashCommandData commandData = new CommandDataImpl(command.getName(), command.getDescription());
                Optional.ofNullable(command.getData()).ifPresent(data -> data.accept(commandData));
                for (SlashSubCommand cmd : command.getSubCommands()) {
                    for (String alias : cmd.getAliases()) {
                        SubcommandData subcommandData = new SubcommandData(alias, cmd.getDescription());
                        Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                        commandData.addSubcommands(subcommandData);
                    }

                    SubcommandData subcommandData = new SubcommandData(cmd.getName(), cmd.getDescription());
                    Optional.ofNullable(cmd.getData()).ifPresent(data -> data.accept(subcommandData));
                    commandData.addSubcommands(subcommandData);
                }

                System.out.println("[Command Registry] Registered Server Command `" + commandData.getName()
                        + "` to Guild `" + guild.getName() + "`");

                guildCommands.addCommands(commandData);
            });

            guildCommands.queue();

        });

    }


    private void initialize(JDABuilder builder) {
        this.commandMap = new CommandMapProvider();
        this.scheduler = new SchedulerProvider();
        this.jsonConfigManager = new JSONConfigManagerProvider();
        this.buttonRegistry = new DiscordButtonRegistryProvider();
        this.menuRegistry = new DiscordSelectMenuRegistryProvider();
        this.modalRegistry = new DiscordModalRegistryProvider();

        builder.addEventListeners(
                new SlashCommandListener(commandMap),
                new DiscordButtonListener(),
                new DiscordSelectMenuListener(),
                new DiscordModalListener()
        );
    }

    public static DiscordBot get() {
        return inst;
    }

}
