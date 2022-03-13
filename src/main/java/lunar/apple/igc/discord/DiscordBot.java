package lunar.apple.igc.discord;

import apple.discord.acd.ACD;
import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.verify.CommandDiscordVerify;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    private static final long APPLE_BOTS_SERVER = 603039156892860417L;
    public static ACD acd;
    public static Guild mainGuild;

    public DiscordBot() throws LoginException, InterruptedException {
        LunarPlugin.get().getLogger().info("Starting Discord Bot");
        JDABuilder builder = JDABuilder.createDefault(DiscordConfig.get().discordToken);
        JDA client = builder.build();
        client.addEventListener(this);
        acd = new ACD("a!", client, APPLE_BOTS_SERVER);
        new CommandDiscordVerify(acd);
        acd.updateCommands();

        LunarPlugin.get().getLogger().info("Started Discord Bot");
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        if (DiscordConfig.get().mainGuildId == null)
            LunarPlugin.get().getSLF4JLogger().error("Discord guild not specified in DiscordConfig");
        else
            mainGuild = acd.getJDA().getGuildById(DiscordConfig.get().mainGuildId);
        if (mainGuild == null) {
            LunarPlugin.get().getSLF4JLogger().error("Discord guild not found!");
        }
    }
}
