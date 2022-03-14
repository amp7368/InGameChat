package lunar.apple.igc.discord.format;

import org.bukkit.Bukkit;

import java.util.function.Function;

public enum FormatComponent {
    CONTENT("content", (context) -> context.content),
    MAX_PLAYER_COUNT("maxPlayerCount", (context) -> String.valueOf(Bukkit.getMaxPlayers())),
    NEW_PLAYER_COUNT("newPlayerCount", (context) -> String.valueOf(context.newOnlinePlayers)),
    OLD_PLAYER_COUNT("oldPlayerCount", (context) -> String.valueOf(context.oldOnlinePlayers)),
    PLAYER_UUID("playerUUID", (context) -> context.player.getUniqueId().toString()),
    SERVER_IP("serverIp", (context) -> Bukkit.getServer().getIp()),
    SERVER_NAME("serverName", (context) -> Bukkit.getServer().getName()),
    USERNAME("username", (context) -> context.player.getName());

    private final String replaceString;
    private final Function<FormattedMessageContext, String> replacement;

    FormatComponent(String replaceString, Function<FormattedMessageContext, String> replacement) {
        this.replaceString = "%" + replaceString + "%";
        this.replacement = replacement;
    }

    public static String format(String content, FormattedMessageContext context) {
        for (FormatComponent component : values())
            content = component.formatThis(content, context);
        return content;
    }

    private String formatThis(String content, FormattedMessageContext context) {
        String replaceWith = this.replacement.apply(context);
        if (replaceWith == null) replaceWith = "";
        return content.replace(
                this.replaceString,
                replaceWith
        );
    }
}
