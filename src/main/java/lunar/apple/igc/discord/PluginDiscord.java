package lunar.apple.igc.discord;

import lunar.apple.igc.LunarPlugin;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;

import javax.security.auth.login.LoginException;

public class PluginDiscord extends PluginManagedModule {
    private static PluginManagedModule instance;

    public PluginDiscord() {
        instance = this;
    }

    public static PluginManagedModule get() {
        return instance;
    }

    @Override
    public void enable() {
        DiscordConfig.load();
        try {
            new DiscordBot();
        } catch (LoginException | InterruptedException e) {
            LunarPlugin.get().getSLF4JLogger().error("The discord bot could not log in!", e);
        }
    }

    @Override
    public String getName() {
        return "Discord";
    }
}
