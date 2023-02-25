package games.negative.framework.discord.modal;

import com.google.common.collect.Lists;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiConsumer;

public class DiscordModal {

    private final String key;
    private final String title;
    private Modal modal;
    private final List<TextInput> inputs;
    private BiConsumer<Modal, ModalInteractionEvent> event;

    public DiscordModal(@NotNull String key, @NotNull String title) {
        this.key = key;
        this.title = title;
        this.inputs = Lists.newArrayList();

        DiscordModalRegistry.getInstance().registerModal(this);
    }

    public DiscordModal setEvent(BiConsumer<Modal, ModalInteractionEvent> event) {
        this.event = event;
        return this;
    }


    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style) {
        return addTextInput(key, label, style, null, false, 0, 0);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, @Nullable String placeholder) {
        return addTextInput(key, label, style, placeholder, false, 0, 0);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, boolean required) {
        return addTextInput(key, label, style, null, required, 0, 0);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, int min, int max) {
        return addTextInput(key, label, style, null, false, min, max);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, @Nullable String placeholder, boolean required) {
        return addTextInput(key, label, style, placeholder, required, 0, 0);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, @Nullable String placeholder, int min, int max) {
        return addTextInput(key, label, style, placeholder, false, min, max);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, boolean required, int min, int max) {
        return addTextInput(key, label, style, null, required, min, max);
    }

    public DiscordModal addTextInput(@NotNull String key, @NotNull String label, @NotNull TextInputStyle style, @Nullable String placeholder, boolean required, int min, int max) {
        TextInput.Builder builder = TextInput.create(key, label, style);

        if (placeholder != null)
            builder.setPlaceholder(placeholder);

        if (min > 0)
            builder.setMinLength(min);

        if (max > 0)
            builder.setMaxLength(max);

        builder.setRequired(required);

        inputs.add(builder.build());
        return this;
    }

    public Modal build() {
        Modal.Builder builder = Modal.create(key, title);

        for (TextInput input : inputs) {
            builder.addActionRow(input);
        }

        modal = builder.build();
        return modal;
    }

    public BiConsumer<Modal, ModalInteractionEvent> getEvent() {
        return event;
    }

    public Modal getModal() {
        return modal;
    }

}
