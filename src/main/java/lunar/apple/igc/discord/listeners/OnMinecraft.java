package lunar.apple.igc.discord.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import lunar.apple.igc.LunarPlugin;
import lunar.apple.igc.discord.format.FormatMessageFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnMinecraft implements Listener, FormatMessageFactory {
    public OnMinecraft() {
        LunarPlugin.get().registerEvents(this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncChatEvent event) {
        sendFormatted(cChat(event.getPlayer(), event.message()));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        sendFormatted(cPlayerJoin(event.getPlayer(), event.joinMessage()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        sendFormatted(cPlayerLeave(event.getPlayer(), event.quitMessage()));
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        sendFormatted(cDeath(event.getPlayer(), event.deathMessage()));
    }
}
