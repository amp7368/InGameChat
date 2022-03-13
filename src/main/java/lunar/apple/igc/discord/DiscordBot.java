package lunar.apple.igc.discord;

import apple.discord.acd.ACD;
import lunar.apple.igc.LunarPlugin;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class DiscordBot {
    private static final long APPLE_BOTS_SERVER = 603039156892860417L;
    public static ACD acd;

    public DiscordBot() throws LoginException, InterruptedException {
        LunarPlugin.get().getLogger().info("Starting Discord Bot");
        JDABuilder builder = JDABuilder.createDefault(DiscordConfig.get().discordToken);
        JDA client = builder.build();
        acd = new ACD("a!", client, APPLE_BOTS_SERVER);
        acd.updateCommands();
        LunarPlugin.get().getLogger().info("Started Discord Bot");
    }
}
