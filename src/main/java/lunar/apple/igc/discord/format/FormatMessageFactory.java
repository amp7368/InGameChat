package lunar.apple.igc.discord.format;

import lunar.apple.igc.discord.SendDiscord;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public interface FormatMessageFactory {
    default FormattedMessageContext context(Player player, Component message, FormatEvent chat) {
        return new FormattedMessageContext(player, message, chat);
    }

    default FormattedMessageContext context(Player player, String message, FormatEvent chat) {
        return new FormattedMessageContext(player, message, chat);
    }

    default void sendFormatted(FormattedMessageContext context) {
        SendDiscord.queueDiscord(FormatBase.format(context));
    }

    default FormattedMessageContext cChat(Player player, Component message) {
        return context(player, message, FormatEvent.CHAT);
    }

    default FormattedMessageContext cPlayerJoin(Player player, Component message) {
        return context(player, message, FormatEvent.PLAYER_JOIN);
    }

    default FormattedMessageContext cPlayerLeave(Player player, Component message) {
        return context(player, message, FormatEvent.PLAYER_LEAVE);
    }

    default FormattedMessageContext cDeath(Player player, Component message) {
        return context(player, message, FormatEvent.PLAYER_DEATH);
    }

    default FormattedMessageContext cDisable() {
        return context(null, "", FormatEvent.DISABLE_SERVER);
    }

    default FormattedMessageContext cEnable() {
        return context(null, "", FormatEvent.ENABLE_SERVER);
    }
}
