package lunar.apple.igc.discord;

import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.format.FormatBase;
import lunar.apple.igc.discord.format.FormatMessageFactory;
import lunar.apple.igc.discord.listeners.OnMinecraft;
import lunar.apple.igc.discord.player.VerifiedPlayersDatabase;
import lunar.apple.igc.discord.verify.CommandMCConfirm;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import voltskiya.apple.configs.plugin.manage.ConfigBuilderHolder;
import voltskiya.apple.configs.plugin.manage.PluginManagedModuleConfig;

import javax.security.auth.login.LoginException;
import java.util.Collection;
import java.util.List;

public class PluginIgc extends PluginManagedModule implements PluginManagedModuleConfig, FormatMessageFactory {
    private static PluginManagedModule instance;

    public PluginIgc() {
        instance = this;
    }

    public static PluginManagedModule get() {
        return instance;
    }

    @Override
    public void init() {
        DiscordSecret.load();
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
        new OnMinecraft();
        LunarPlugin.get().scheduleSyncDelayedTask(this::cEnable);
    }

    @Override
    public void onDisable() {
        DiscordBot.disable();
        cDisable();
    }

    @Override
    public String getName() {
        return "Discord";
    }

    @Override
    public Collection<ConfigBuilderHolder<?>> getConfigsToRegister() {
        return List.of(
                gson(DiscordConfig.class).setName("DiscordConfig").setExtension(this::extensionJsonI).nameAsExtension(),
                yml(FormatBase.class).setName("Formatting").setExtension(this::extensionYmlI).nameAsExtension()
        );
    }
}
