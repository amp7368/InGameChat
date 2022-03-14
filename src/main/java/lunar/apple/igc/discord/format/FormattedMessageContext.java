package lunar.apple.igc.discord.format;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FormattedMessageContext {
    public Player player;
    public String content;
    public FormatEvent event;
    public int oldOnlinePlayers;
    public int newOnlinePlayers;

    public FormattedMessageContext(Player player, String content, FormatEvent event) {
        this.player = player;
        this.content = content;
        this.event = event;
        this.oldOnlinePlayers = Bukkit.getOnlinePlayers().size();
        this.newOnlinePlayers = this.oldOnlinePlayers + event.getPlayersOnlineChange();
    }

    public FormattedMessageContext(Player player, Component message, FormatEvent chat) {
        this(player, plainText(message), chat);
    }

    private static String plainText(Component message) {
        return PlainTextComponentSerializer.plainText().serialize(message);
    }
}
