package lunar.apple.igc.discord.player;

import apple.utilities.database.ajd.AppleAJDInstImpl;
import lunar.apple.igc.discord.PluginIgc;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;

import java.io.File;
import java.util.*;

public class VerifiedPlayersDatabase {
    private static VerifiedPlayersDatabase instance;
    private static AppleAJDInstImpl<VerifiedPlayersDatabase> manager;

    private final Set<VerifiedPlayer> players = new HashSet<>();
    private final transient Map<UUID, VerifiedPlayer> minecraftPlayers = new HashMap<>();
    private final transient Map<Long, VerifiedPlayer> discordPlayers = new HashMap<>();

    private final transient Map<UUID, VerifiedPlayer> processingUsernames = new HashMap<>();

    public static void load() {
        File file = PluginIgc.get().getFile("players.json");
        manager = new AppleAJDInstImpl<>(file, VerifiedPlayersDatabase.class, FileIOServiceNow.get());
        instance = manager.loadNowOrMake();
        for (VerifiedPlayer player : instance.players) {
            instance.add(player);
        }
    }

    public static VerifiedPlayersDatabase get() {
        return instance;
    }

    private void add(VerifiedPlayer player) {
        this.players.add(player);
        this.minecraftPlayers.put(player.getMinecraftId(), player);
        this.discordPlayers.put(player.getDiscordId(), player);
        manager.save();
    }

    public UUID process(VerifiedPlayer player) {
        UUID id = UUID.randomUUID();
        this.processingUsernames.put(id, player);
        return id;
    }

    public boolean confirm(UUID playerUUID, UUID requestId) {
        VerifiedPlayer processing = processingUsernames.remove(requestId);
        if (processing == null || !processing.getMinecraftId().equals(playerUUID)) return false;
        add(processing);
        processing.verifyName();
        return true;
    }

    public boolean deny(UUID requestId) {
        VerifiedPlayer processing = processingUsernames.remove(requestId);
        return processing != null;
    }
}
