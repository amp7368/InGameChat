package lunar.apple.igc.discord;

import apple.discord.acd.ACD;
import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.verify.CommandDiscordVerify;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    private static final long APPLE_BOTS_SERVER = 603039156892860417L;
    public static ACD acd;
    public static JDA client;

    public DiscordBot() throws LoginException, InterruptedException {
        LunarPlugin.get().getLogger().info("Starting Discord Bot");
        JDABuilder builder = JDABuilder.createDefault(DiscordSecret.get().discordToken);
        client = builder.build();
        client.addEventListener(this);
        acd = new ACD("a!", client, APPLE_BOTS_SERVER);
        new CommandDiscordVerify(acd);
        acd.updateCommands();

        LunarPlugin.get().getLogger().info("Started Discord Bot");
    }

    public static void disable() {
        LunarPlugin.get().getLogger().info("Stopping Discord Bot");
        acd.getJDA().shutdown();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        SendDiscord.onReady();
    }
}
