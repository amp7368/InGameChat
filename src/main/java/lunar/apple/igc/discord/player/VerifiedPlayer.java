package lunar.apple.igc.discord.player;

import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.DiscordBot;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.UUID;

public class VerifiedPlayer {
    private long discordId;
    private UUID minecraftId;
    private String lastKnownUsername;
    private Date lastPlayed = new Date();
    private boolean memberFound = false;

    public VerifiedPlayer(Player minecraft, User discord) {
        this.discordId = discord.getIdLong();
        this.minecraftId = minecraft.getUniqueId();
        this.lastKnownUsername = minecraft.getName();
    }

    public VerifiedPlayer() {
    }

    public long getDiscordId() {
        return this.discordId;
    }

    public UUID getMinecraftId() {
        return this.minecraftId;
    }

    @Override
    public int hashCode() {
        return this.minecraftId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VerifiedPlayer other &&
                other.minecraftId.equals(this.minecraftId) &&
                other.discordId == this.discordId;
    }

    public void verifyName() {
        DiscordBot.mainGuild.retrieveMemberById(discordId).queue(
                member -> {
                    if (member == null) {
                        this.memberFound = false;
                    } else {
                        DiscordBot.mainGuild.modifyNickname(member, lastKnownUsername).queue();
                        this.memberFound = true;
                    }
                },
                failure -> {
                    LunarPlugin.get().getSLF4JLogger().error("Could not modify the nickname of " + this.lastKnownUsername, failure);
                }
        );
    }
}
