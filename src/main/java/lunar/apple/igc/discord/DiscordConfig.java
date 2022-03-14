package lunar.apple.igc.discord;

import apple.utilities.database.ajd.AppleAJDInstImpl;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;
import ycm.yml.manager.fields.YcmField;

import java.io.File;

public class DiscordConfig {
    private static DiscordConfig instance;
    @YcmField
    public Long mainGuildId = null;
    @YcmField
    public Long linkedChannel = null;
    @YcmField
    public String webhookUrl;

    public static void load() {
        File file = PluginIgc.get().getFile("discordConfig.json");
        AppleAJDInstImpl<DiscordConfig> manager = new AppleAJDInstImpl<>(file, DiscordConfig.class, FileIOServiceNow.get());
        instance = manager.loadNowOrMake();
    }

    public static DiscordConfig get() {
        return instance;
    }
}

