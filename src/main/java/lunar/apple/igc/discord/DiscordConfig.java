package lunar.apple.igc.discord;

import apple.utilities.database.ajd.AppleAJDInstImpl;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;

import java.io.File;

public class DiscordConfig {
    private static DiscordConfig instance;
    public String discordToken = "discordToken";

    public static void load() {
        File file = PluginDiscord.get().getFile("discordConfig.json");
        AppleAJDInstImpl<DiscordConfig> manager = new AppleAJDInstImpl<>(file, DiscordConfig.class, FileIOServiceNow.get());
        instance = manager.loadNowOrMake();
    }

    public static DiscordConfig get() {
        return instance;
    }
}

