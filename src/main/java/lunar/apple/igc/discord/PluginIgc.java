package lunar.apple.igc.discord;

import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.player.VerifiedPlayersDatabase;
import lunar.apple.igc.discord.verify.CommandMCConfirm;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;

import javax.security.auth.login.LoginException;

public class PluginIgc extends PluginManagedModule {
    private static PluginManagedModule instance;

    public PluginIgc() {
        instance = this;
    }

    public static PluginManagedModule get() {
        return instance;
    }

    @Override
    public void enable() {
        DiscordConfig.load();
        VerifiedPlayersDatabase.load();
        try {
            new DiscordBot();
        } catch (LoginException | InterruptedException e) {
            LunarPlugin.get().getSLF4JLogger().error("The discord bot could not log in!", e);
        }
        new CommandMCConfirm();
    }

    @Override
    public String getName() {
        return "Discord";
    }
}
