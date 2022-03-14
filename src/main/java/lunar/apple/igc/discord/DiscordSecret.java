package lunar.apple.igc.discord;

import apple.utilities.database.ajd.AppleAJDInstImpl;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;

public class DiscordSecret {
    private static DiscordSecret instance;
    public String discordToken = "discordToken";

    public static DiscordSecret get() {
        return instance;
    }

    public static void load() {
        AppleAJDInstImpl<DiscordSecret> manager = new AppleAJDInstImpl<>(PluginIgc.get().getFile("DiscordSecret.json"), DiscordSecret.class, FileIOServiceNow.get());
        instance = manager.loadNowOrMake();
    }
}
