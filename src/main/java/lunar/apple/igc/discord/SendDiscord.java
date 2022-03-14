package lunar.apple.igc.discord;

import apple.utilities.request.AppleRequestVoid;
import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.AllowedMentions;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.format.FormattedMessage;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;

import java.util.Objects;

public class SendDiscord {
    public static Guild mainGuild;
    public static GuildChannel linkedChannel;
    private static Activity lastActivity = null;

    public static void onReady() {
        if (DiscordConfig.get().mainGuildId == null) {
            LunarPlugin.logger().error("Discord guild not specified in DiscordConfig");
            return;
        }
        mainGuild = DiscordBot.client.getGuildById(DiscordConfig.get().mainGuildId);
        if (mainGuild == null) LunarPlugin.logger().error("Discord guild not found!");

        if (DiscordConfig.get().linkedChannel == null) {
            LunarPlugin.logger().error("Discord guild not specified in DiscordConfig");
        } else {
            linkedChannel = DiscordBot.client.getGuildChannelById(DiscordConfig.get().linkedChannel);
        }
    }

    private static void verifyPresence() {
        Activity newActivity = Activity.of(
                Activity.ActivityType.PLAYING,
                String.format("LunarSol (%d players online)", Bukkit.getOnlinePlayers().size()),
                "https://lunarsol.xyz/"
        );
        if (Objects.equals(lastActivity, newActivity)) return;
        lastActivity = newActivity;
        DiscordBot.client.getPresence().setPresence(OnlineStatus.ONLINE, newActivity);

    }

    private static void queueVoid(AppleRequestVoid task) {
        FileIOServiceNow.get().queueVoid(task);
        // this is scheduled to make sure the player count is correct
        LunarPlugin.get().scheduleSyncDelayedTask(
                () -> FileIOServiceNow.get().queueVoid(SendDiscord::verifyPresence),
                1
        );
    }

    public static void queueDiscord(FormattedMessage message) {
        queueVoid(() -> sendDiscord(message));
    }

    private static void sendDiscord(FormattedMessage message) {
        if (linkedChannel == null) {
            logger().error("Could not send in-game message to discord");
            return;
        }
        WebhookMessage webhookMessage = new WebhookMessageBuilder()
                .setUsername(message.getUsername())
                .setAvatarUrl(message.getAvatar())
                .setContent(message.getContent())
                .setAllowedMentions(AllowedMentions.none())
                .build();
        WebhookClient client = webhook();
        if (client == null) {
            logger().error("Webhook url is invalid");
            return;
        }
        client.send(webhookMessage);
        client.close();
    }

    @Nullable
    private static WebhookClient webhook() {
        String url = DiscordConfig.get().webhookUrl;
        return url == null ? null : new WebhookClientBuilder(url).build();
    }

    private static Logger logger() {
        return LunarPlugin.logger();
    }
}
