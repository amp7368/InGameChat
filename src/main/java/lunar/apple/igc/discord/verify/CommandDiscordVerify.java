package lunar.apple.igc.discord.verify;

import apple.discord.acd.ACD;
import apple.discord.acd.slash.base.ACDBaseCommand;
import apple.discord.acd.slash.base.ACDSlashCommand;
import apple.discord.acd.slash.options.SlashOptionDefault;
import apple.discord.acd.slash.runner.ACDSlashMethodCommand;
import apple.discord.acd.util.MessageFactory;
import lunar.apple.igc.discord.player.VerifiedPlayer;
import lunar.apple.igc.discord.player.VerifiedPlayersDatabase;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@ACDBaseCommand(alias = "verify", description = "Link/verify your discord and minecraft accounts")
public class CommandDiscordVerify extends ACDSlashCommand implements MessageFactory {
    public CommandDiscordVerify(ACD acd) {
        super(acd);
    }

    @ACDSlashMethodCommand
    public void verify(SlashCommandInteractionEvent event, @SlashOptionDefault(optionType = OptionType.STRING, name = "minecraft_ign", description = "Your minecraft username") String username) {
        @Nullable Player player = Bukkit.getPlayer(username);
        if (player == null) {
            event.reply("Please link a valid online player!").queue();
            return;
        }
        User discord = event.getUser();
        UUID confirmationId = VerifiedPlayersDatabase.get().process(new VerifiedPlayer(player, discord));
        player.sendMessage(createConfirmationMessage(confirmationId, discord.getName(), discord.getDiscriminator()));
        event.reply("Please validate by confirming the message sent in-game.").queue();
    }

    private Component createConfirmationMessage(UUID confirmationId, String name, String discriminator) {
        TextColor fillerColor = TextColor.color(189, 189, 189);
        TextColor confirmColor = TextColor.color(50, 200, 50);
        TextColor denyColor = TextColor.color(255, 100, 100);
        TextColor discordColor = TextColor.color(125, 125, 200);

        TextComponent discordText = Component.text(String.format("%s#%s", name, discriminator)).color(discordColor);

        Component message = discordSeparator("Confirm that ", discordText, " is your discord\n", fillerColor);
        Component confirmYes = discordSeparator("Yes, ", discordText, " is my discord", fillerColor);
        Component denyNo = discordSeparator("No, ", discordText, " is not my discord", fillerColor);

        TextComponent confirm = Component.text("[Confirm]")
                .color(confirmColor)
                .hoverEvent(HoverEvent.showText(confirmYes))
                .clickEvent(ClickEvent.runCommand("/discord confirm " + confirmationId));
        TextComponent or = Component.text(" or ").color(fillerColor);
        TextComponent deny = Component.text("[Deny]")
                .color(denyColor)
                .hoverEvent(HoverEvent.showText(denyNo))
                .clickEvent(ClickEvent.runCommand("/discord deny " + confirmationId));
        return Component.join(JoinConfiguration.noSeparators(), message, confirm, or, deny);
    }

    @NotNull
    private Component discordSeparator(String firstPart, Component discordText, String secondPart, TextColor fillerColor) {
        return Component.join(
                JoinConfiguration.noSeparators(),
                Component.text(firstPart).color(fillerColor),
                discordText,
                Component.text(secondPart).color(fillerColor)
        );
    }
}
